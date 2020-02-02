package com.staxter.player.impl;

import com.staxter.player.api.Message;
import com.staxter.player.api.Messenger;
import com.staxter.player.api.PlayerException;
import com.staxter.player.api.PlayerID;

import java.util.function.Function;
import java.util.logging.Level;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * Represents a player implementation class that provides automatic replies to
 * received messages.
 */
public class ReplyingPlayer extends CountingPlayer {

    private final Function<Integer, Boolean> stopFunction; // Stop is immutable

    /**
     * Constructs a new instance of replying player with given messenger and
     * player ID, and no stop function (i.e. endless replying player).
     *
     * @param messenger the associated messenger, cannot be null
     * @param playerID  the player ID, cannot be null
     */
    public ReplyingPlayer(Messenger messenger, PlayerID playerID) {
        this(messenger, playerID, NO_STOP_FUNCTION);
    }

    /**
     * Constructs a new instance of replying player with given messenger,
     * player ID and stop function.
     *
     * @param messenger the associated messenger, cannot be null
     * @param playerID  the player ID, cannot be null
     * @param stopFunction the stop function implementing stop condition based
     *      on received messages counter, cannot be null (endless replying player)
     */
    public ReplyingPlayer(
            Messenger messenger, PlayerID playerID, Function<Integer, Boolean> stopFunction) {

        super(messenger, playerID);

        requireNonNull(stopFunction, "stopFunction cannot be null");
        this.stopFunction = stopFunction;
    }

    @Override
    protected final void handleReceivedMessage(Message message, int counter) {
        requireNonNull(message, "message cannot be null");

        if (checkStopCondition(counter)) {
            getLogger().log(Level.INFO, () -> format("Stop condition met, stopping..."));

            getMessenger().stop(); // Stopping messenger, could use callback for flexibility
            return;
        }

        try {
            sendMessage(new MessageImpl(getPlayerID(), message.getSenderID(),
                    createReplyContent(message, counter)));

        } catch (PlayerException e) {
            getLogger().log(Level.SEVERE, () -> format(
                    "Failed to send message, reason: \"%s\"", e.getMessage()));
        }
    }

    /**
     * Creates reply message content, which consists of the received message
     * content concatenated with received messages counter value.
     *
     * Note: To be overridden in subclasses as needed.
     *
     * @param message the received message to reply to
     * @param counter the current messages counter
     * @return reply message content, cannot be null
     */
    protected String createReplyContent(Message message, int counter) {
        return message.getMessageContent() + counter;
    }

    private boolean checkStopCondition(int counter) { return stopFunction.apply(counter); }

    private static final Function<Integer, Boolean> NO_STOP_FUNCTION = i -> false;
}
