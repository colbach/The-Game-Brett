package thegamebrett.game.MADN;
import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import thegamebrett.game.dummy.D_Board;
import thegamebrett.game.dummy.D_Figure;
import thegamebrett.game.dummy.D_GameLogic;
import thegamebrett.game.dummy.D_Player;
import thegamebrett.model.GameFactory;
import thegamebrett.model.Layout;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;

/**
 * @author Koré
 */
public class MADN_GameFactory implements GameFactory{

    public Model createGame(ArrayList<User> users) throws TooMuchPlayers, TooFewPlayers {
        MADN_GameLogic gl = new MADN_GameLogic(null);
        MADN_Board b = new MADN_Board();
        
        if(4 < users.size()) {
            throw new TooMuchPlayers();
        } else if(1 > users.size()) {
            throw new TooFewPlayers();
        }
        ArrayList<Player> players = new ArrayList<>();
        for(int i=0; i<users.size(); i++) {
            users.get(i);
            D_Player p = new D_Player(i, users.get(i));
            Layout l = new Layout();
            //Image image = new Image("thegamebrett/gui/test.png");
            //l.setBackgroundImage(image);
            l.setFormFactor(Layout.FORM_FACTOR_OVAL);
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
            D_Figure f = new D_Figure(p, l);
            f.setField(b.getField(0));
            p.setFigure(f);
            players.add(p);
        }
        
        Model model = new Model(players, gl, b);
        gl.setDependingModel(model);
        
        return model;
    }

    @Override
    public Image getGameIcon() {
        Canvas c = new Canvas(500,500);
        WritableImage wi = new WritableImage(500, 500);
        GraphicsContext g = c.getGraphicsContext2D();
        g.setFill(Color.GAINSBORO);
        g.fillRect(0, 0, 500, 500);
        g.setFill(Color.HONEYDEW);
        g.fillOval(0, 0, 350, 350);
        g.setFill(Color.BEIGE);
        g.fillOval(150, 150, 350, 350);
        c.snapshot(null, wi);
        return wi;
    }

    @Override
    public String getGameName() {
        return "MADN";
    }
}