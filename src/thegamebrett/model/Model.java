package thegamebrett.model;

import java.util.ArrayList;
import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.game.dummy.D_Board;
import thegamebrett.game.dummy.D_GameLogic;
import thegamebrett.game.dummy.D_Player;
import thegamebrett.model.elements.Board;
import thegamebrett.network.User;

/**
 * @author Christian Colbach
 */
public class Model {
    
    private ArrayList<Player> players;
    private GameLogic gameLogic;
    private Board board;

    public Model(ArrayList<Player> players, GameLogic gameLogic, Board board) {
        this.players = players;
        this.gameLogic = gameLogic;
        this.board = board;
    }
    
    /** reicht ActionResponse-Object durch und gibt ActionRequest-Object zuruek */
    public ActionRequest[] react(ActionResponse resonse) {
        return gameLogic.next(resonse);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    
}