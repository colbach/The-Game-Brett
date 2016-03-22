package thegamebrett.action.request;

import javafx.scene.image.Image;
import thegamebrett.model.Player;

public class GameEndRequest implements GUIRequest, MobileRequest {

    private final Player winner;
    private final String acknowledgment;
    private final int delay;
    private final Image backgroundImage;

    public GameEndRequest(Player winner, String acknowledgment, int delay, Image backgroundImage) {
        this.delay = delay;
        this.winner = winner;
        this.acknowledgment = acknowledgment;
        this.backgroundImage = backgroundImage;
    }

    public GameEndRequest(Player winner) {
        this(winner, "", 0, null);
    }

    public Player getWinner() {
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
