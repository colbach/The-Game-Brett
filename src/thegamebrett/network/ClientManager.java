package thegamebrett.network;

import java.net.InetAddress;
import java.util.*;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.model.Player;

/**
 * Managt verschiedene Clients
 * 
 * @author Christian Colbach
 */
public class ClientManager {
    
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
    
    /** Liste in der Devices sich in System anmelden koenen */
    private Client[] systemClients = new Client[systemClientNames.length];
    
    public String getHTMLSystemClientChoser() {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1> Melden sie sich an System an </h1>");
        for(int i=0; i<systemClientNames.length; i++) {
            if (systemClients[i] == null || !systemClients[i].isAlife()) {
                sb.append("<div align=\"center\"><button class=\"choices\" onclick=\"reply('login:" + systemClientIDs[i] + "')\">" + systemClientNames[i] + "</button></div>");
            }
        }
        return sb.toString();
    }
    
    public boolean tryToSetSystemClient(String clientID, Client c) {
        for(int i=0; i<systemClientNames.length; i++) {
            if(clientID.equals(systemClientIDs[i]) && (systemClients[i] == null || !systemClients[i].isAlife())) {
                systemClients[i] = c;
                return true;
            }
        }
        return false;
    }
    
    public boolean isSystemClient(Client c) {
        for(int i=0; i<systemClients.length; i++) {
            if(systemClients[i] == c) {
                return true;
            }
        }
        return false;
    }
    
    private ArrayList<Client> clients = new ArrayList<Client>();
    
    public void deliverMessage(InteractionRequest ir) throws PlayerNotRegisteredException {
        Player p = ir.getPlayer();
        for(Client c : clients) {
            if(p.getUser() == c) {
                c.setActualInteractionRequest(ir);
                return;
            }
        }
        throw new PlayerNotRegisteredException(p);
    }
    
    public Client getClientForInetAddress(InetAddress ia) {
        if(ia == null) {
            throw new IllegalArgumentException("InetAddress must not be null");
        }
        for(Client client : clients) {
            if(client.matchInetAddress(ia)) {
                return client;
            }
        }
        return null;
    }
    
    public Client getOrAddClientForInetAddress(InetAddress ia) {
        if(ia == null) {
            throw new IllegalArgumentException("InetAddress must not be null");
        }
        for(Client client : clients) {
            if(client.matchInetAddress(ia)) {
                return client;
            }
        }
        Client newClient = new Client(ia);
        clients.add(newClient);
        return newClient;
    }
    
}
