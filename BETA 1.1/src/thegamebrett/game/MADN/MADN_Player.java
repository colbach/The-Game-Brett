
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
    private MADN_Board board;
    private MADN_Field startField;
    
    
    
    public MADN_Player(int playerNr, User user, MADN_Board board, Layout layout){
        super(user);
        this.layout = layout;
        this.playerNr = playerNr;
        this.board = board;
        
        this.startField = (MADN_Field)board.getField(playerNr*10);
        startField.setLayout(getCharacterLayout(user));
        this.figures = new MADN_Figure[4];
        int j = 0;
        for(int i=playerNr*4;i<playerNr*4+4;i++){
            
            Layout figureLayout = getCharacterLayout(user);
            figureLayout.setTitle(j+"");
            MADN_Field endField = (MADN_Field)board.getField(56+i);
            endField.setLayout(getCharacterLayout(user));
            MADN_Field initField = (MADN_Field)board.getField(40+i);
            initField.setLayout(getCharacterLayout(user));
            figures[j] = new MADN_Figure(this, board, startField, initField, figureLayout, ""+j);
            figures[j].setField(initField);
            j++;
        }
        
    }
    
    private Layout getCharacterLayout(User user){
        Layout figureLayout = new Layout();
        if(user!=null&&user.getUserCharacter()!=null){
            Color c = user.getUserCharacter().getColor();
            figureLayout.setBackgroundColor(c);
            
        } 
        
        /*else zweig wird nur zu dummyzwecken gebraucht,sollte später nicht mehr so verwendet werden*/
        else {
            figureLayout.setBackgroundColor(layout.getBackgroundColor());
        }
        figureLayout.setFormFactor(Layout.FORM_FACTOR_OVAL);
        return figureLayout;
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
    
    public void setLayout(Layout l){
        this.layout = l;
    }

    public MADN_Field getStartField() {
        return startField;
    }
    
}