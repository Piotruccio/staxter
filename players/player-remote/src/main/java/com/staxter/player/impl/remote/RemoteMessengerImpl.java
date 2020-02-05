package com.staxter.player.impl.remote;

import com.staxter.player.api.PlayerException;
import com.staxter.player.impl.MessengerImpl;

import java.net.InetSocketAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

import static java.rmi.registry.LocateRegistry.createRegistry;
import static java.rmi.registry.LocateRegistry.getRegistry;
import static java.rmi.server.UnicastRemoteObject.exportObject;
import static java.rmi.server.UnicastRemoteObject.unexportObject;

/**
 * A simple messenger implementation that allows for sending messages between the
 * registered players remotely (within separate Java process) via RMI.
 */
public class RemoteMessengerImpl extends MessengerImpl implements RemoteMessenger {

    public static final String REMOTE_MESSENGER_ID = "Messenger";

    private final InetSocketAddress remoteAddress;
    private final Registry registry;
    private final AtomicReference<RemoteMessenger> messenger;

    /**
     * Constructs a new remote messenger instance.
     *
     * @param localAddress local address of this messenger to set
     * @param remoteAddress address of the remote messenger to set
     * @throws RemoteException when unable to create RMI registry or export
     *      object
     */
    public RemoteMessengerImpl(InetSocketAddress localAddress, InetSocketAddress remoteAddress)
            throws RemoteException {

        this.remoteAddress = remoteAddress;

        registry = createRegistry(localAddress.getPort());
        registry.rebind(REMOTE_MESSENGER_ID, exportObject(this, 0));

        messenger = new AtomicReference<>();

        getLogger().log(Level.INFO, () -> "Registered remote messenger in RMI registry");
    }

    @Override
    public void sendMessage(String message, String senderID, String receiverID)
            throws PlayerException {

        try {
            getRemoteMessenger().sendRemoteMessage(message, senderID, receiverID);

        } catch (RemoteException | NotBoundException e) {
            getLogger().log(Level.SEVERE, () -> "Failed to send message: " + e.getMessage());

            throw new PlayerException("Failed to send message", e); // Rethrow
        }
    }

    @Override
    public void stop() { stop(true); }

    private void stop(boolean stopRemote) {
        super.stop(); // Cleanup the resources (registered players)

        try {
            unexportObject(this, true);
            registry.unbind(REMOTE_MESSENGER_ID);

            getLogger().log(Level.INFO, () ->
                    "Unregistered remote messenger from RMI registry");

        } catch (RemoteException | NotBoundException e) {
            getLogger().log(Level.WARNING, () ->
                    "Failed to unregister remote messenger from RMI registry");
        }
        if (!stopRemote) return; // We're done

        try {
            getRemoteMessenger().stopRemote(); // Delegate stop to remote as needed

        } catch (RemoteException | NotBoundException e) {
            getLogger().log(Level.SEVERE, () ->
                    "Failed to stop remote messenger: " + e.getMessage());
        }
    }

    @Override
    public void sendRemoteMessage(String message, String senderID, String receiverID)
            throws PlayerException {

        getLogger().log(Level.INFO, () -> "Executing send remote message command");
        super.sendMessage(message, senderID, receiverID);
    }

    @Override
    public void stopRemote() {
        getLogger().log(Level.INFO, () -> "Executing stop remote command");

        stop(false);
    }

    private RemoteMessenger getRemoteMessenger() throws RemoteException, NotBoundException {
        if (messenger.get() != null) return messenger.get();

        return (RemoteMessenger) getRegistry(remoteAddress.getHostString(),
                remoteAddress.getPort()).lookup(REMOTE_MESSENGER_ID);
    }
}
