package thegamebrett.network;

import thegamebrett.model.Player;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 *
 * Fehler wird geworfen falls Player nicht in System angemeldet ist
 */
public class PlayerNotRegisteredException extends Exception {

    private final Player player;

    public PlayerNotRegisteredException(Player player) {
        this.player = player;
    }

    @Override
    public String getMessage() {
        return "Player is not registered in the System";
    }
}
