/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package thegamebrett.model;

import java.util.ArrayList;
import thegamebrett.model.elements.Board;

/**
 *
 * @author christiancolbach
 */
public class Model {
    
    private ArrayList<Player> players;
    private InteractionTemplates interactionTemplates;
    private GameLogic gameLogic;
    private Board board;

    public Model(ArrayList<Player> players, InteractionTemplates interactionTemplates, GameLogic gameLogic, Board board) {
        this.players = players;
        this.interactionTemplates = interactionTemplates;
        this.gameLogic = gameLogic;
        this.board = board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public InteractionTemplates getInteractionTemplates() {
        return interactionTemplates;
    }

    public void setInteractionTemplates(InteractionTemplates interactionTemplates) {
        this.interactionTemplates = interactionTemplates;
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