/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.PSS;

import thegamebrett.model.Layout;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Figure;

/**
 *
 * @author Korè
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
        super(owner, layout,0.02,0.02);
        this.board = board;
    }
    
    
    
}
