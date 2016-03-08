/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MADN;

import GUI.GameView;
import Model.Board_Creator;
import Model.GameFactory;
import Model.Layout;
import Model.Model;
import Model.Player;
import java.util.ArrayList;


public class MADN_GameFactory implements GameFactory {
    
    MADN_Board mb = new MADN_Board();
    
    ArrayList<MADN_Field> fields = mb.getFields();
    
//    public static Model createGame(ArrayList<Player> players) {
//
//        if(MADN_GameLogic.maximumPlayers < players.size()) {
//            throw new IllegalArgumentException("Too much players");
//        } else if(MADN_GameLogic.minimumPlayers > players.size()) {
//            throw new IllegalArgumentException("Too few players");
//        }
//        
//        MADN_GameLogic gl = new MADN_GameLogic(null);
//        Layout l = new Layout();
//        MADN_Board b = new MADN_Board(l);
//        Model model = new Model(players, gl, b);
//        gl.setDependingModel(model);
//        
//        return model;
//    }
//    

}
