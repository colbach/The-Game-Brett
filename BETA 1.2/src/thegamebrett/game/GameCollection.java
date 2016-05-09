package thegamebrett.game;

import javafx.application.Platform;
import javafx.scene.image.Image;
import thegamebrett.game.KFSS.KFSS_GameFactory;
import thegamebrett.game.MADN.MADN_GameFactory;
import thegamebrett.game.PSS.PSS_GameFactory;
import thegamebrett.game.dummy.D_GameFactory;
import thegamebrett.model.GameFactory;

public class GameCollection {
    
    /** Hier muessen alle Spiele(gameFactorys) eingetragen werden */
    public static GameFactory[] gameFactorys = {
        new D_GameFactory(), new PSS_GameFactory(), new KFSS_GameFactory(), new MADN_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory()
    };
    
    public static Image[] imageCache = new Image[gameFactorys.length];
    static {
        Platform.runLater(() -> {
            for(int i=0; i<imageCache.length; i++) {
                imageCache[i] = gameFactorys[i].getGameIcon();
            }
        });
    }
    
    
}
