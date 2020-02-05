package com.staxter.player.impl;

import com.staxter.player.api.Messenger;
import com.staxter.player.api.Player;
import com.staxter.player.api.PlayerException;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Simple abstract implementation of player that communicates via a mediator
 * instance with other player(s).
 *
 * To be overridden by concrete player implementations.
 */
abstract class AbstractPlayer implements Player {

    private final Messenger messenger; // Both fields are immutable
    private final String playerID;

    private final Logger logger;

    /**
     * Constructs a new instance of a default player with given messenger
     * and player ID.
     *
     * @param messenger the associated messenger, cannot be null
     * @param playerID the player ID, cannot be null
     */
    AbstractPlayer(Messenger messenger, String playerID) {
        requireNonNull(messenger, "messenger cannot be null");
        requireNonNull(playerID, "playerID cannot be null");

        this.messenger = messenger;
        this.playerID = playerID;

        logger = Logger.getLogger(getClass().getName() + "-" + getPlayerID());
    }

    @Override
    public void sendMessage(String message, String receiverID) throws PlayerException {
        requireNonNull(message, "message cannot be null");
        requireNonNull(receiverID, "receiverID cannot be null");

        logger.log(Level.INFO, () -> format("Sending message: \"%s\" to: \"%s\"",
                message, receiverID));

        messenger.sendMessage(message, playerID, receiverID);
    }

    @Override
    public final void receiveMessage(String message, String senderID) {
        requireNonNull(message, "message cannot be null");
        requireNonNull(senderID, "senderID cannot be null");

        logger.log(Level.INFO, () -> format("Received message: \"%s\" from: \"%s\"",
                message, senderID));

        handleReceivedMessage(message, senderID);
    }

    /**
     * Handles the received message in a way specific to concrete player
     * instance. To be overridden in subclasses.
     *
     * @param message the received message to handle, not null
     * @param senderID the player ID of the message sender, not null
     */
    protected abstract void handleReceivedMessage(String message, String senderID);

    @Override
    public String getPlayerID() { return playerID; }

    @Override
    public String toString() { return playerID; }

    /**
     * Returns the messenger instance associated with this player.
     *
     * @return the messenger, not null
     */
    protected Messenger getMessenger() { return messenger; }

    /**
     * Returns the logger instance associated with this player that can be used
     * for logging purposes.
     *
     * @return the logger, not null
     */
    protected Logger getLogger() { return logger; }
}
