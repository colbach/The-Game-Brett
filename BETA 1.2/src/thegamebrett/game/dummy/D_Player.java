package thegamebrett.game.dummy;

import thegamebrett.model.Player;
import thegamebrett.network.User;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class D_Player extends Player {

    private int playerNr;
    private D_Figure figure;

    public D_Player(int playerNr, User user) {
        super(user);
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
        return new D_Figure[]{figure};
    }
}
