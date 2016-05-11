package thegamebrett.mobile;

import thegamebrett.Manager;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.action.request.MobileRequest;
import thegamebrett.network.NetworkManager;
import thegamebrett.network.NetworkManagerDummy;
import thegamebrett.network.PlayerNotRegisteredException;
import thegamebrett.network.UserManager;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class MobileManager {

    private final Manager manager;
    private final NetworkManager networkManager;
    private final UserManager userManager;

    private static final boolean USE_DUMMY_NETWORK_MANAGER = false; // fuer Debuging

    public MobileManager(Manager manager) {
        this.manager = manager;
        userManager = new UserManager(manager);
        if (USE_DUMMY_NETWORK_MANAGER) {
            this.networkManager = new NetworkManagerDummy(userManager, manager);
        } else {
            this.networkManager = new NetworkManager(userManager, manager);
        }
    }

    public void react(MobileRequest mobileRequest) throws PlayerNotRegisteredException {
        if (mobileRequest instanceof InteractionRequest) {
            networkManager.deliverMessage((InteractionRequest) mobileRequest);
        } else {
            System.err.println("Not supported yet.");
        }
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
