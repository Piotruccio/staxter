package com.staxter.player.impl;

import com.staxter.player.api.Messenger;
import com.staxter.player.api.Player;
import com.staxter.player.api.PlayerException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * A simple messenger implementation that allows for sending messages between the
 * registered players locally (within single Java process).
 */
public class MessengerImpl implements Messenger {

    private final AtomicBoolean running;
    private final Map<String, Player> players; // Map with registered players (by ID)

    private final Logger logger;

    /**
     * Constructs a new local messenger instance.
     */
    public MessengerImpl() {
        running = new AtomicBoolean(true);
        players = new ConcurrentHashMap<>();
        logger = Logger.getLogger(getClass().getName());
    }

    @Override
    public void registerPlayer(Player player) throws PlayerException {
        requireNonNull(player, "player cannot be null");

        final Player registeredPlayer = players.putIfAbsent(player.getPlayerID(), player);
        if (registeredPlayer != null) {
            throw new PlayerException(format("Player \"%s\" already registered", player));
        }

        logger.log(Level.INFO, () -> format("Registered player: \"%s\"", player));
    }

    @Override
    public void unregisterPlayer(Player player) throws PlayerException {
        requireNonNull(player, "player cannot be null");

        final Player registeredPlayer = players.remove(player.getPlayerID());
        if (registeredPlayer == null) {
            throw new PlayerException(format("Player \"%s\" not registered", player));
        }

        logger.log(Level.INFO, () -> format("Unregistered player: \"%s\"", player));
    }

    @Override
    public boolean isPlayerRegistered(String playerID) {
        requireNonNull(playerID, "playerID cannot be null");

        return players.get(playerID) != null;
    }

    @Override
    public void sendMessage(String message, String senderID, String receiverID)
            throws PlayerException {

        requireNonNull(message, "message cannot be null");
        requireNonNull(senderID, "senderID cannot be null");
        requireNonNull(receiverID, "receiverID cannot be null");

        final Player receiver = players.get(receiverID);
        if (receiver == null) {
            throw new PlayerException(format("Player \"%s\" not registered", receiverID));
        }

        logger.log(Level.INFO, () -> format("Sending message: \"%s\" from \"%s\" to \"%s\"",
                message, senderID, receiverID));

        receiver.receiveMessage(message, senderID);
    }

    @Override
    public void stop() {
        logger.log(Level.INFO, () -> "Stopping... ");

        running.set(false);
        players.clear(); // Cleanup the resources (registered players)
    }

    @Override
    public boolean isStopped() { return !running.get(); }

    /**
     * Returns the logger instance associated with this messenger that can be used
     * for logging purposes.
     *
     * @return the logger, not null
     */
    protected Logger getLogger() { return logger; }
}
