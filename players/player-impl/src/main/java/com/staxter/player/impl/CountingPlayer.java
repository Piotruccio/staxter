package com.staxter.player.impl;

import com.staxter.player.api.Message;
import com.staxter.player.api.Messenger;
import com.staxter.player.api.PlayerID;

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
    public CountingPlayer(Messenger messenger, PlayerID playerID) {
        super(messenger, playerID);

        counter = new AtomicInteger();
    }

    @Override
    protected final void handleReceivedMessage(Message message) {
        final int count = counter.incrementAndGet();

        getLogger().log(Level.INFO, () -> "Incremented messages counter to: " + count);

        handleReceivedMessage(message, count);
    }

    /**
     * Handles the received message in a way specific to concrete player
     * instance. To be overridden in subclasses.
     *
     * @param message the received message to handle
     * @param counter the current value of messages counter
     */
    protected abstract void handleReceivedMessage(Message message, int counter);
}
