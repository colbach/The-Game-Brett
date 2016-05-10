package thegamebrett.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.util.Duration;
import thegamebrett.Manager;
import thegamebrett.assets.AssetNotExistsException;
import thegamebrett.assets.AssetsLoader;
import thegamebrett.game.GameCollection;
import thegamebrett.game.dummy.D_GameLogic;
import thegamebrett.model.GameFactory;
import thegamebrett.model.Model;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.NetworkGameSelector;
import thegamebrett.network.User;

/**
 *
 * @author christiancolbach
 */
public class MenueView extends Group {

    private Manager manager;
    private int soundValue;
    private boolean aktivated = false;
    private boolean charWindow = false;
    private boolean muted = false;

    private Button optionBtn;
    private Button muteBtn;
    private Label soundLbl;
    private Label addressLbl;
    private Label languageLbl;
    private Slider soundSlider;
    private ComboBox languageCBox;

    private String optionButton;
    private String soundLabel;
    private String muteButton;
    private String languageLabel;
    private String language1 = "Deutsch";
    private String language2 = "English";

    private Canvas waitScreen = null;
    private Button startGameButton = null;

    public MenueView(Manager manager) {

        this.manager = manager;
        double d = Math.min(ScreenResolution.getScreenWidth(), ScreenResolution.getScreenHeigth());
        double iconWH = d / 5.3;
        int iconsInARow = Math.round((ScreenResolution.getScreenWidth() / (float) ScreenResolution.getScreenHeigth()) * 3);
        double rowWidth = iconsInARow * iconWH + (iconsInARow - 1) * (iconWH / 2);
        double rowMid = ScreenResolution.getScreenWidth() / 2;
        double rowStart = rowMid - (rowWidth / 2) + 30;

        int count = 0;
        int rowCount = 0;
        for (GameFactory game : GameCollection.gameFactorys) {

            ImageView icon;
            try {
                icon = new ImageView(AssetsLoader.loadImage(game.getGameIcon()));
                icon.setLayoutX(rowStart + (1.5 * count * iconWH));
                icon.setLayoutY(rowStart + (1.7 * rowCount * iconWH));

                icon.setFitHeight(iconWH);
                icon.setFitWidth(iconWH);
                icon.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
                    refreshGameSelectedScreen();
                    selectGame(game);
                });
                getChildren().add(icon);
            } catch (AssetNotExistsException ex) {
                Logger.getLogger(MenueView.class.getName()).log(Level.SEVERE, null, ex);
            }
            Label label = new Label(game.getGameName());
            label.setAlignment(Pos.CENTER);
            label.setLayoutX(rowStart + (1.5 * count * iconWH));
            label.setPrefWidth(iconWH);
            label.setFont(Font.font(15));
            label.setLayoutY(rowStart + (1.7 * rowCount * iconWH) + iconWH + 5);
            
            

            getChildren().addAll(label);

            count++;
            if (count == iconsInARow) {
                count = 0;
                rowCount += 1;
            }
        }

        Group g = new Group();
        MenueBar bar = new MenueBar();
        optionBtn = new Button(optionButton);
        optionBtn.setPrefWidth(100d);
        optionBtn.setLayoutX(ScreenResolution.getScreenWidth() / 2 - 50);
        optionBtn.setLayoutY(ScreenResolution.getScreenHeigth() - 50);

        TranslateTransition tt = new TranslateTransition(Duration.millis(500), g);
        tt.setCycleCount(1);
        tt.setAutoReverse(true);

        optionBtn.setOnAction((ActionEvent t)
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
        soundPane.setPrefSize(160, 100);
        soundPane.setLayoutX(ScreenResolution.getScreenWidth()/2 - 150 - 80);
        soundPane.setLayoutY(ScreenResolution.getScreenHeigth());
        createVolumeOption(soundPane);

        Pane languagePane = new Pane();
        languagePane.setPrefSize(160, 100);
        languagePane.setLayoutX(ScreenResolution.getScreenWidth()/2 + 150 - 80);
        languagePane.setLayoutY(ScreenResolution.getScreenHeigth());
        createLanguageOption(languagePane);

        g.getChildren().addAll(optionBtn, soundPane, languagePane);
        this.getChildren().add(g);
        addressLbl = new Label();
        addressLbl.setPrefWidth(100d);
        addressLbl.setAlignment(Pos.CENTER);
        addressLbl.setFont(Font.font("Arial", 20));

        addressLbl.setLayoutX(0);
        addressLbl.setLayoutY(50);
        addressLbl.setMaxWidth(ScreenResolution.getScreenWidth());
        addressLbl.setMinWidth(ScreenResolution.getScreenWidth());
        addressLbl.setAlignment(Pos.CENTER);
        this.getChildren().add(addressLbl);
        setLanguage();
    }

    public void refreshGameSelectedScreen() {
        Platform.runLater(() -> {
            if (waitScreen != null) {
                getChildren().remove(waitScreen);
            }
            if (startGameButton != null) {
                getChildren().remove(startGameButton);
            }
            NetworkGameSelector ngs = manager.getMobileManager().getNetworkManager().getNetworkGameSelector();
            
            if (ngs.isGameSelected()) {
                waitScreen = GUILoader.createGameSelectedScreen(ngs);
                getChildren().add(waitScreen);
                if(ngs.canStart()) {
                    startGameButton = new Button(Manager.rb.getString("StartGame"));
                    startGameButton.setPrefWidth(120d);
                    startGameButton.setLayoutX(ScreenResolution.getScreenWidth() / 2 - 60d);
                    startGameButton.setLayoutY(ScreenResolution.getScreenHeigth() / 3 * 2 + 60);
                    startGameButton.setOnAction((e) -> {
                        boolean b = ngs.tryToStart();
                        if(!b) {
                            refreshGameSelectedScreen();
                        }
                    });
                    getChildren().add(startGameButton);
                    System.out.println("Spiel kann starten");
                } else {
                    System.out.println("Spieleranzahl stimmt noch nicht");
                }
            }
        });
    }

    public void selectGame(GameFactory game) {
        manager.getMobileManager().getNetworkManager().getNetworkGameSelector().tryToSelectGame(game, null);
    }

    final public void createVolumeOption(Pane m) {
        Pane p = m;

        soundLbl = new Label(soundLabel);
        soundLbl.setLayoutX(55);
        soundLbl.setLayoutY(10);
        soundLbl.setPrefWidth(100d);
        soundLbl.setAlignment(Pos.CENTER);

        soundSlider = new Slider(0, 100, 0);
        soundSlider.setLayoutX(30);
        soundSlider.setLayoutY(35);
        soundSlider.setPadding(new Insets(5));
        soundSlider.setShowTickLabels(false);
        soundSlider.setShowTickMarks(true);
        soundSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val)
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

        muteBtn = new Button(muteButton);
        muteBtn.setPrefWidth(100d);
        muteBtn.setLayoutX(55);
        muteBtn.setLayoutY(80);
        muteBtn.setOnAction((ActionEvent t)
                -> {
            int sound;
            if (muted) {
                sound = soundValue;
                muted = false;
            } else {
                sound = 0;
                muted = true;
            }
            //System.out.println(muted);
            try {
                Process p1 = Runtime.getRuntime().exec("amixer -D pulse sset Master " + sound + "%+");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        p.getChildren().addAll(soundLbl, soundSlider, muteBtn);
    }
    
    final public void createLanguageOption(Pane m) {
        Pane p = m;

        languageLbl = new Label(languageLabel);
        languageLbl.setLayoutX(35);
        languageLbl.setPrefWidth(100);
        languageLbl.setAlignment(Pos.CENTER);
        languageLbl.setLayoutY(10);

        ObservableList<String> options
                = FXCollections.observableArrayList(
                        language1,
                        language2
                );
        Label label = new Label(options.get(0));
        label.setId("language_label");
        label.setMinWidth(50);
        label.setLayoutX(60);
        label.setLayoutY(53);
        Button btn1 = new Button("<");
        btn1.setId("prev_language");
        btn1.setOnAction((ActionEvent t)
                -> {
            int index = options.indexOf(label.getText()) - 1;
            int maxIndex = options.size();
            if (index < 0) {
                index = maxIndex - 1;
            }
            label.setText(options.get(index));
            chooseLanguage(options.get(index));
        });
        btn1.setLayoutX(25);
        btn1.setLayoutY(50);
        Button btn2 = new Button(">");
        btn2.setId("next_language");
        btn2.setOnAction((ActionEvent t)
                -> {
            int index = options.indexOf(label.getText()) + 1;
            int maxIndex = options.size();
            if (index > maxIndex - 1) {
                index = 0;
            }
            label.setText(options.get(index));
            chooseLanguage(options.get(index));
        });
        btn2.setLayoutX(120);
        btn2.setLayoutY(50);

        languageCBox = new ComboBox(options);
        languageCBox.setLayoutX(40);
        languageCBox.setLayoutY(40);
        languageCBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                try {
                    if (t1.equals(language1)) {
                        Manager.rb = PropertyResourceBundle.getBundle(Manager.LANGUAGE, Locale.GERMAN);
                        setLanguage();
                    } else if (t1.equals(language2)) {
                        Manager.rb = PropertyResourceBundle.getBundle(Manager.LANGUAGE, Locale.ENGLISH);
                        setLanguage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        p.getChildren().addAll(languageLbl, label, btn1, btn2);
    }

    public void chooseLanguage(String lang) {
        System.out.println(lang + " " + language1 + " " + language2 + " " + Manager.LANGUAGE);
        if (lang.equals(language1)) {
            Manager.rb = PropertyResourceBundle.getBundle(Manager.LANGUAGE, Locale.GERMAN);
            setLanguage();
        } else if (lang.equals(language2)) {
            Manager.rb = PropertyResourceBundle.getBundle(Manager.LANGUAGE, Locale.ENGLISH);
            setLanguage();
        }
    }

    public void setLanguage() {
        optionButton = Manager.rb.getString("Options");
        soundLabel = Manager.rb.getString("Sound");
        muteButton = Manager.rb.getString("Mute");
        languageLabel = Manager.rb.getString("Language");
        addressLbl.setText(manager.rb.getString("MobileInfo") + ": " + manager.getMobileManager().getNetworkManager().getHttpServer().getAddressText());
        optionBtn.setText(optionButton);
        muteBtn.setText(muteButton);
        soundLbl.setText(soundLabel);
        languageLbl.setText(languageLabel);
    }
}
