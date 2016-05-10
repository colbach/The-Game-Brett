/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.PSS;

import thegamebrett.model.Layout;
import thegamebrett.model.Player;
import thegamebrett.network.User;

/**
 *
 * @author Kore
 */
public class PSS_Player extends Player{
    
    private User user;
    private int playerNr;
    private PSS_Figure figure;
    private Layout layout;
    private PSS_Board board;
    private boolean suspended;
    private String playerName;

    public PSS_Player(int playerNr, User user, PSS_Board board){
        super(user);
        this.playerNr = playerNr;
        this.board = board;
        suspended = false;
        
        layout = new Layout();
        layout.setFormFactor(Layout.FORM_FACTOR_OVAL);
        playerName = user.getUserCharacter().getName();
        layout.setBackgroundImage(user.getUserCharacter().getAvatar());
        
        figure = new PSS_Figure(this, board, layout);
        figure.setField(board.getField(0));   
        
    }
    
    
    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }
    
    public PSS_Figure getFigure() {
        return figure;
    }
    
    @Override
    public PSS_Figure[] getFigures() {
        return new PSS_Figure[]{
            figure
        };
    }

    public void setFigure(PSS_Figure figure) {
        this.figure = figure;
    }
    
    public void setLayout(Layout l){
        this.layout = l;
    }
    
    public String getPlayerName(){
        return playerName;
    }
}