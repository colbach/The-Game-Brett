
package thegamebrett.game.MADN;

import thegamebrett.model.Player;
import thegamebrett.network.User;

/**
 * @author Korè
 */
public class MADN_Player extends Player{
    private User user;
    private int playerNr;
    private MADN_Figure[] figures;
    //das erste feld nach den startfeldern
    private MADN_Field firstField;
    //das letzte feld vor den endfeldern
    private MADN_Field lastField;
    
    public MADN_Player(User user, int playerNr, MADN_Figure[] figures, MADN_Field firstField, MADN_Field lastField){
        super(user);
        this.playerNr = playerNr;
        this.figures = figures;
        this.firstField = firstField;
        this.lastField = lastField;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }

    @Override
    public MADN_Figure[] getFigures() {
        return figures;
    }

    public void setFigures(MADN_Figure[] figures) {
        this.figures = figures;
    }

    public MADN_Field getFirstField() {
        return firstField;
    }

    public MADN_Field getLastField() {
        return lastField;
    }
    
    
}
