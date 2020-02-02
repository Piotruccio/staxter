package com.staxter.player.api;

/**
 * A convenient message holder that, in addition to the message value, contains
 * player IDs of the sender and receiver players.
 */
public interface Message {

    /**
     * Returns the player ID of the message sender.
     *
     * @return the player ID of the message sender
     */
    PlayerID getSenderID();

    /**
     * Returns the player ID of the message receiver.
     *
     * @return the player ID of the message receiver
     */
    PlayerID getReceiverID();

    /**
     * Returns the actual message content.
     * @return the message content
     */
    String getMessageContent();
}
