
package thegamebrett.game.MADN;

import thegamebrett.model.Layout;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Figure;

/**
 * @author Koré
 */
public class MADN_Figure extends Figure{
    
    private String description;
    private MADN_Board board;
    private MADN_Field startField;
    private MADN_Field initField;

    public MADN_Figure(Player owner, MADN_Board board, MADN_Field startField, MADN_Field initField, Layout layout, String description) {
        super(owner, layout,0.04/board.getRatioX(),0.04);
        this.board = board;
        this.startField = startField;
        this.initField = initField;
        this.description = description;
    }

    public MADN_Board getBoard() {
        return board;
    }

    public MADN_Field getStartField() {
        return startField;
    }

    public MADN_Field getInitField() {
        return initField;
    }
    
    @Override
    public String toString() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBoard(MADN_Board board) {
        this.board = board;
    }
}
