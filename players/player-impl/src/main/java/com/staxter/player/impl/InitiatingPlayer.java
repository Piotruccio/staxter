package com.staxter.player.impl;

import com.staxter.player.api.Messenger;
import com.staxter.player.api.PlayerID;

/**
 * Represents a player implementation class that provides automatic replies to
 * received messages until the received messages counter is equal to given limit.
 */
public class InitiatingPlayer extends ReplyingPlayer {

    /**
     * Constructs a new instance of initiating player with given messenger,
     * player ID, and messages limit.
     *
     * @param messenger the associated messenger, cannot be null
     * @param playerID  the player ID, cannot be null
     * @param messagesLimit the messages limit
     */
    public InitiatingPlayer(
            Messenger messenger, PlayerID playerID, int messagesLimit) {

        super(messenger, playerID, counter -> counter >= messagesLimit);
    }
}
