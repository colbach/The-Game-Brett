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
