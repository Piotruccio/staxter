package com.staxter.player.impl;

import com.staxter.player.api.Message;
import com.staxter.player.api.Messenger;
import com.staxter.player.api.Player;
import com.staxter.player.api.PlayerException;
import com.staxter.player.api.PlayerID;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

/**
 * Abstract, simple base test class common for other test classes.
 */
public abstract class BaseTest {

    protected Messenger messenger = new MessengerImpl();
    protected Message receivedMessage; // Common for all test players, for simplicity

//    protected Messenger createMessenger() { return new TestMessenger(); }

    /**
     * Simple test player implementation class for testing purposes.
     */
    private class TestPlayer extends AbstractPlayer {
        private TestPlayer(Messenger messenger, PlayerID playerID) {
            super(messenger, playerID);
        }

        @Override
        protected void handleReceivedMessage(Message message) {
            receivedMessage = message;
        }
    }

//    /**
//     * Simple test messenger implementation class for testing purposes.
//     */
//    private class TestMessenger implements Messenger {
//        private final Map<PlayerID, Player> players = new HashMap<>();
//
//        @Override
//        public void registerPlayer(Player player) throws PlayerException {
//            if (players.putIfAbsent(player.getPlayerID(), player) != null)
//                throw new PlayerException("Player already registered");
//        }
//
//        @Override
//        public void unregisterPlayer(Player player) throws PlayerException {
//            if (players.remove(player.getPlayerID()) == null)
//                throw new PlayerException("Player not registered");
//        }
//
//        @Override
//        public boolean isPlayerRegistered(PlayerID playerID) {
//            return players.get(playerID) != null;
//        }
//
//        @Override
//        public void sendMessage(Message message) throws PlayerException {
//            Player receiver = players.get(message.getReceiverID());
//            if (receiver == null)
//                throw new PlayerException("Player not registered");
//
//            receiver.receiveMessage(message);
//        }
//
//        @Override
//        public void stop() { players.clear(); }
//
//        @Override
//        public boolean isStopped() { return false; }
//    }

    protected Player createTestPlayer(String name) {
        return new TestPlayer(messenger, new PlayerIDImpl(name));
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


    protected Message createMessage(Player sender, Player receiver, String message) {
        return new MessageImpl(sender.getPlayerID(), receiver.getPlayerID(), message);
    }

    protected Message sendMessage(final Message message) throws PlayerException {
        messenger.sendMessage(message);
        return message;
    }

    protected void assertReceivedMessage(Message expectedMessage) {
        assertEquals(expectedMessage, receivedMessage);
    }

    protected void assertReceivedMessagesUntilLimitReached(
            Player testPlayer, Player testedPlayer, int limit) throws PlayerException {

        registerPlayer(testedPlayer, testPlayer);

        for (int i = 1; i < limit; ++ i) {
            testPlayer.sendMessage(
                    createMessage(testPlayer, testedPlayer, "Hello"));

            assertReceivedMessage(
                    createMessage(testedPlayer, testPlayer, "Hello" + i));
        }
    }
}
