package thegamebrett.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.util.Duration;
import thegamebrett.Manager;
import thegamebrett.game.GameCollection;
import thegamebrett.game.dummy.D_GameLogic;
import thegamebrett.model.GameFactory;
import thegamebrett.model.Model;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;

/**
 *
 * @author christiancolbach
 */
public class MenueView extends Group {

    Manager manager;
    private int soundValue;
    private boolean aktivated = false;
    private boolean charWindow = false;
    private boolean muted = false;
    private double textScaleFactor;

    public MenueView(Manager manager) {
        this.manager = manager;

        double d = Math.min(ScreenResolution.getScreenWidth(), ScreenResolution.getScreenHeigth());
        double iconWH = d / 5.3;
        int iconsInARow = Math.round((ScreenResolution.getScreenWidth() / (float) ScreenResolution.getScreenHeigth()) * 3);
        double rowWidth = iconsInARow * iconWH + (iconsInARow - 1) * (iconWH / 2);
        double rowMid = ScreenResolution.getScreenWidth() / 2;
        double rowStart = rowMid - (rowWidth / 2);

        int count = 0;
        int rowCount = 0;
        for (GameFactory game : GameCollection.gameFactorys) {

            ImageView icon = new ImageView(game.getGameIcon());

            icon.setLayoutX(rowStart + (1.5 * count * iconWH));
            icon.setLayoutY(rowStart + (1.5 * rowCount * iconWH));

            icon.setFitHeight(iconWH);
            icon.setFitWidth(iconWH);
            icon.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
                startGame(game);
            });

            getChildren().add(icon);

            count++;
            if (count == iconsInARow) {
                count = 0;
                rowCount += 1;
            }
        }

        Group g = new Group();
        MBar bar = new MBar();
        Button b = new Button("xxxx");
        b.setLayoutX(ScreenResolution.getScreenWidth() / 2);
        b.setLayoutY(ScreenResolution.getScreenHeigth() - 50);

        TranslateTransition tt = new TranslateTransition(Duration.millis(500), g);
        tt.setCycleCount(1);
        tt.setAutoReverse(true);

        b.setOnAction((ActionEvent t)
                -> {
            if (!aktivated) {
                tt.setByY(-(120));
                aktivated = true;
            } else if (aktivated) {
                tt.setByY(120);
                aktivated = false;
            }
            tt.play();
        });

        g.getChildren().add(bar.getRoot());

        Pane soundPane = new Pane();
        soundPane.setStyle("-fx-background-color: white;");
        soundPane.setPrefSize(160, 100);
        soundPane.setLayoutX((int) (rowStart + (-0.1 * iconWH)));
        soundPane.setLayoutY(ScreenResolution.getScreenHeigth());
        createVolumeOption(soundPane);

        Pane characterPane = new Pane();
        characterPane.setStyle("-fx-background-color: white;");
        characterPane.setPrefSize(160, 100);
        characterPane.setLayoutX((int) (rowStart + (1.6 * iconWH)));
        characterPane.setLayoutY(ScreenResolution.getScreenHeigth());
        createCharacterOption(characterPane);

        Pane textPane = new Pane();
        textPane.setStyle("-fx-background-color: white;");
        textPane.setPrefSize(160, 100);
        textPane.setLayoutX((int) (rowStart + (3.2 * iconWH)));
        textPane.setLayoutY(ScreenResolution.getScreenHeigth());
        createTextSizeOption(textPane);

        Pane wlanPane = new Pane();
        wlanPane.setStyle("-fx-background-color: white;");
        wlanPane.setPrefSize(160, 100);
        wlanPane.setLayoutX((int) (rowStart + (4.85 * iconWH)));
        wlanPane.setLayoutY(ScreenResolution.getScreenHeigth());
        createWLANOption(wlanPane);

        Pane languagePane = new Pane();
        languagePane.setStyle("-fx-background-color: white;");
        languagePane.setPrefSize(160, 100);
        languagePane.setLayoutX((int) (rowStart + (6.45 * iconWH)));
        languagePane.setLayoutY(ScreenResolution.getScreenHeigth());
        createLanguageOption(languagePane);

        g.getChildren().addAll(b, soundPane, characterPane, textPane, wlanPane, languagePane);
        this.getChildren().add(g);

    }

    public void startGame(GameFactory game) {

        ArrayList<User> al = new ArrayList<User>();
        for (User systemUser : manager.getMobileManager().getUserManager().getSystemClients()) {
            if (systemUser != null) {
                al.add(systemUser);
            }
        }

        try {
            if (manager.getGui().getGameView().getGameModel() == null || !(manager.getGui().getGameView().getGameModel().getGameLogic() instanceof D_GameLogic)) {

                Model gameModel = game.createGame(al);
                manager.getGui().getGameView().setGameModel(gameModel);
                manager.startGame(gameModel);

            }

            manager.getGui().showGameScene();

        } catch (TooMuchPlayers ex) {
            Logger.getLogger(MenueView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TooFewPlayers ex) {
            Logger.getLogger(MenueView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    final public void createVolumeOption(Pane m) {
        Pane p = m;

        Label label = new Label("Sound");
        label.setLayoutX(60);
        label.setLayoutY(10);

        Slider slider = new Slider(0, 100, 0);
        slider.setLayoutX(5);
        slider.setLayoutY(30);
        slider.setPadding(new Insets(5));
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(true);
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val)
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

        Button btn = new Button("Mute");
        btn.setLayoutX(60);
        btn.setLayoutY(65);
        btn.setOnAction((ActionEvent t)
                -> {
            int sound;
            if (muted) {
                sound = soundValue;
                muted = false;
            } else {
                sound = 0;
                muted = true;
            }
            System.out.println(muted);
            try {
                Process p1 = Runtime.getRuntime().exec("amixer -D pulse sset Master " + sound + "%+");
            } catch (IOException ex) {
                System.out.println(ex);
            }
        });
        p.getChildren().addAll(label, slider, btn);
    }

    final public void createCharacterOption(Pane m) {
        Pane p = m;

        Rectangle2D dimension = Screen.getPrimary().getBounds();
        int x = (int) (dimension.getWidth() * 0.4);
        int y = (int) (dimension.getHeight() * 0.6);

        Pane characterPane = new Pane();
        characterPane.setStyle("-fx-background-color: white;");
        characterPane.setPrefSize(x, y);
        characterPane.setLayoutX((dimension.getWidth() / 2) - (x / 2));
        characterPane.setLayoutY((dimension.getHeight() / 2) - (y / 2));

        Button btn = new Button("Character Option");
        btn.setLayoutX(25);
        btn.setLayoutY(40);
        btn.setOnAction((ActionEvent t)
                -> {
            if (!charWindow) {
                this.getChildren().add(characterPane);
                charWindow = true;
            } else if (charWindow) {
                this.getChildren().remove(characterPane);
                charWindow = false;
            }
        });

        Label label = new Label("Character");
        label.setLayoutX(55);
        label.setLayoutY(10);

        p.getChildren().addAll(label, btn);
    }

    final public void createTextSizeOption(Pane m) {
        Pane p = m;

        Slider slider = new Slider(0, 200, 0);
        slider.setLayoutX(5);
        slider.setLayoutY(30);
        slider.setPadding(new Insets(5));
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val)
                -> {
            textScaleFactor = (double) new_val / 100;
            System.out.println(textScaleFactor);
        });
        Label label = new Label("Textgröße: ");
        label.setLayoutX(55);
        label.setLayoutY(10);

        p.getChildren().addAll(label, slider);
    }

    final public void createWLANOption(Pane m) {
        Pane p = m;
        Label label = new Label("WLAN");
        label.setLayoutX(60);
        label.setLayoutY(10);

        p.getChildren().add(label);
    }

    final public void createLanguageOption(Pane m) {
        Pane p = m;

        Label label = new Label("Language");
        label.setLayoutX(60);
        label.setLayoutY(10);

        ObservableList<String> options
                = FXCollections.observableArrayList(
                        "Deutsch",
                        "Englisch",
                        "Spanisch"
                );

        ComboBox comboBox = new ComboBox(options);
        comboBox.setLayoutX(40);
        comboBox.setLayoutY(40);
        p.getChildren().addAll(label, comboBox);
    }

}
