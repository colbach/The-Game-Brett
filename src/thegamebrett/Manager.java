/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thegamebrett;

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
import thegamebrett.sound.SoundManager;
import thegamebrett.timer.TimeManager;

/**
 *
 * @author christiancolbach
 */
public class Manager {
    
    private Model model;
    private GameScreenManager screenManager;
    private SoundManager soundManager;
    private MobileManager mobileManager;
    private TimeManager timeManager;
    private MenueScreenManager menueManager;

    public Manager() {
        
    }
    
    
    
    /** reicht ActionResponse-Object durch und gibt ActionRequest-Object zuruek */
    public void react(ActionResponse response) {
        
        ActionRequest[] ars = model.react(response);
        
        for(ActionRequest ar : ars) {
            if(ar instanceof GUIRequest) {
                screenManager.react((GUIRequest) ar);
            } else if(ar instanceof SoundRequest) {
                soundManager.react((SoundRequest) ar);
            } else if(ar instanceof MobileRequest) {
                mobileManager.react((MobileRequest) ar);
            }  else if(ar instanceof TimerRequest) {
                timeManager.react((TimerRequest) ar);
            } else {
                System.err.println("Unbekannte ActionResponse!");
            }
        }
    }
}