package com.staxter.player.impl;

import com.staxter.player.api.Message;
import com.staxter.player.api.Player;
import com.staxter.player.api.PlayerID;

import java.io.Serializable;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Simple POJO implementation representing a message that can be sent between
 * the two players.
 */
public class MessageImpl implements Message, Serializable {

    private final PlayerID senderID, receiverID; // All fields are immutable
    private final String messageContent;

    /**
     * Constructs a new instance of local message with given senderID and
     * receiverID IDs, and message content.
     *
     * @param senderID the message sender ID to set, cannot be null
     * @param receiverID the message receiver ID to set, cannot be null
     * @param messageContent the message content to set, cannot be null
     */
    public MessageImpl(
            PlayerID senderID, PlayerID receiverID, String messageContent) {

        requireNonNull(senderID, "senderID cannot be null");
        requireNonNull(receiverID, "receiverID cannot be null");
        requireNonNull(messageContent, "messageContent cannot be null");

        this.senderID = senderID;
        this.receiverID = receiverID;
        this.messageContent = messageContent;
    }

    /**
     * Constructs a new instance of local message with given sender, receiver
     * and message content.
     *
     * @param sender the message sender to get sender ID from, cannot be null
     * @param receiver the message receiver to get receiver ID, cannot be null
     * @param messageContent the message content to set, cannot be null
     */
    public MessageImpl(Player sender, Player receiver, String messageContent) {
        this(sender.getPlayerID(), receiver.getPlayerID(), messageContent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageImpl that = (MessageImpl) o;
        return senderID.equals(that.senderID) &&
                receiverID.equals(that.receiverID) &&
                messageContent.equals(that.messageContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderID, receiverID, messageContent);
    }

    @Override
    public PlayerID getSenderID() { return senderID; }

    @Override
    public PlayerID getReceiverID() { return receiverID; }

    @Override
    public String getMessageContent() { return messageContent; }
}
