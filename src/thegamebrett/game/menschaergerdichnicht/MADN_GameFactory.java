package thegamebrett.game.menschaergerdichnicht;
import java.util.ArrayList;
import thegamebrett.model.GameFactory;
import thegamebrett.model.GameLogic;
import thegamebrett.model.Layout;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Board;

/**
 * @author Koré
 */
public class MADN_GameFactory implements GameFactory {

    public static Model createGame(ArrayList<Player> players) {

        if(MADN_GameLogic.maximumPlayers < players.size()) {
            throw new IllegalArgumentException("Too much players");
        } else if(MADN_GameLogic.minimumPlayers > players.size()) {
            throw new IllegalArgumentException("Too few players");
        }
        
        MADN_GameLogic gl = new MADN_GameLogic(null);
        Layout l = new Layout();
        MADN_Board b = new MADN_Board(l);
        Model model = new Model(players, gl, b);
        gl.setDependingModel(model);
        
        return model;
    }
    
}
