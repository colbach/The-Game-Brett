package thegamebrett.action.request;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import thegamebrett.model.Player;

/**
 * @author Christian Colbach
 */
public class InteractionRequest implements GUIRequest, MobileRequest {
    
    private final String titel;
    //alle Objekte brauchen eine implementierte toString methode fuer die GUI
    private final Object[] choices;
    private final Player player;
    private final boolean hidden;
    private final String acknowledgment;
    private final int delay;
    private final int messageId;
    private final String messageIdAs9CharacterString;
    private static volatile AtomicInteger messageIdCounter = new AtomicInteger(0);
    private final Object userData;

    public InteractionRequest(String titel, Object[] choices, Player player, boolean hidden, Object userData) {
        this(titel, choices, player, hidden, "", 0, userData);
    }
    
    public InteractionRequest(String titel, Object[] choices, Player player, boolean hidden, String acknowledgment, int delay, Object userData) {
        this.titel = titel;
        this.choices = choices;
        this.player = player;
        this.hidden = hidden;
        this.acknowledgment = acknowledgment;
        this.delay = delay;
        this.messageId = messageIdCounter.addAndGet(1);
        this.messageIdAs9CharacterString = intAs9CharacterString(this.messageId);
        this.userData = userData;
    }

    public String getTitel() {
        return titel;
    }

    public Object[] getChoices() {
        return choices;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isHidden() {
        return hidden;
    }

    public String getAcknowledgment() {
        return acknowledgment;
    }
    
    public int getDelay() {
        return delay;
    }

    public int getMessageId() {
        return messageId;
    }
    
    public boolean matchMessageId(int messageId) {
        return this.messageId == messageId;
    }
    
    public boolean matchMessageId(String messageId) {
        try {
            if(messageId.equals("#########")) {
                return false;
            } else {
                return matchMessageId(Integer.valueOf(messageId));
            }
        } catch (NumberFormatException numberFormatException) {
            System.err.println("messageId=\"" + messageId + "\" is not a legal String");
            return false;
        }
    }
    
    public String getMessageIdAs9CharacterString() {
        return messageIdAs9CharacterString;
    }
    
    private static String intAs9CharacterString(int i) {
       String s = String.valueOf(i);
        while(s.length() < 9) {
            s = "0" + s;
        }
        return s;
    }

    public Object getUserData() {
        return userData;
    }

}
