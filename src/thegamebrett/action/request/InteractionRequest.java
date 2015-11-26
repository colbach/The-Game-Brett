package thegamebrett.action.request;

import thegamebrett.model.Player;

/**
 * @author Christian Colbach
 */
public class InteractionRequest implements GUIRequest, MobileRequest {
    
    private final String titel;
    private final String[] choices;
    private final Player player;
    private final boolean hidden;
    private final String acknowledgment;
    private final int delay;

    public InteractionRequest(String titel, String[] choices, Player player, boolean hidden) {
        this(titel, choices, player, hidden, "", 0);
    }
    
    public InteractionRequest(String titel, String[] choices, Player player, boolean hidden, String acknowledgment, int delay) {
        this.titel = titel;
        this.choices = choices;
        this.player = player;
        this.hidden = hidden;
        this.acknowledgment = acknowledgment;
        this.delay = delay;
    }

    public String getTitel() {
        return titel;
    }

    public String[] getChoices() {
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
    
}
