package thegamebrett.game.menschaergerdichnicht;
import java.util.ArrayList;
import javafx.scene.image.Image;
import thegamebrett.model.GameFactory;
import thegamebrett.model.GameLogic;
import thegamebrett.model.Layout;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Board;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;

/**
 * @author Koré
 */
public class MADN_GameFactory implements GameFactory {

    /*public Model createGame(ArrayList<Player> players) {

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
    }*/

    @Override
    public Image getGameIcon() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Image getGameName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Model createGame(ArrayList<User> users) throws TooMuchPlayers, TooFewPlayers {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
