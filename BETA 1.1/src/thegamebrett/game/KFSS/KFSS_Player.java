/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.KFSS;

import thegamebrett.game.PSS.*;
import javafx.scene.paint.Color;
import thegamebrett.model.Layout;
import thegamebrett.model.Player;
import thegamebrett.network.User;

/**
 *
 * @author Korè
 */
public class KFSS_Player extends Player{
    
    private User user;
    private int playerNr;
    private KFSS_Figure figure;
    private Layout layout;
    private KFSS_Board board;
    private boolean suspended;
    private String playerName;

    public KFSS_Player(int playerNr, User user, KFSS_Board board){
        super(user);
        this.playerNr = playerNr;
        this.board = board;
        suspended = false;
        
        layout = new Layout();
        layout.setFormFactor(Layout.FORM_FACTOR_OVAL);
        /*if((user != null)&&(user.getUserCharacter()!=null)){
            playerName = user.getUserCharacter().getAvatarName();
            if(user.getUserCharacter().getAvatar()!= null){
                layout.setBackgroundImage(user.getUserCharacter().getAvatar());
            } else {
                layout.setBackgroundColor(user.getUserCharacter().getColor());
            }
        } else {*/
            playerName = "Spieler "+playerNr;
            layout.setBackgroundColor(Color.DARKGOLDENROD);
        //}
        layout.setTitle(playerName);
        Layout fl = new Layout(playerName,"");
        fl.setBackgroundColor(Color.DARKGOLDENROD);
        fl.setFormFactor(Layout.FORM_FACTOR_OVAL);
        figure = new KFSS_Figure(this, board, fl);
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
    
    public KFSS_Figure getFigure() {
        return figure;
    }

    public void setFigure(KFSS_Figure figure) {
        this.figure = figure;
    }
    
    public void setLayout(Layout l){
        this.layout = l;
    }
    
    public String getPlayerName(){
        return playerName;
    }
}