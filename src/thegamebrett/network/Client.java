package thegamebrett.network;

import java.net.InetAddress;
import thegamebrett.action.request.InteractionRequest;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Benutzer des Systems aus technischer Sicht
 *
 * @author Christian Colbach
 */
public class Client {
    private static AtomicLong messageIdCounter = new AtomicLong(1); // laufender counter
    public long newMessageId() {
        return messageIdCounter.getAndIncrement();
    }
    
    /** Zugehöriger Character */
    private Character character = null;
    
    private long lastSignOfLife = -1; //in ms
    
    public int TIMEOUT = 15000; //in ms
    
    private InetAddress inetAddress;
        
    private InteractionRequest actualInteractionRequest;
    
    private volatile long messageId = -1;
    
    private boolean delivered = false;
        
    public Client(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }
    
    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
    
    public void signOfLife() {
        lastSignOfLife = System.currentTimeMillis();
    }
    
    public boolean isAlife() {
        return lastSignOfLife + TIMEOUT < System.currentTimeMillis();
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }
    
    public boolean matchInetAddress(InetAddress ia) {
        return inetAddress.equals(ia);
    }

    public InteractionRequest getActualInteractionRequest() {
        return actualInteractionRequest;
    }

    public void setActualInteractionRequest(InteractionRequest actualInteractionRequest) {
        this.actualInteractionRequest = actualInteractionRequest;
        this.messageId = newMessageId();
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public long getMessageId() {
        return messageId;
    }
    
    // Client koente eine Funktion haben welche aktuelle HTML-Frage ausgibt (caching)
}
