package com.staxter.player.impl;

import com.staxter.player.api.Player;
import com.staxter.player.api.PlayerException;
import org.junit.Test;

import java.util.function.Function;

/**
 * Defines basic test coverage for ReplyingPlayer - a player implementation.
 */
public class ReplyingPlayerTest extends TestBase {

    private static final String REPLYING_PLAYER_ID = "ReplyingPlayer";

    @Test
    public void shouldReplyToReceivedMessage() throws PlayerException {
        // given
        final Player replyingPlayer = createReplyingPlayer(); // No stop function
        final Player testPlayer = createTestPlayer();

        // when
        registerPlayer(replyingPlayer, testPlayer);

        testPlayer.sendMessage("Hello", replyingPlayer.getPlayerID());

        // then
        assertReceivedMessage("Hello1");
    }

    @Test
    public void shouldReplyToAllReceivedMessages() throws PlayerException {
        // given
        final Player replyingPlayer = createReplyingPlayer(); // No stop function
        final Player testPlayer = createTestPlayer();

        // when
        registerPlayer(replyingPlayer, testPlayer);

        for (int i = 1; i <= 1000; ++ i) {
            testPlayer.sendMessage("Hello", replyingPlayer.getPlayerID());

            // then
            assertReceivedMessage("Hello" + i);
        }
    }

    @Test
    public void shouldReplyToReceivedMessagesUntilLimitReached() throws PlayerException {
        // given
        final int limit = 5;

        final Player replyingPlayer = createReplyingPlayer(i -> i >= limit);
        final Player testPlayer = createTestPlayer();

        // when-then
        assertReceivedMessagesUntilLimitReached(testPlayer, replyingPlayer, limit);
    }

    private Player createReplyingPlayer() {
        return new ReplyingPlayer(messenger, REPLYING_PLAYER_ID);
    }

    private Player createReplyingPlayer(Function<Integer, Boolean> stopFunction) {
        return new ReplyingPlayer(messenger, REPLYING_PLAYER_ID, stopFunction);
    }
}
