package com.staxter.player.api;

/**
 * Interface representing a player that can send/receive messages to/from
 * other player(s).
 */
public interface Player {

    /**
     * Sends given message to the given player (by player ID).
     *
     * @param message the message to send, not null
     * @param receiverID the player ID of the message receiver, not null
     * @throws PlayerException when unable to send the given message due the
     *      player is not registered (by given player ID)
     */
    void sendMessage(String message, String receiverID) throws PlayerException;

    /**
     * Reports a new message received from other player.
     *
     * @param message the message received, not null
     * @param senderID the player ID of the message sender, not null
     */
    void receiveMessage(String message, String senderID);

    /**
     * Returns the player ID of this player.
     *
     * The player ID should be unique value across all players that want to
     * register in the messenger and communicate with each other.
     *
     * @return the player ID of this player, not null
     */
    String getPlayerID();
}
