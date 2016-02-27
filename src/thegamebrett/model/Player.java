package thegamebrett.model;

import java.util.ArrayList;
import thegamebrett.model.elements.Figure;
import thegamebrett.network.User;

/**
 * @author Christian Colbach
 */
public abstract class Player {
   
    private ArrayList<Figure> figures = new ArrayList<Figure>();
    
    private User user;
    
    private int playerPositon;

    public Figure[] getFigures() {
        return figures.toArray(new Figure[0]);
    }

    public User getUser() {
        return user;
    }

    public int getPlayerPositon() {
        return playerPositon;
    }

    
    
}