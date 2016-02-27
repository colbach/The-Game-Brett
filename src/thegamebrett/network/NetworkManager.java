package thegamebrett.network;

import thegamebrett.Manager;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.network.httpserver.HttpServer;

/**
 * @author Christian Colbach
 */
public class NetworkManager {
    
    protected final UserManager clientManager;
    protected final ControlDirector controlDirector;
    protected final HttpServer httpServer;
    protected final Manager manager;

    public NetworkManager(UserManager clientManager, Manager manager) {
        this.clientManager = clientManager;
        this.controlDirector = new ControlDirector(clientManager);
        if(clientManager != null) {
            this.httpServer = new HttpServer(8123, controlDirector);
            this.httpServer.enableServer();
            System.err.println("clientManager is null! Server not startet");
        } else {
            this.httpServer = null;
        }
        this.manager = manager;
    }
    
    public NetworkManager(Manager manager) {
        this(new UserManager(), manager);
    }
    
    public void deliverMessage(InteractionRequest ir) throws PlayerNotRegisteredException {
        clientManager.deliverMessage(ir);
    }
    
}