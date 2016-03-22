package thegamebrett.mobile;

import thegamebrett.Manager;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.action.request.MobileRequest;
import thegamebrett.network.NetworkManager;
import thegamebrett.network.NetworkManagerDummy;
import thegamebrett.network.PlayerNotRegisteredException;
import thegamebrett.network.UserManager;

/**
 * @author Christian Colbach
 */
public class MobileManager {
    
    private Manager manager;
    private NetworkManager networkManager;
    private UserManager userManager;
    
    private static final boolean USE_DUMMY_NETWORK_MANAGER = true; // fuer Debuging
    
    public MobileManager(Manager manager) {
        this.manager = manager;
        userManager = new UserManager(manager);
        if(USE_DUMMY_NETWORK_MANAGER) {
            this.networkManager = new NetworkManagerDummy(userManager, manager);
        } else {
            this.networkManager = new NetworkManager(userManager, manager);
        }
    }

    public void react(MobileRequest mobileRequest) throws PlayerNotRegisteredException {
        if(mobileRequest instanceof InteractionRequest)
            networkManager.deliverMessage((InteractionRequest) mobileRequest);
        else
            throw new UnsupportedOperationException("Not supported yet."); 
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}