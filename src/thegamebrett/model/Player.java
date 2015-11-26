package thegamebrett.model;

import java.util.ArrayList;
import thegamebrett.model.elements.Figure;
import thegamebrett.network.Client;

/**
 * @author Christian Colbach
 */
public abstract class Player {
   
    private ArrayList<Figure> figures = new ArrayList<Figure>();
    
    private Client user;
    
    private int playerPositon;

    public Figure[] getFigures() {
        return figures.toArray(new Figure[0]);
    }

    public Client getUser() {
        return user;
    }

    public int getPlayerPositon() {
        return playerPositon;
    }
    
    
    
}