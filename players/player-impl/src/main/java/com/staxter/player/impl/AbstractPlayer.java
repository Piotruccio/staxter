package com.staxter.player.impl;

import com.staxter.player.api.Message;
import com.staxter.player.api.Messenger;
import com.staxter.player.api.Player;
import com.staxter.player.api.PlayerException;
import com.staxter.player.api.PlayerID;

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
    private final PlayerID playerID;

    private final Logger logger;

    /**
     * Constructs a new instance of a default player with given messenger
     * and player ID.
     *
     * @param messenger the associated messenger, cannot be null
     * @param playerID the player ID, cannot be null
     */
    AbstractPlayer(Messenger messenger, PlayerID playerID) {
        requireNonNull(messenger, "messenger cannot be null");
        requireNonNull(playerID, "playerID cannot be null");

        this.messenger = messenger;
        this.playerID = playerID;

        logger = Logger.getLogger(getClass().getName() + "-" + getPlayerID());
    }

    @Override
    public void sendMessage(Message message) throws PlayerException {
        requireNonNull(message, "message cannot be null");

        logger.log(Level.INFO, () -> format("Sending message: \"%s\" to: \"%s\"",
                message.getMessageContent(), message.getReceiverID()));

        messenger.sendMessage(message);
    }

    @Override
    public final void receiveMessage(Message message) {
        requireNonNull(message, "message cannot be null");

        logger.log(Level.INFO, () -> format("Received message: \"%s\" from: \"%s\"",
                message.getMessageContent(), message.getSenderID()));

        handleReceivedMessage(message);
    }

    /**
     * Handles the received message in a way specific to concrete player
     * instance. To be overridden in subclasses.
     *
     * @param message the received message to handle, not null
     */
    protected abstract void handleReceivedMessage(Message message);

    @Override
    public PlayerID getPlayerID() { return playerID; }

    @Override
    public String toString() { return playerID.getStringValue(); }

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
