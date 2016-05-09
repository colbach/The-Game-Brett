package thegamebrett.action.request;

import thegamebrett.model.Layout;
import thegamebrett.model.Player;

public class ScreenMessageRequest implements GUIRequest {
    
    private final String label;
    private final Player player;

    public ScreenMessageRequest(String label, Player player) {
        this.label = label;
        this.player = player;
    }

    public ScreenMessageRequest(String label) {
        this(label, null);
    }

    public String getLabel() {
        return label;
    }

    public Player getPlayer() {
        return player;
    }
    
    public boolean isGeneral() {
        return player == null;
    }
    
}
