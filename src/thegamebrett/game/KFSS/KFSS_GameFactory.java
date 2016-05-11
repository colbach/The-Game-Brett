package thegamebrett.game.KFSS;

import java.util.ArrayList;
import thegamebrett.model.GameFactory;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class KFSS_GameFactory implements GameFactory {

    private KFSS_Board board;

    public Model createGame(ArrayList<User> users) throws TooMuchPlayers, TooFewPlayers {
        KFSS_GameLogic gl = new KFSS_GameLogic(null);
        gl.setAnzPlayer(users.size());
        board = new KFSS_Board(gl.getFieldContent());

        if (getMaximumPlayers() < users.size()) {
            throw new TooMuchPlayers();
        } else if (getMinimumPlayers() > users.size()) {
            throw new TooFewPlayers();
        }
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            KFSS_Player p = new KFSS_Player(i, users.get(i), board);
            players.add(p);
            System.out.println("Spielername: " + p.getPlayerName());
        }

        Model model = new Model(players, gl, board);
        gl.setDependingModel(model);
        gl.setBoard(board);

        return model;
    }

    @Override
    public String getGameIcon() {
        return "gameicons/gameIconKFSS.jpg";
    }

    @Override
    public String getGameName() {
        return "Sweets";
    }

    public KFSS_Board getBoard() {
        return board;
    }

    @Override
    public int getMaximumPlayers() {
        return 8;
    }

    @Override
    public int getMinimumPlayers() {
        return 1;
    }

}
