package thegamebrett.game.dummy;
import java.awt.Color;
import thegamebrett.game.dummy.*;
import java.util.ArrayList;
import thegamebrett.model.GameFactory;
import thegamebrett.model.GameLogic;
import thegamebrett.model.Layout;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Board;
import thegamebrett.model.elements.Figure;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;

public class D_GameFactory implements GameFactory {

    public static Model createGame(ArrayList<User> users) throws TooMuchPlayers, TooFewPlayers {
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
            D_Player p = new D_Player(i);
            Layout l = new Layout();
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
    
}
