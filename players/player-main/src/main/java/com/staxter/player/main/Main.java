package com.staxter.player.main;

import com.staxter.player.api.Message;
import com.staxter.player.api.Messenger;
import com.staxter.player.api.Player;
import com.staxter.player.api.PlayerException;
import com.staxter.player.impl.InitiatingPlayer;
import com.staxter.player.impl.MessageImpl;
import com.staxter.player.impl.MessengerImpl;
import com.staxter.player.impl.PlayerIDImpl;
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
 *           java Main remote-initiator localhost:1099 localhost:1199 PlayerA PlayerB 5
 *           java Main remote localhost:1199 localhost:1099 PlayerB PlayerA
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static final int DEFAULT_MESSAGES_LIMIT = 10;
    public static final String REMOTE_INITIATOR = "remote-initiator";

    /**
     * Runs the players application with given arguments.
     *
     * @param args the arguments to run with
     */
    public static void main(String[] args) throws PlayerException, RemoteException {
        if (isLocal(args)) {
            logger.log(Level.INFO, () -> "Running locally, args: " + Arrays.toString(args));

            runLocally(args);
        } else {
            logger.log(Level.INFO, () -> "Running remotely, args: " + Arrays.toString(args));

            runRemotely(args);
        }
    }

    private static boolean isLocal(String[] args) {
        return args == null || args.length == 0
                || !(args[0].equals("remote") || args[0].equals(REMOTE_INITIATOR));
    }

    private static void runLocally(String[] args) throws PlayerException {
        final Messenger messenger = new MessengerImpl();
        try {
            Player initiator = new InitiatingPlayer(messenger,
                    new PlayerIDImpl("PlayerA"), getMessagesLimit(args, 1));

            Player replayer = new ReplyingPlayer(messenger, new PlayerIDImpl("PlayerB"));

            messenger.registerPlayer(initiator);
            messenger.registerPlayer(replayer);

            final Message message = new MessageImpl(initiator, replayer, "Hello");

            logger.log(Level.INFO, () -> "Sending initial message: " + message);
            initiator.sendMessage(message);

        } finally {
            messenger.stop(); // Stop and release messenger resources
        }
    }

    private static void runRemotely(String[] args) throws PlayerException, RemoteException {
        final boolean isInitiator = args[0].equals(REMOTE_INITIATOR);

        String localName = getPlayerName(args, 3);
        String remoteName = getPlayerName(args, 4);

        final Messenger messenger = new RemoteMessengerImpl(getAddress(args, 1),
                getAddress(args, 2));
        try {
            if (isInitiator) {

                final Player player = new InitiatingPlayer(messenger,
                        new PlayerIDImpl(localName), getMessagesLimit(args, 5));

                messenger.registerPlayer(player);

                final Message message = new MessageImpl(player.getPlayerID(),
                        new PlayerIDImpl(remoteName), "Hello");

                logger.log(Level.INFO, () -> "Sending initial message: " + message);
                player.sendMessage(message);

            } else {
                messenger.registerPlayer(
                        new ReplyingPlayer(messenger, new PlayerIDImpl(localName)));
            }

            if (!isInitiator) {
                logger.log(Level.INFO, () -> "Waiting for messages from: \"" + remoteName + "\"");
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
