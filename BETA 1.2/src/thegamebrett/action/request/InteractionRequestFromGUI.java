package thegamebrett.action.request;

import thegamebrett.model.Player;

/**
 *
 * @author christiancolbach
 */
public class InteractionRequestFromGUI extends InteractionRequest {
    
    public InteractionRequestFromGUI(String titel, Object[] choices, Player player, boolean hidden, Object userData) {
        super(titel, choices, player, hidden, userData);
    }
    
}
