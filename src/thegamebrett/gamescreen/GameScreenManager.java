/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thegamebrett.gamescreen;

import thegamebrett.Manager;
import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.action.request.GUIRequest;

/**
 *
 * @author christiancolbach
 */
public class GameScreenManager {
    
    private ScreenView screenView;
    private Manager manager;

    public GameScreenManager(Manager manager) {
        this.manager = manager;
        // lade Daten aus Manager
        // initialisiere ScreenView...
    }

    public ScreenView getView() {
        return screenView;
    }

    public void react(GUIRequest response) {
        // mach was
    }

}