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

    public InteractionRequest(String titel, String[] choices, Player player, boolean hidden) {
        this(titel, choices, "", player, hidden);
    }
    
    public InteractionRequest(String titel, String[] choices, String acknowledgment, Player player, boolean hidden) {
        this.titel = titel;
        this.choices = choices;
        this.player = player;
        this.hidden = hidden;
        this.acknowledgment = acknowledgment;
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
    
}
