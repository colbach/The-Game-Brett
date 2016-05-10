package thegamebrett.game.MADN;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import thegamebrett.model.GameFactory;
import thegamebrett.model.Layout;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;
import thegamebrett.usercharacter.UserCharacter;

/**
 * @author Koré
 */
public class MADN_GameFactory implements GameFactory{

    MADN_Board board;
    UserCharacter uc;
    
    @Override
    public Model createGame(ArrayList<User> users) throws TooMuchPlayers, TooFewPlayers {
        board = new MADN_Board();
        MADN_GameLogic gl = new MADN_GameLogic(null);
        gl.setAnzPlayer(users.size());
        
        if(gl.getMaximumPlayers() < users.size()) {
            throw new TooMuchPlayers();
        } else if(gl.getMinimumPlayers() > users.size()) {
            throw new TooFewPlayers();
        }
        ArrayList<Player> players = new ArrayList<>();
        for(int i=0; i<users.size(); i++) {
            users.get(i);
            Layout l = new Layout();
            //Image image = new Image("thegamebrett/gui/test.png");
            //l.setBackgroundImage(image);
            l.setFormFactor(Layout.FORM_FACTOR_OVAL);
//            uc = new UserCharacter("Player "+i, i, "");
//            users.get(i).setUserCharacter(uc);
            switch (i) {
                case 0:
                    l.setBackgroundColor(Color.RED);
                    break;
                case 1:
                    l.setBackgroundColor(Color.BLUE);
                    break;
                case 2:
                    l.setBackgroundColor(Color.GREEN);
                    break;
                case 3:
                    l.setBackgroundColor(Color.PINK);
                    break;
                default:
                    break;
            }
            MADN_Player p = new MADN_Player(i, users.get(i), board, l);
            players.add(p);
        }
        
        Model model = new Model(players, gl, board);
        gl.setDependingModel(model);
        gl.setBoard(board);
        
        return model;
    }

    @Override
    public String getGameIcon() {
        return "gameicons/gameIconMADN.jpg";
    }

    @Override
    public String getGameName() {
        return "MADN";
    }
    
    public MADN_Board getBoard(){
        return board;
    }
    
    @Override
    public int getMaximumPlayers() {
        return 4;
    }

    @Override
    public int getMinimumPlayers() {
        return 2;
    }
}
