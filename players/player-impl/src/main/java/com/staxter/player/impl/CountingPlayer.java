package com.staxter.player.impl;

import com.staxter.player.api.Messenger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

/**
 * A player implementation that provides automatic counting of received
 * messages.
 */
abstract class CountingPlayer extends AbstractPlayer {

    private final AtomicInteger counter;

    /**
     * Constructs a new instance of counting player with given messenger
     * and player ID.
     *
     * @param messenger the associated messenger, cannot be null
     * @param playerID the player ID, cannot be null
     */
    CountingPlayer(Messenger messenger, String playerID) {
        super(messenger, playerID);

        counter = new AtomicInteger();
    }

    @Override
    protected final void handleReceivedMessage(String message, String senderID) {
        final int count = counter.incrementAndGet();

        getLogger().log(Level.INFO, () -> "Incremented messages counter to: " + count);

        handleReceivedMessage(message, senderID, count);
    }

    /**
     * Handles the received message in a way specific to concrete player
     * instance. To be overridden in subclasses.
     *
     * @param message the received message to handle, not null
     * @param senderID the player ID of the message sender, not null
     * @param counter the current value of messages counter
     */
    protected abstract void handleReceivedMessage(
            String message, String senderID, int counter);
}
