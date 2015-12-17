package thegamebrett.network;

import thegamebrett.action.request.InteractionRequest;
import thegamebrett.network.httpserver.HttpServer;

/**
 * @author Christian Colbach
 */
public class NetworkManager {
    
    private final ClientManager clientManager;
    private final ControlDirector controlDirector;
    private final HttpServer httpServer;

    public NetworkManager(ClientManager clientManager) {
        this.clientManager = clientManager;
        this.controlDirector = new ControlDirector(clientManager);
        this.httpServer = new HttpServer(8123, controlDirector);
        this.httpServer.enableServer();
    }
    
    public NetworkManager() {
        this(new ClientManager());
    }
    
    public void deliverMessage(InteractionRequest ir) throws PlayerNotRegisteredException {
        clientManager.deliverMessage(ir);
    }
    
}