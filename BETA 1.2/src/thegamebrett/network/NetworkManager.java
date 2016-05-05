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
    protected final NetworkGameSelector networkGameSelector;
    
    
    public NetworkManager(UserManager userManager, Manager manager) {
        this.clientManager = userManager;
        this.networkGameSelector = new NetworkGameSelector(manager);
        this.controlDirector = new ControlDirector(userManager);
        if(userManager != null) {
            this.httpServer = new HttpServer(8123, controlDirector);
            this.httpServer.enableServer();
        } else {
            this.httpServer = null;
            System.err.println("userManager is null! Server not startet");
        }
        this.manager = manager;
    }
    
    public NetworkManager(Manager manager) {
        this(new UserManager(manager), manager);
    }
    
    public void deliverMessage(InteractionRequest ir) throws PlayerNotRegisteredException {
        clientManager.deliverMessage(ir);
    }

    public NetworkGameSelector getNetworkGameSelector() {
        return networkGameSelector;
    }
    
}