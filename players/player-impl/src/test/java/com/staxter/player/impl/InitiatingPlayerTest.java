package com.staxter.player.impl;

import com.staxter.player.api.Player;
import com.staxter.player.api.PlayerException;
import org.junit.Test;

/**
 * Defines basic test coverage for InitiatingPlayer - a player implementation.
 */
public class InitiatingPlayerTest extends TestBase {

    @Test
    public void shouldReplyToReceivedMessagesUntilLimitReached() throws PlayerException {
        // given
        final int limit = 10;

        final Player initiatingPlayer = createInitiatingPlayer(limit);
        final Player testPlayer = createTestPlayer();

        // when-then
        assertReceivedMessagesUntilLimitReached(testPlayer, initiatingPlayer, limit);
    }

    private Player createInitiatingPlayer(int messagesLimit) {
        return new InitiatingPlayer(messenger, "InitiatingPlayer", messagesLimit);
    }
}
