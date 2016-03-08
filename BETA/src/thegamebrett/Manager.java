/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thegamebrett;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.action.request.GUIRequest;
import thegamebrett.action.request.MobileRequest;
import thegamebrett.action.request.SoundRequest;
import thegamebrett.action.request.TimerRequest;
import thegamebrett.action.response.StartPseudoResonse;
import thegamebrett.gui.GUIApplication;
import thegamebrett.mobile.MobileManager;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Board;
import thegamebrett.network.PlayerNotRegisteredException;
import thegamebrett.sound.SoundManager;
import thegamebrett.timer.TimeManager;

/**
 * @author Christian Colbach
 */
public class Manager {
    
    private Model model;
    private SoundManager soundManager;
    private MobileManager mobileManager;
    private TimeManager timeManager;
        
    private GUIApplication gui;

    public GUIApplication getGui() {
        return gui;
    }
    
    
    
    public Manager(GUIApplication gui) {
        soundManager = new SoundManager(this);
        mobileManager = new MobileManager(this);
        timeManager = new TimeManager(this);
        this.gui = gui;
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
        react(new StartPseudoResonse());
        //initialisiere Gamescreenmanager
        //main.setView(gameScreenManager.getView());
    }
    
    /*public void stopGame(Model model) {
        main.setView(menueManager.getView());
        this.model = null;
    }*/
    
    /** reicht ActionResponse-Object durch und gibt ActionRequest-Object zuruek */
    public void react(ActionResponse response) {
        
        ActionRequest[] ars = model.react(response);
        
        for(ActionRequest ar : ars) {
            if(ar instanceof GUIRequest) {
                gui.react((GUIRequest) ar);
            }
            if(ar instanceof SoundRequest) {
                soundManager.react((SoundRequest) ar);
            }
            if(ar instanceof MobileRequest) {
                try {
                    mobileManager.react((MobileRequest) ar);
                } catch (PlayerNotRegisteredException ex) {
                    Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(ar instanceof TimerRequest) {
                timeManager.react((TimerRequest) ar);
            }
        }
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public MobileManager getMobileManager() {
        return mobileManager;
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }
    
    
}