/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.KFSS;

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
public class KFSS_GameFactory implements GameFactory{
    
    private KFSS_Board board;
    
   

    public Model createGame(ArrayList<User> users) throws TooMuchPlayers, TooFewPlayers {
        KFSS_GameLogic gl = new KFSS_GameLogic(null);
        gl.setAnzPlayer(users.size());
        board = new KFSS_Board(gl.getFieldContent());

        
        if(getMaximumPlayers() < users.size()) {
            throw new TooMuchPlayers();
        } else if(getMinimumPlayers() > users.size()) {
            throw new TooFewPlayers();
        }
        ArrayList<Player> players = new ArrayList<>();
        for(int i=0; i<users.size(); i++) {
            KFSS_Player p = new KFSS_Player(i, users.get(i), board);
            players.add(p);
        }
        
        Model model = new Model(players, gl, board);
        gl.setDependingModel(model);
        gl.setBoard(board);
        
        return model;
    }

    @Override
    public Image getGameIcon() {
        Canvas c = new Canvas(500,500);
        WritableImage wi = new WritableImage(500, 500);
        GraphicsContext g = c.getGraphicsContext2D();
        g.setFill(Color.ALICEBLUE);
        g.fillRect(0, 0, 500, 500);
        g.setFill(Color.ANTIQUEWHITE);
        g.fillOval(0, 0, 350, 350);
        g.setFill(Color.AQUA);
        g.fillOval(150, 150, 350, 350);
        c.snapshot(null, wi);
        return wi;
    }
    @Override
    public String getGameName() {
        return "PSS";
    }
    
    public KFSS_Board getBoard(){
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

