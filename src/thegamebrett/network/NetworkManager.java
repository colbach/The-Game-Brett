package thegamebrett.network;

import thegamebrett.network.httpserver.HttpServer;

/**
 * @author Christian Colbach
 */
public class NetworkManager {
    
    ClientManager clientManager;
    ControlDirector controlDirector;
    HttpServer httpServer;

    public NetworkManager(ClientManager clientManager) {
        this.clientManager = clientManager;
        this.controlDirector = new ControlDirector(clientManager);
        this.httpServer = new HttpServer(8123, controlDirector);
        this.httpServer.enableServer();
    }
    
    public NetworkManager() {
        this(new ClientManager());
    }
    
    
}