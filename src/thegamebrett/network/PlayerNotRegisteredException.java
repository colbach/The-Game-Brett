package thegamebrett.network;

/**
 * Fehler wird geworfen falls Player nicht in System angemeldet ist
 * 
 * @author christiancolbach
 */
public class PlayerNotRegisteredException extends Exception {
    
    @Override
    public String getMessage() {
        return "Player is not registered in the System";
    }
}
