package com.staxter.player.impl;

import com.staxter.player.api.Player;
import com.staxter.player.api.PlayerException;
import org.junit.Test;

/**
 * Defines basic test coverage for LocalMessenger - a messenger implementation.
 */
public class MessengerImplTest extends BaseTest {

    @Test
    public void shouldRegisterUnregisteredPlayer() throws PlayerException {
        // given
        final Player playerA = createPlayerA();

        // when
        registerPlayer(playerA);

        // then
        assertPlayerRegistered(playerA);
    }

    @Test
    public void shouldRegisterTwoUnregisteredPlayers() throws PlayerException {
        // given
        final Player playerA = createPlayerA();
        final Player playerB = createPlayerB();

        // when
        registerPlayer(playerA, playerB);

        // then
        assertPlayerRegistered(playerA);
        assertPlayerRegistered(playerB);
    }

    @Test
    public void shouldUnregisterRegisteredPlayerPlayer() throws PlayerException {
        // given
        final Player playerA = createPlayerA();

        // when
        registerPlayer(playerA);
        assertPlayerRegistered(playerA);

        // then
        unregisterPlayer(playerA);
        assertPlayerUnregistered(playerA);
    }

    @Test(expected = PlayerException.class)
    public void shouldFailRegisteringRegisteredPlayer() throws PlayerException {
        // given
        final Player playerA = createPlayerA();

        // when
        registerPlayer(playerA);
        registerPlayer(playerA); // then expect PlayerException
    }

    @Test(expected = PlayerException.class)
    public void shouldFailUnregisteringUnregisteredPlayer() throws PlayerException {
        // given

        // when
        unregisterPlayer(createPlayerA()); // then expect PlayerException
    }

    @Test(expected = PlayerException.class)
    public void shouldFailUnregisteringRegisteredAndUnregisteredPlayer() throws PlayerException {
        // given
        final Player playerA = createPlayerA();

        // when
        unregisterPlayer(registerPlayer(playerA));

        unregisterPlayer(playerA); // then expect PlayerException
    }

    @Test
    public void shouldSendMessageBetweenPlayers() throws PlayerException {
        // given
        final Player playerA = createPlayerA();
        final Player playerB = createPlayerB();

        // when
        registerPlayer(playerA, playerB);

        // then
        assertReceivedMessage(sendMessage(
                createMessage(playerA, playerB, "Hello")));
    }

    @Test(expected = PlayerException.class)
    public void shouldFailSendingMessageToUnregisteredPlayer() throws PlayerException {
        // given
        final Player playerA = createPlayerA();
        final Player playerB = createPlayerB();

        // when
        registerPlayer(playerA);
        sendMessage(createMessage(playerA, playerB, "Hello")); // then expect PlayerException
    }

    @Test
    public void shouldSendAndReplyMessageBetweenPlayers() throws PlayerException {
        // given
        final Player playerA = createPlayerA();
        final Player playerB = createPlayerB();

        // when
        registerPlayer(playerA, playerB);

        // then
        assertReceivedMessage(sendMessage(
                createMessage(playerA, playerB, "HelloA")));

        assertReceivedMessage(sendMessage(
                createMessage(playerB, playerA, "HelloB")));
    }

    // common utility test methods

    private Player createPlayerA() { return createTestPlayer("PlayerA"); }

    private Player createPlayerB() { return createTestPlayer("PlayerB"); }
}
