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

    public InteractionRequest(String titel, String[] choices, Player player, boolean hidden) {
        this.titel = titel;
        this.choices = choices;
        this.player = player;
        this.hidden = hidden;
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
    
    
}
