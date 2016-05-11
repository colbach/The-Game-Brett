package thegamebrett.game.KFSS;

import thegamebrett.model.Layout;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Figure;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class KFSS_Figure extends Figure {

    private KFSS_Board board;

    public KFSS_Board getBoard() {
        return board;
    }

    public void setBoard(KFSS_Board board) {
        this.board = board;
    }

    public KFSS_Figure(Player owner, KFSS_Board board, Layout layout) {
        super(owner, layout, 0.08 / board.getRatioX(), 0.08);
        this.board = board;
    }

}
