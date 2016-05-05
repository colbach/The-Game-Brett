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
    
    public int TIMEOUT = 30000; //in ms (1s = 1000ms)
    
    // Achtung: Werte Niemals aendern da sie in Webseiten hard-gecodet sind
    public static final int WEB_PAGE_CHOOSE_POSITION = 0;
    public static final int WEB_PAGE_CHOOSE_CHARACTER = 1;
    public static final int WEB_PAGE_CHOOSE_GAME = 2;
    public static final int WEB_PAGE_WAIT = 3;
    public static final int WEB_PAGE_PLAY_GAME = 4;
    public static final int WEB_PAGE_GAME_ALREADY_STARTED = 5;
    public static final int WEB_PAGE_PREFERENCES = 6;
    public static final int WEB_PAGE_START_GAME = 7;
    public static final int WEB_PAGE_JOIN_GAME = 8;
    
    private Manager manager;

    /**
     * Aktuelle Position des Spielers im System.
     * Auswahl:
     *    WEB_PAGE_CHOOSE_POSITION,    WEB_PAGE_CHOOSE_CHARACTER,  WEB_PAGE_CHOOSE_GAME
     *    WEB_PAGE_WAIT,               WEB_PAGE_PLAY_GAME,         WEB_PAGE_PREFERENCES
     */
    private volatile int webPage = WEB_PAGE_CHOOSE_POSITION;
    
    /**
     * Zugehöriger Character
     */
    private volatile UserCharacter character = null;

    private static volatile AtomicLong lastClientId = new AtomicLong(0);
    
    private final long clientId;

    private volatile long lastSignOfLife = -1; //in ms

    private volatile InetAddress inetAddress;

    private volatile InteractionRequest actualInteractionRequest;

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
    
    public boolean hasUserCharacter() {
        return character == null;
    }
    
    public void removeUserCharacter() {
        if(character != null) {
            character.setInUse(false);
            character = null;
        }
    }

    public boolean tryToSetUserCharacter(UserCharacter character) {
        if(character.tryToUse()) {
            this.character = character;
            return true;
        } else {
            return false;
        }
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

    public long getMessageId() {
        if (actualInteractionRequest != null) {
            return actualInteractionRequest.getMessageId();
        } else {
            return -1;
        }
    }

    public synchronized String getActualGameHTML() {
        if (htmlCache != null) {
            return htmlCache;
        } else {
            htmlCache = WebGenerator.generateHTMLContent(
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

    public int getWebPage() {
        return webPage;
    }

    public void setWebPage(int webPage) {
        this.webPage = webPage;
    }

    public String toString() {
        if (inetAddress != null) {
            return "Client id=" + clientId + " address=" + InetAddressFormatter.formatAddress(inetAddress);
        } else {
            return "Client id=" + clientId;
        }
    }
}
