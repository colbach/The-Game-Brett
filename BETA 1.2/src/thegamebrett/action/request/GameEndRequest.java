package thegamebrett.action.request;

import javafx.scene.image.Image;
import thegamebrett.model.Player;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class GameEndRequest implements GUIRequest, MobileRequest {

    private final Player[] winner;
    private final String acknowledgment;
    private final int delay;
    private Image backgroundImage;

    public GameEndRequest(Player[] winner, String acknowledgment, int delay, Image backgroundImage) {
        this.delay = delay;
        this.winner = winner;
        this.acknowledgment = acknowledgment;
        this.backgroundImage = backgroundImage;
    }

    public GameEndRequest(Player[] winner) {
        this(winner, "", 0, null);
    }

    public GameEndRequest(Player[] winner, String acknowledgment, int delay) {
        this.delay = delay;
        this.winner = winner;
        this.acknowledgment = acknowledgment;
    }

    public Player[] getWinner() {
        return winner;
    }

    public String getAcknowledgment() {
        return acknowledgment;
    }

    public int getDelay() {
        return delay;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

}
