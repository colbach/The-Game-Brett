/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.PSS;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import thegamebrett.model.GameFactory;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;

/**
 *
 * @author Korè
 */
public class PSS_GameFactory implements GameFactory{
    
    private PSS_Board board;
    
    public Model createGame(ArrayList<User> users) throws TooMuchPlayers, TooFewPlayers {
        PSS_GameLogic gl = new PSS_GameLogic(null);
        gl.setAnzPlayer(users.size());
        board = new PSS_Board(gl.getFieldContent());

        
        if(getMaximumPlayers() < users.size()) {
            throw new TooMuchPlayers();
        } else if(getMinimumPlayers() > users.size()) {
            throw new TooFewPlayers();
        }
        ArrayList<Player> players = new ArrayList<>();
        for(int i=0; i<users.size(); i++) {
            PSS_Player p = new PSS_Player(i, users.get(i), board);
            players.add(p);
        }
        
        Model model = new Model(players, gl, board);
        gl.setDependingModel(model);
        gl.setBoard(board);
        
        return model;
    }

    @Override
    public String getGameIcon() {
        return "gameicons/gameIconPSS.jpg";
    }
    @Override
    public String getGameName() {
        return "Polish Drinking";
    }
    
    public PSS_Board getBoard(){
        return board;
    }

    @Override
    public int getMaximumPlayers() {
        return 8;
    }

    @Override
    public int getMinimumPlayers() {
        return 1;
    }
    
}

