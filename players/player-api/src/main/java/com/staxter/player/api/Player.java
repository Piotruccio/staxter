package com.staxter.player.api;

/**
 * Interface representing a player that can send/receive messages to/from
 * other player(s).
 */
public interface Player {

    /**
     * Sends given message to the given player (by player ID).
     *
     * @param message the message to send
     * @throws PlayerException when unable to send the given message due the
     *      player is not registered (by given player ID)
     */
    void sendMessage(Message message) throws PlayerException;

    /**
     * Reports a new message received from other player.
     *
     * @param message the message received
     */
    void receiveMessage(Message message);

    /**
     * Returns the player ID of this player.
     *
     * The player ID should be unique value across all players that want to
     * register in the messenger and communicate with each other.
     *
     * @return the player ID of this player
     */
    PlayerID getPlayerID();
}
