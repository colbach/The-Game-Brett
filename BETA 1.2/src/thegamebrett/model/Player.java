package thegamebrett.model;

import java.util.ArrayList;
import thegamebrett.model.elements.Figure;
import thegamebrett.network.User;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public abstract class Player {

    private ArrayList<Figure> figures = new ArrayList<Figure>();

    private volatile User user;

    public Player(User user) {
        this.user = user;
    }

    public Figure[] getFigures() {
        return figures.toArray(new Figure[0]);
    }

    public User getUser() {
        return user;
    }

}
