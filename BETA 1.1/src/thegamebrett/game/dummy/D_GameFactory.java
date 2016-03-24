package thegamebrett.game.dummy;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import thegamebrett.assets.AssetNotExistsException;
import thegamebrett.assets.AssetsLoader;
import thegamebrett.model.GameFactory;
import thegamebrett.model.Layout;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;

public class D_GameFactory implements GameFactory {

    public Model createGame(ArrayList<User> users) throws TooMuchPlayers, TooFewPlayers {
        D_GameLogic gl = new D_GameLogic(null);
        D_Board b = new D_Board();
        
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
            Image image;
            try {
                image = AssetsLoader.loadImage("images/test.png");
                l.setBackgroundImage(image);
            } catch (AssetNotExistsException ex) {
                Logger.getLogger(D_GameFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        g.setFill(Color.YELLOWGREEN);
        g.fillRect(0, 0, 500, 500);
        g.setFill(Color.BLUE);
        g.fillOval(0, 0, 350, 350);
        g.setFill(Color.RED);
        g.fillOval(150, 150, 350, 350);
        c.snapshot(null, wi);
        return wi;
    }

    @Override
    public String getGameName() {
        return "Dummy";
    }
    
}
