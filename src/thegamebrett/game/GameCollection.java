package thegamebrett.game;

import java.util.ArrayList;
import thegamebrett.game.dummy.D_GameFactory;
import thegamebrett.model.GameFactory;

public class GameCollection {
    
    public GameFactory[] gameFactorys = {
        new D_GameFactory()
    };
    
    public static void main(String[] args) {
        
        
        
        D_GameFactory g = new D_GameFactory();
        
    }
    
    
    
}
