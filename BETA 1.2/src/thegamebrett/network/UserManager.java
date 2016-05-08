package thegamebrett.network;

import java.net.InetAddress;
import java.util.*;
import thegamebrett.Manager;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.model.Player;

/**
 * Managt verschiedene Clients
 *
 * @author Christian Colbach
 */
public class UserManager {

    protected Manager manager;
    

    public static final String[] SYSTEM_CLIENT_NAMES = new String[] {
        "Spieler 1",
        "Spieler 2",
        "Spieler 3",
        "Spieler 4",
        "Spieler 5",
        "Spieler 6",
        "Spieler 7",
        "Spieler 8"
    };
    public static final String[] SYSTEM_CLIENT_IDS = new String[]{
        "systemClient0",
        "systemClient1",
        "systemClient2",
        "systemClient3",
        "systemClient4",
        "systemClient5",
        "systemClient6",
        "systemClient7"
    };

    /**
     * Liste in der Devices sich in System anmelden koenen
     */
    protected User[] systemClients = new User[SYSTEM_CLIENT_NAMES.length];

    public UserManager(Manager manager) {
        this.manager = manager;
    }

    public String generateSystemClientChooserAvailabilityInfoForAPI() {
        return WebGenerator.generateSystemClientChooserAvailabilityInfoForAPI(this);
    }
    
    public String generateFreePositionHTML() {
        return WebGenerator.generateFreePositionHTML(this);
    }

    public boolean tryToSetSystemClient(String clientID, User c) {
        
        for (int i = 0; i < SYSTEM_CLIENT_NAMES.length; i++) {
            if (clientID.equals(SYSTEM_CLIENT_IDS[i]) && (systemClients[i] == null || !systemClients[i].isAlife())) {
                if(systemClients[i] != null) {
                    systemClients[i].setSittingPlace(-1);
                }
                systemClients[i] = c;
                systemClients[i].setSittingPlace(i);
                return true;
            }
        }
        return false;
    }
    
    public boolean tryToReplaceSystemClient(String clientID, InetAddress ia) {
        final NetworkGameSelector ngs = manager.getMobileManager().getNetworkManager().getNetworkGameSelector();
        
        for (int i = 0; i < SYSTEM_CLIENT_NAMES.length; i++) {
            if (clientID.equals(SYSTEM_CLIENT_IDS[i]) && !systemClients[i].isAlife()) {
                systemClients[i].setInetAddress(ia);
                return true;
            }
        }
        return false;
    }
    
    public boolean canSetSystemClient(String id) {
        for(int i=0; i<SYSTEM_CLIENT_IDS.length; i++) {
            if(SYSTEM_CLIENT_IDS[i].equals(id)) {
                return canSetSystemClient(i);
            }
        }
        System.err.println("Ungueltige SystemClient-ID");
        return false;
    }
    
    public boolean canSetSystemClient(int index) {
        /*if(Math.random() > 0.7)
            return false;*/ // zum testen
        return systemClients[index] == null || !systemClients[index].isAlife();
    }
    
    public boolean[] canSetSystemClientArray() {
        boolean[] bs = new boolean[SYSTEM_CLIENT_IDS.length];
        for(int i=0; i<bs.length; i++) {
            bs[i] = canSetSystemClient(i);
        }
        return bs;
    }

    public boolean isSystemClient(User c) {
        for (int i = 0; i < systemClients.length; i++) {
            if (systemClients[i] == c) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isSystemClient(InetAddress ia) {
        for (User systemClient : systemClients) {
            if (systemClient!=null && systemClient.matchInetAddress(ia)) {
                return true;
            }
        }
        return false;
    }
    
    public void logOutSystemClient(User c) {
        for (int i = 0; i < systemClients.length; i++) {
            if (systemClients[i] == c) {
                if(systemClients[i] != null) {
                    systemClients[i].setSittingPlace(-1);
                }
                systemClients[i] = null;
            }
        }
    }

    private ArrayList<User> clients = new ArrayList<User>();

    public void deliverMessage(InteractionRequest ir) throws PlayerNotRegisteredException {
        Player p = ir.getPlayer();
        User u = p.getUser();
        synchronized (clients) {
            for (User c : clients) {
                if (u == c) {
                    c.setActualInteractionRequest(ir);
                    return;
                }
            }
        }
        throw new PlayerNotRegisteredException(p);
    }

    public synchronized User getClientForInetAddress(InetAddress ia) {
        if (ia == null) {
            throw new IllegalArgumentException("InetAddress must not be null");
        }
        for (User client : clients) {
            if (client.matchInetAddress(ia)) {
                return client;
            }
        }
        return null;
    }

    public synchronized User getOrAddClientForInetAddress(InetAddress ia) {
        if (ia == null) {
            throw new IllegalArgumentException("InetAddress must not be null");
        }
        for (User client : clients) {
            if (client.matchInetAddress(ia)) {
                return client;
            }
        }
        User newClient = new User(ia, manager);
        clients.add(newClient);
        return newClient;
    }

    public User[] getSystemClients() {
        return systemClients;
    }

    public Manager getManager() {
        return manager;
    }
    
    
}
