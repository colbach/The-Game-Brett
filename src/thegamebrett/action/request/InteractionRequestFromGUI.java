package thegamebrett.action.request;

import thegamebrett.model.Player;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class InteractionRequestFromGUI extends InteractionRequest {
    
    public InteractionRequestFromGUI(String titel, Object[] choices, Player player, boolean hidden, Object userData) {
        super(titel, choices, player, hidden, userData);
    }
    
}
