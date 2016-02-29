package thegamebrett.mobile;

import thegamebrett.Manager;
import thegamebrett.action.ActionRequest;
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
    
    public MobileManager(Manager manager) {
        this.manager = manager;
        userManager = new UserManager();
        this.networkManager = new NetworkManagerDummy(userManager, manager);
    }

    public void react(MobileRequest mobileRequest) throws PlayerNotRegisteredException {
        if(mobileRequest instanceof InteractionRequest)
            networkManager.deliverMessage((InteractionRequest) mobileRequest);
        else
            throw new UnsupportedOperationException("Not supported yet."); 
    }
}