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

    private Manager manager;

    private String[] systemClientNames = new String[]{
        "Spieler 1",
        "Spieler 2",
        "Spieler 3",
        "Spieler 4"
    };
    private String[] systemClientIDs = new String[]{
        "systemClient1",
        "systemClient2",
        "systemClient3",
        "systemClient4"
    };

    /**
     * Liste in der Devices sich in System anmelden koenen
     */
    protected User[] systemClients = new User[systemClientNames.length];

    public UserManager(Manager manager) {
        this.manager = manager;
    }

    public String getHTMLSystemClientChoser() {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1> Melden sie sich an System an </h1>");
        for (int i = 0; i < systemClientNames.length; i++) {
            if (systemClients[i] == null || !systemClients[i].isAlife()) {
                sb.append("<div align=\"center\"><button class=\"choices\" onclick=\"reply('login:" + systemClientIDs[i] + "')\">" + systemClientNames[i] + "</button></div>");
            }
        }
        return sb.toString();
    }

    public boolean tryToSetSystemClient(String clientID, User c) {
        for (int i = 0; i < systemClientNames.length; i++) {
            if (clientID.equals(systemClientIDs[i]) && (systemClients[i] == null || !systemClients[i].isAlife())) {
                systemClients[i] = c;
                return true;
            }
        }
        return false;
    }

    public boolean isSystemClient(User c) {
        for (int i = 0; i < systemClients.length; i++) {
            if (systemClients[i] == c) {
                return true;
            }
        }
        return false;
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

}
