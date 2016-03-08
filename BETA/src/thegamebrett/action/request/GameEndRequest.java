/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.action.request;

import thegamebrett.model.Player;

/**
 *
 * @author Koré
 */
public class GameEndRequest implements GUIRequest, MobileRequest{
    
    private final Player winner;
    private final String acknowledgment;
    private final int delay;
    
    public GameEndRequest(Player winner, String acknowledgment, int delay){
        this.delay = delay;
        this.winner = winner;
        this.acknowledgment = acknowledgment;
    }
    
    public GameEndRequest(Player winner){
        this(winner, "", 0);
    }

    public Player getWinner() {
        return winner;
    }

    public String getAcknowledgment() {
        return acknowledgment;
    }

    public int getDelay() {
        return delay;
    }
    
    
}
