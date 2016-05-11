package thegamebrett.model;

import java.util.ArrayList;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 * 
 * Dieses Interface gibt an dass es sich um eine GameFactory handelt.
 * Eine Gamefactory soll die statische Methode MADN_GameFactory enthalten
 */
public interface GameFactory {
    
    public Model createGame(ArrayList<User> users) throws TooMuchPlayers, TooFewPlayers;
    
    /* gibt Bild zuruck dieses soll 200x200 sein */
    public String getGameIcon();
    
    public String getGameName();
    
    public int getMaximumPlayers();
    public int getMinimumPlayers();

}
