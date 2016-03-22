package thegamebrett.game;

import thegamebrett.game.MADN.MADN_GameFactory;
import thegamebrett.game.dummy.D_GameFactory;
import thegamebrett.model.GameFactory;

public class GameCollection {
    
    /** Hier muessen alle Spiele(gameFactorys) eingetragen werden */
    public static GameFactory[] gameFactorys = {
        new D_GameFactory(), new MADN_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory()
    };
    
}