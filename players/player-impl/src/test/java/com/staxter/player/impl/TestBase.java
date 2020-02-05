package com.staxter.player.impl;

import com.staxter.player.api.Messenger;
import com.staxter.player.api.Player;
import com.staxter.player.api.PlayerException;

import static org.junit.Assert.*;

/**
 * Abstract, simple base test class common for other test classes.
 */
public abstract class TestBase {

    protected Messenger messenger = new MessengerImpl();
    protected String receivedMessage; // Common for all test players, for simplicity

    /**
     * Simple test player implementation class for testing purposes.
     */
    private class TestPlayer extends AbstractPlayer {
        private TestPlayer(Messenger messenger, String playerID) {
            super(messenger, playerID);
        }

        @Override
        protected void handleReceivedMessage(String message, String senderID) {
            receivedMessage = message;
        }
    }

    protected Player createTestPlayer(String name) {
        return new TestPlayer(messenger, name);
    }

    protected Player createTestPlayer() {
        return createTestPlayer("TestPlayer");
    }

    protected Player registerPlayer(final Player player) throws PlayerException {
        messenger.registerPlayer(player);
        return player;
    }

    protected Player unregisterPlayer(final Player player) throws PlayerException {
        messenger.unregisterPlayer(player);
        return player;
    }

    protected void registerPlayer(Player... players) throws PlayerException {
        for (Player player: players) messenger.registerPlayer(player);
    }

    protected void assertPlayerRegistered(Player player) {
        assertTrue(messenger.isPlayerRegistered(player.getPlayerID()));
    }

    protected void assertPlayerUnregistered(Player player) {
        assertFalse(messenger.isPlayerRegistered(player.getPlayerID()));
    }


    protected String sendMessage(final String message, Player sender, Player receiver)
            throws PlayerException {

        messenger.sendMessage(message, sender.getPlayerID(), receiver.getPlayerID());
        return message;
    }

    protected void assertReceivedMessage(String expectedMessage) {
        assertEquals(expectedMessage, receivedMessage);
    }

    protected void assertReceivedMessagesUntilLimitReached(
            Player testPlayer, Player testedPlayer, int limit) throws PlayerException {

        registerPlayer(testedPlayer, testPlayer);

        for (int i = 1; i < limit; ++ i) {
            testPlayer.sendMessage("Hello", testedPlayer.getPlayerID());

            assertReceivedMessage("Hello" + i);
        }
    }
}
