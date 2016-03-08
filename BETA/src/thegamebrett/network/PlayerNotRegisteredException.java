package thegamebrett.network;

import thegamebrett.model.Player;

/**
 * Fehler wird geworfen falls Player nicht in System angemeldet ist
 * 
 * @author christiancolbach
 */
public class PlayerNotRegisteredException extends Exception {
    
    private final Player player;

    public PlayerNotRegisteredException(Player player) {
        this.player = player;
    }
            
            
    
    @Override
    public String getMessage() {
        return "Player is not registered in the System";
    }
}
