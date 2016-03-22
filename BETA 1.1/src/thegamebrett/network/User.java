package thegamebrett.network;

import java.net.InetAddress;
import thegamebrett.network.httpserver.InetAddressFormatter;
import thegamebrett.action.request.InteractionRequest;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import thegamebrett.Manager;
import thegamebrett.usercharacter.UserCharacter;
import thegamebrett.action.response.InteractionResponse;

/**
 * Benutzer des Systems aus technischer Sicht
 *
 * @author Christian Colbach
 */
public class User {

    private Manager manager;

    /**
     * Zugehöriger Character
     */
    private UserCharacter character = null;

    private static volatile AtomicLong lastClientId = new AtomicLong(0);

    private final long clientId;

    private volatile long lastSignOfLife = -1; //in ms

    public int TIMEOUT = 30000; //in ms (1s = 1000ms)

    private volatile InetAddress inetAddress;

    private volatile InteractionRequest actualInteractionRequest;

    private volatile boolean delivered = false;

    private String htmlCache = null;

    public User(InetAddress inetAddress, Manager m) {
        this.inetAddress = inetAddress;
        this.manager = m;
        clientId = lastClientId.addAndGet(1);
    }

    public long getClientId() {
        return clientId;
    }

    public UserCharacter getUserCharacter() {
        return character;
    }

    public void setUserCharacter(UserCharacter character) {
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

    //???Kann man so vergleichen
    public boolean matchInetAddress(InetAddress ia) {
        return inetAddress.equals(ia);
    }

    public InteractionRequest getActualInteractionRequest() {
        return actualInteractionRequest;
    }

    public synchronized void setActualInteractionRequest(InteractionRequest actualInteractionRequest) {
        this.actualInteractionRequest = actualInteractionRequest;
        htmlCache = null;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public long getMessageId() {
        if (actualInteractionRequest != null) {
            return actualInteractionRequest.getMessageId();
        } else {
            return -1;
        }
    }

    public synchronized String getActualHTML() {
        if (htmlCache != null) {
            return htmlCache;
        } else {
            htmlCache = HTMLHelper.generateHTMLContent(
                    actualInteractionRequest.getTitel(),
                    actualInteractionRequest.getChoices(),
                    actualInteractionRequest.getMessageId());
            return htmlCache;
        }
    }

    public void replyFromHTTP(int messageID, int answerID) {
        if (actualInteractionRequest != null && actualInteractionRequest.getMessageId() == messageID) {
            InteractionResponse response = new InteractionResponse(actualInteractionRequest, answerID);
            actualInteractionRequest = null;
            manager.react(response);

        } else {
            System.err.println("Resonse doen't match Request");
        }

    }

    public String toString() {
        if (inetAddress != null) {
            return "Client id=" + clientId + " address=" + InetAddressFormatter.formatAddress(inetAddress);
        } else {
            return "Client id=" + clientId;
        }
    }
}
