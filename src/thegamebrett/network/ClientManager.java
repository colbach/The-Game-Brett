package thegamebrett.network;

import java.net.InetAddress;
import java.util.*;

/**
 *
 * @author christiancolbach
 */
public class ClientManager {
    
    private ArrayList<Client> clients = new ArrayList<Client>();
    
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
