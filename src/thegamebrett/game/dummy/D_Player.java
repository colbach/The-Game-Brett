
package thegamebrett.game.dummy;

import thegamebrett.game.dummy.*;
import thegamebrett.model.Player;

/**
 * @author Korè
 */
public class D_Player extends Player{
    private int playerNr;
    private D_Figure[] figures;
    //das erste feld nach den startfeldern
    private D_Field firstField;
    //das letzte feld vor den endfeldern
    private D_Field lastField;
    
    public D_Player(int playerNr, D_Figure[] figures, D_Field firstField, D_Field lastField){
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
    public D_Figure[] getFigures() {
        return figures;
    }

    public void setFigures(D_Figure[] figures) {
        this.figures = figures;
    }

    public D_Field getFirstField() {
        return firstField;
    }

    public D_Field getLastField() {
        return lastField;
    }
    
    
}
