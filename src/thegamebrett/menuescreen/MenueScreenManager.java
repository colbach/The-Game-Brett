package thegamebrett.menuescreen;

import javafx.scene.Group;
import javafx.scene.Scene;
import thegamebrett.Manager;

/**
 * @author Christian Colbach
 */
public class MenueScreenManager {
    
    private final Manager manager;
    private Group menueView;

    public MenueScreenManager(Manager manager) {
        this.manager = manager;
        
        // initialisiere menueView...
        
    }
    
    public Group getView() {
        return menueView;
    }
    
    public void startGame(int gameID) {
        /*
        if(gameID == IdVonMenschAergerDichNicht) {
            Model game = initialisiere MenschAergerDichNicht...
            manager.startGame(game);
        } else ...
        */
    }
}
