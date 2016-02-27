package thegamebrett.game.dummy;
import thegamebrett.game.dummy.*;
import java.util.ArrayList;
import thegamebrett.model.GameFactory;
import thegamebrett.model.GameLogic;
import thegamebrett.model.Layout;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Board;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;

public class D_GameFactory implements GameFactory {

    public static Model createGame(ArrayList<Player> players) throws TooMuchPlayers, TooFewPlayers {

        if(D_GameLogic.maximumPlayers < players.size()) {
            throw new TooMuchPlayers();
        } else if(D_GameLogic.minimumPlayers > players.size()) {
            throw new TooFewPlayers();
        }
        
        D_GameLogic gl = new D_GameLogic(null);
        D_Board b = new D_Board();
        Model model = new Model(players, gl, b);
        gl.setDependingModel(model);
        
        return model;
    }
    
}
