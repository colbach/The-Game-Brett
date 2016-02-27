/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thegamebrett;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Scene;
import thegamebrett.gamescreen.GameScreenManager;
import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.action.request.GUIRequest;
import thegamebrett.action.request.MobileRequest;
import thegamebrett.action.request.SoundRequest;
import thegamebrett.action.request.TimerRequest;
import thegamebrett.menuescreen.MenueScreenManager;
import thegamebrett.mobile.MobileManager;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Board;
import thegamebrett.sound.SoundManager;
import thegamebrett.timer.TimeManager;

/**
 * @author Christian Colbach
 */
public class Manager {
    
    private Model model;
    private GameScreenManager gameScreenManager;
    private SoundManager soundManager;
    private MobileManager mobileManager;
    private TimeManager timeManager;
    private MenueScreenManager menueManager;
        
    private Main main;

    public Manager() {
        gameScreenManager = new GameScreenManager(this);
        soundManager = new SoundManager(this);
        mobileManager = new MobileManager(this);
        timeManager = new TimeManager(this);
        menueManager = new MenueScreenManager(this);
    }
    
    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = getModel().getPlayers();
        if(players == null)
            throw new RuntimeException("players ist nicht gesetzt");
        return players;
    }
    
    public Board getBoard() {
        Board board = getModel().getBoard();
        if(board == null)
            throw new RuntimeException("board ist nicht gesetzt");
        return board;
    }
    
    public Model getModel() {
        if(model == null)
            throw new RuntimeException("Model ist nicht gesetzt");
        return model;
    }
    
    public void startGame(Model model) { // Aufruf: startGame(D_GameFactory.createGame(ArrayList<User> users))
        this.model = model;
        //initialisiere Gamescreenmanager
        main.setView(gameScreenManager.getView());
    }
    
    public void stopGame(Model model) {
        main.setView(menueManager.getView());
        this.model = null;
    }
    
    /** reicht ActionResponse-Object durch und gibt ActionRequest-Object zuruek */
    public void react(ActionResponse response) {
        
        ActionRequest[] ars = model.react(response);
        
        for(ActionRequest ar : ars) {
            if(ar instanceof GUIRequest) {
                gameScreenManager.react((GUIRequest) ar);
            } else if(ar instanceof SoundRequest) {
                soundManager.react((SoundRequest) ar);
            } else if(ar instanceof MobileRequest) {
                mobileManager.react((MobileRequest) ar);
            }  else if(ar instanceof TimerRequest) {
                timeManager.react((TimerRequest) ar);
            } else {
                throw new RuntimeException("Unbekannter ActionRequest-Typ");
            }
        }
    }

    
    
    
}