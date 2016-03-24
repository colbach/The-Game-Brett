
package thegamebrett.game.MADN;

import javafx.scene.paint.Color;
import thegamebrett.model.Layout;
import thegamebrett.model.Player;
import thegamebrett.network.User;

/**
 * @author Korè
 */
public class MADN_Player extends Player{
    private User user;
    private int playerNr;
    private MADN_Figure[] figures;
    private Layout layout;
    private Layout figureLayout;
    private MADN_Board board;
    private MADN_Field startField;
    
    
    public MADN_Player(int playerNr, User user, MADN_Board board){
        super(user);
        this.playerNr = playerNr;
        figureLayout = new Layout();
        figureLayout.setBackgroundColor(Color.DARKGOLDENROD);
        figureLayout.setFormFactor(Layout.FORM_FACTOR_OVAL);
        this.board = board;
        this.startField = (MADN_Field)board.getField(playerNr*10);
        this.figures = new MADN_Figure[4];
        int j = 0;
        for(int i=playerNr*4;i<playerNr*4+4;i++){
            MADN_Field initField = (MADN_Field)board.getField(40+i);
            figures[j] = new MADN_Figure(this, board, startField, startField, figureLayout, "Figur");
            figures[j].setField(initField);
            j++;
        }
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
}