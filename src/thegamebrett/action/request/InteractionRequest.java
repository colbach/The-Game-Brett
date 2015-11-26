package thegamebrett.action.request;

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
    private final long messageId;
    private static long messageIdCounter = 0;
    private final Object userData;

    public InteractionRequest(String titel, Object[] choices, Player player, boolean hidden) {
        this(titel, choices, player, hidden, "", 0, null);
    }
    
    public InteractionRequest(String titel, Object[] choices, Player player, boolean hidden, String acknowledgment, int delay, Object userData) {
        this.titel = titel;
        this.choices = choices;
        this.player = player;
        this.hidden = hidden;
        this.acknowledgment = acknowledgment;
        this.delay = delay;
        this.messageId = ++messageIdCounter;
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

    public long getMessageId() {
        return messageId;
    }

    public Object getUserData() {
        return userData;
    }

}
