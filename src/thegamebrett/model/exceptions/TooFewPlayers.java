package thegamebrett.model.exceptions;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class TooFewPlayers extends Exception {

    @Override
    public String toString() {
        return "Too few players";
    }

}
