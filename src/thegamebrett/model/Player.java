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
    
    
}