/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.menschaergerdichnicht;

import thegamebrett.model.Player;
import thegamebrett.model.elements.Figure;

/**
 *
 * @author Korè
 */
public class MADNplayer extends Player{
    private int playerNr;
    private Figure[] figures;
    
    public MADNplayer(int playerNr, Figure[] figures){
        this.playerNr = playerNr;
        this.figures = figures;
    }
}
