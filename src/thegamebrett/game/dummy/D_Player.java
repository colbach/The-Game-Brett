
package thegamebrett.game.dummy;

import thegamebrett.game.dummy.*;
import thegamebrett.model.Player;

public class D_Player extends Player{
    private int playerNr;
    private D_Figure figure;
    
    public D_Player(int playerNr){
        this.playerNr = playerNr;
    }

    public void setFigure(D_Figure figure) {
        this.figure = figure;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }

    @Override
    public D_Figure[] getFigures() {
        return new D_Figure[] { figure };
    }
}
