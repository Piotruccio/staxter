package com.staxter.player.main;

import com.staxter.player.api.Messenger;
import com.staxter.player.api.Player;
import com.staxter.player.api.PlayerException;
import com.staxter.player.impl.InitiatingPlayer;
import com.staxter.player.impl.MessengerImpl;
import com.staxter.player.impl.ReplyingPlayer;
import com.staxter.player.impl.remote.RemoteMessengerImpl;

import java.net.InetSocketAddress;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

/**
 * Players application starting point, allowing to run the application with given
 * arguments.
 *
 * Run with following cmd arguments:
 * - 1) mode, where "local", "remote", or "remote-initiator" are supported
 * - 2) limit, the messages limit, i.e. stop condition (default is 10),
 *      only when running locally, or for remote initiating player
 *
 * - 3) local address (hostport), only when running remotely
 * - 4) remote address (hostport), only when running remotely
 * - 5) local name, the player name (ID), only when running remotely
 * - 6) remote name, the remote player name (ID), only when running remotely
 *
 * Examples: java Main local 20
 *
 *           java Main remote localhost:1199 localhost:1099 PlayerB PlayerA
 *           java Main remote-initiator localhost:1099 localhost:1199 PlayerA PlayerB 5
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static final int MODE_INDEX = 0;
    public static final int LOCAL_ADDRESS_INDEX = 1;
    public static final int REMOTE_ADDRESS_INDEX = 2;
    public static final int LOCAL_NAME_INDEX = 3;
    public static final int REMOTE_NAME_INDEX = 4;

    public static final int LOCAL_LIMIT_INDEX = 1;
    public static final int REMOTE_LIMIT_INDEX = 5;

    public static final String MODE_LOCAL = "local";
    public static final String MODE_REMOTE = "remote";
    public static final String MODE_REMOTE_INITIATOR = "remote-initiator";

    public static final int DEFAULT_MESSAGES_LIMIT = 10;

    /**
     * Runs the players application with given arguments.
     *
     * @param args the arguments to run with
     */
    public static void main(String[] args) throws PlayerException, RemoteException {
        final String mode = getMode(args);

        if (mode.equalsIgnoreCase(MODE_LOCAL)) {
            logger.log(Level.INFO, () -> "Running locally, args: " + Arrays.toString(args));

            runLocally(args);
        } else if (mode.equalsIgnoreCase(MODE_REMOTE)
                || mode.equalsIgnoreCase(MODE_REMOTE_INITIATOR)) {

            logger.log(Level.INFO, () -> "Running remotely, args: " + Arrays.toString(args));

            runRemotely(args);
        }
        else throw new PlayerException("Invalid running mode: " + mode);
    }

    private static void runLocally(String[] args) throws PlayerException {
        final Messenger messenger = new MessengerImpl(); // With local messenger

        try {
            final Player initiator = new InitiatingPlayer(messenger,
                    "PlayerA", getMessagesLimit(args, LOCAL_LIMIT_INDEX));

            final Player replayer = new ReplyingPlayer(messenger, "PlayerB");

            messenger.registerPlayer(initiator);
            messenger.registerPlayer(replayer);

            final String message = "Hello";
            logger.log(Level.INFO, () -> "Sending initial message: " + message);

            initiator.sendMessage(message, replayer.getPlayerID());

        } finally {
            messenger.stop(); // Stop and release messenger resources
        }
    }

    private static void runRemotely(String[] args) throws PlayerException, RemoteException {
        final Messenger messenger = new RemoteMessengerImpl(getLocalAddress(args),
                getRemoteAddress(args)); // With remote messenger

        try {
            if (getMode(args).equals(MODE_REMOTE_INITIATOR)) {

                final Player player = new InitiatingPlayer(messenger,
                        getLocalPlayerName(args), getMessagesLimit(args, REMOTE_LIMIT_INDEX));

                messenger.registerPlayer(player);

                final String message = "Hello";
                logger.log(Level.INFO, () -> "Sending initial message: " + message);

                player.sendMessage(message, getRemotePlayerName(args));

            } else {
                messenger.registerPlayer(
                        new ReplyingPlayer(messenger, getLocalPlayerName(args)));

                logger.log(Level.INFO, () -> "Waiting for messages from remote player");
            }

            while (true) {
                try {
                    Thread.sleep(200); // Allow the thread to pause between checks

                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, () -> "Unexpected exception: " + e.getMessage());

                    throw new PlayerException("Unexpected exception", e);
                }

                if (messenger.isStopped()) break;
            }
        } catch (PlayerException e) {
            messenger.stop(); // Stop and release messenger resources
            throw e;
        }
    }

    private static String getMode(String[] args) throws PlayerException {
        if (args == null || args.length < MODE_INDEX + 1) {
            throw new PlayerException("Missing mode argument, index: " + MODE_INDEX);
        }
        return args[MODE_INDEX];
    }

    private static InetSocketAddress getLocalAddress(String[] args) throws PlayerException {
        return getAddress(args, LOCAL_ADDRESS_INDEX);
    }

    private static InetSocketAddress getRemoteAddress(String[] args) throws PlayerException {
        return getAddress(args, REMOTE_ADDRESS_INDEX);
    }

    private static String getLocalPlayerName(String[] args) throws PlayerException {
        return getPlayerName(args, LOCAL_NAME_INDEX);
    }

    private static String getRemotePlayerName(String[] args) throws PlayerException {
        return getPlayerName(args, REMOTE_NAME_INDEX);
    }

    private static InetSocketAddress getAddress(String[] args, int index) throws PlayerException {
        if (args == null || args.length < index + 1) {
            throw new PlayerException("Missing address argument, index: " + index + 1);
        }

        try {
            String[] hostport = args[index].split(":");

            return new InetSocketAddress(hostport[0], parseInt(hostport[1]));

        } catch (Exception e) {
            throw new PlayerException("Invalid address argument, index: " + index + 1);
        }
    }

    private static String getPlayerName(String[] args, int index) throws PlayerException {
        if (args == null || args.length < index + 1) {
            throw new PlayerException("Missing player name argument, index: " + index + 1);
        }

        return args[index];
    }

    private static int getMessagesLimit(String[] args, int index) {
        return args.length > index ? parseInt(args[index]) : DEFAULT_MESSAGES_LIMIT;
    }
}
