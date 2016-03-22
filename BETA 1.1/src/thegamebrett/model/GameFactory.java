package thegamebrett.model;

import java.util.ArrayList;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;
import javafx.scene.image.Image;

/**
 * Dieses Interface gibt an dass es sich um eine GameFactory handelt.
 * Eine Gamefactory soll die statische Methode MADN_GameFactory enthalten
 * 
 * @author Christian Colbach
 */
public interface GameFactory {
    
    public Model createGame(ArrayList<User> users) throws TooMuchPlayers, TooFewPlayers;
    
    /* gibt Quadratisches Bild zuruck */
    public Image getGameIcon();
    
    public String getGameName();

}
