
package thegamebrett.game.MADN;

import thegamebrett.model.Player;
import thegamebrett.network.User;

/**
 * @author Korè
 */
public class MADNplayer extends Player{
    private User user;
    private int playerNr;
    private MADNfigure[] figures;
    //das erste feld nach den startfeldern
    private MADNfield firstField;
    //das letzte feld vor den endfeldern
    private MADNfield lastField;
    
    public MADNplayer(User user, int playerNr, MADNfigure[] figures, MADNfield firstField, MADNfield lastField){
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
    public MADNfigure[] getFigures() {
        return figures;
    }

    public void setFigures(MADNfigure[] figures) {
        this.figures = figures;
    }

    public MADNfield getFirstField() {
        return firstField;
    }

    public MADNfield getLastField() {
        return lastField;
    }
    
    
}
