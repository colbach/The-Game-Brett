package thegamebrett.gamescreen;

import thegamebrett.Manager;
import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.action.request.GUIRequest;

/**
 * @author Christian Colbach
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