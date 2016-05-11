package thegamebrett.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class UserImageField extends Pane {

    private Canvas canvas;
    private final GraphicsContext gc;

    public UserImageField(String name, Image img) {
        this.setStyle("-fx-background-color: white;");
        this.setMinWidth(img.getWidth() + 125);
        this.setMinHeight(img.getHeight());

        canvas = new Canvas(img.getWidth(), img.getHeight());
        gc = canvas.getGraphicsContext2D();

        gc.drawImage(img, 0, 0);
        Label label = new Label(name);
        label.setLayoutX(img.getWidth() + 20);
        label.setLayoutY(img.getHeight() / 2 - 10);

        this.getChildren().addAll(canvas, label);
    }
}
