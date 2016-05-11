package thegamebrett.game.PSS;

import thegamebrett.model.Layout;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Figure;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class PSS_Figure extends Figure {

    private PSS_Board board;

    public PSS_Board getBoard() {
        return board;
    }

    public void setBoard(PSS_Board board) {
        this.board = board;
    }

    public PSS_Figure(Player owner, PSS_Board board, Layout layout) {
        super(owner, layout, 0.08 / board.getRatioX(), 0.08);
        this.board = board;
    }

}
