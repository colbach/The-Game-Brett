package thegamebrett.gui;

import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class GameOption extends Pane {

    private final Pane p;
    private final int x;
    private final int y;
    private double textScaleFactor;
    private final Rectangle2D dimension;
    private int soundValue;

    public GameOption() {
        this.dimension = Screen.getPrimary().getBounds();
        this.textScaleFactor = 1;
        this.x = (int) (dimension.getWidth() * 0.2);
        this.y = (int) (dimension.getHeight() * 0.6);
        this.setStyle("-fx-background-color: transparent;");
        this.setPrefSize(dimension.getWidth(), dimension.getHeight());
        this.setLayoutX(0);
        this.setLayoutY(0);

        p = new Pane();
        p.setStyle("-fx-background-color: white;");
        p.setPrefSize(x, y);
        p.setLayoutX((dimension.getWidth() / 2) - (x / 2));
        p.setLayoutY((dimension.getHeight() / 2) - (y / 2));

        Label titel = new Label("Optionen");
        titel.setLayoutX(x * 0.4);
        titel.setLayoutY(y * 0.05);

        Label volumeLabel = new Label("Volume:");
        volumeLabel.setLayoutX(x * 0.1);
        volumeLabel.setLayoutY(y * 0.2);

        Slider soundValueSlider = new Slider(0, 100, 0);
        soundValueSlider.setPadding(new Insets(15));
        soundValueSlider.setLayoutX(x * 0.3);
        soundValueSlider.setLayoutY(y * 0.17);
        soundValueSlider.setShowTickLabels(false);
        soundValueSlider.setShowTickMarks(true);
        soundValueSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val)
                -> {
            int diff = (new_val.intValue() - soundValue);
            try {
                if (diff > 0) {
                    Process p1 = Runtime.getRuntime().exec("amixer -D pulse sset Master " + diff + "%+");
                } else {
                    Process p2 = Runtime.getRuntime().exec("amixer -D pulse sset Master " + diff + "%-");
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
            soundValue = new_val.intValue();
        });

        Label textSizeLabel = new Label("Text Size:");
        textSizeLabel.setLayoutX(x * 0.1);
        textSizeLabel.setLayoutY(y * 0.4);

        Slider textSizeSlider = new Slider(0, 200, 100);
        textSizeSlider.setPadding(new Insets(15));
        textSizeSlider.setLayoutX(x * 0.3);
        textSizeSlider.setLayoutY(y * 0.37);
        textSizeSlider.setShowTickLabels(false);
        textSizeSlider.setShowTickMarks(true);
        textSizeSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val)
                -> {
            textScaleFactor = (double) new_val / 100;
            System.out.println(textScaleFactor);
        });

        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "Deutsch",
                        "Englisch",
                        "Spanisch"
                );
        Label languageLabel = new Label("Language: ");
        languageLabel.setLayoutX(x * 0.1);
        languageLabel.setLayoutY(y * 0.6);
        ComboBox comboBox = new ComboBox(options);
        comboBox.setLayoutX(x * 0.42);
        comboBox.setLayoutY(y * 0.59);

        Button quitButton = new Button("Quit Game");
        quitButton.setLayoutX(x * 0.1);
        quitButton.setLayoutY(y * 0.8);
        quitButton.setOnAction(eh
                -> {

        });

        Button resumeButton = new Button("Resume Game");
        resumeButton.setLayoutX(x * 0.55);
        resumeButton.setLayoutY(y * 0.8);
        resumeButton.setOnAction(eh
                -> {

        });

        p.getChildren().addAll(titel, volumeLabel, soundValueSlider, textSizeLabel,
                textSizeSlider, languageLabel, comboBox, quitButton, resumeButton);
        this.getChildren().add(p);
    }
}
