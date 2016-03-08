package thegamebrett.game.MADN;
import java.util.ArrayList;
import javafx.scene.image.Image;
import thegamebrett.model.GameFactory;
import thegamebrett.model.Model;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;

/**
 * @author Koré
 */
public class MADNgameFactory implements GameFactory{

    @Override
    public Model createGame(ArrayList<User> users) throws TooMuchPlayers, TooFewPlayers {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Image getGameIcon() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getGameName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
