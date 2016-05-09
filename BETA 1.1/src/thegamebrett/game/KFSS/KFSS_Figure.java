/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.KFSS;

import thegamebrett.game.PSS.*;
import thegamebrett.model.Layout;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Figure;

/**
 *
 * @author Kore
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
        super(owner, layout,0.05,0.05);
        this.board = board;
    }
    
    
    
}
