package com.staxter.player.impl.remote;

import com.staxter.player.api.Message;
import com.staxter.player.api.PlayerException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Represents a remote interface, which allows executing commands remotely.
 */
public interface RemoteMessenger extends Remote {

    /**
     * Executes the send message command on the remote messenger for the given
     * message.
     *
     * @param message the message to send
     * @throws RemoteException when the command failed to complete due remote
     *      issue
     * @throws PlayerException when the player given by player ID is not
     *      registered
     */
    void sendRemoteMessage(Message message) throws RemoteException, PlayerException;

    /**
     * Executes the stop command on the remote messenger.
     *
     * @throws RemoteException when the command failed to complete due remote
     *      issue
     */
    void stopRemote() throws RemoteException;
}
