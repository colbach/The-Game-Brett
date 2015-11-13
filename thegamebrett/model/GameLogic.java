/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thegamebrett.model;

import thegamebrett.model.elements.Field;

/**
 *
 * @author christiancolbach
 */
public abstract class GameLogic {
    
    public abstract int getMaximumPlayers();
    public abstract int getMinimumPlayers();
    
    public abstract Field getNextStartPositionForPlayer(Player player);
    
    
    
    
    
    
    
    
    
}