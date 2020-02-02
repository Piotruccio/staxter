package com.staxter.player.api;

/**
 * Represents a mediator interface, which allows sending messages between
 * the registered players.
 *
 * This approach allows a loose coupling between the players, thanks to a
 * single point of communication (different player implementations may
 * work together via the messenger).
 */
public interface Messenger {

    /**
     * Registers the given player in the messenger so that it can receive
     * messages from other player(s).
     *
     * @param player the player to register
     * @throws PlayerException when the player by given player ID is already
     *      registered
     */
    void registerPlayer(Player player) throws PlayerException;

    /**
     * Unregisters the given player from the messenger so that it cannot
     * receive any new messages from other player(s).
     *
     * @param player the player to unregister
     * @throws PlayerException when the player by given player ID is already
     *      unregistered
     */
    void unregisterPlayer(Player player) throws PlayerException;

    /**
     * Returns true if player with given player ID is registered in the
     * messenger, false otherwise.
     *
     * @param playerID the player ID to search
     * @return true if the player is registered, false otherwise
     */
    boolean isPlayerRegistered(PlayerID playerID);

    /**
     * Sends the given message to the given player (receiver).
     *
     * @param message the message to send
     * @throws PlayerException when the player given by player ID is not
     *      registered
     */
    void sendMessage(Message message) throws PlayerException;

    /**
     * Stops the messenger and releases all resources (unregisters players).
     */
    void stop();

    /**
     * Returns true if the messenger is in stopped state, false otherwise.
     *
     * @return true if the messenger is in stopped state, false otherwise
     */
    boolean isStopped();
}
