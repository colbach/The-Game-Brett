package thegamebrett.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
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

    private Manager manager;
    private int soundValue;
    private boolean aktivated = false;
    private boolean charWindow = false;
    private boolean muted = false;
    private double textScaleFactor;
    
    private Button optionBtn;
    private Button muteBtn;
    private Button characterBtn;
    private Label soundLbl;
    private Label characterLbl;
    private Label textSizeLbl;
    private Label languageLbl;
    private Slider soundSlider;
    private Slider textSizeSlider;
    private ComboBox languageCBox;
    
    private String optionButton;
    private String soundLabel;
    private String muteButton;
    private String characterButton;
    private String characterLabel;
    private String textSizeLabel;
    private String languageLabel;
    private String language1 = "Deutsch";
    private String language2 = "English";
    
    public MenueView(Manager manager) {
        
        this.manager = manager;
        double d = Math.min(ScreenResolution.getScreenWidth(), ScreenResolution.getScreenHeigth());
        double iconWH = d/5.3;
        int iconsInARow = Math.round((ScreenResolution.getScreenWidth() / (float)ScreenResolution.getScreenHeigth()) * 3);
        double rowWidth = iconsInARow*iconWH + (iconsInARow-1)*(iconWH/2);
        double rowMid = ScreenResolution.getScreenWidth()/2;
        double rowStart = rowMid-(rowWidth/2);
        
        int count = 0;
        int rowCount = 0;
        for(GameFactory game : GameCollection.gameFactorys) {
            
            ImageView icon = new ImageView(game.getGameIcon());
            
            icon.setLayoutX(rowStart + (1.5*count*iconWH));
            icon.setLayoutY(rowStart + (1.5*rowCount*iconWH));
            
            icon.setFitHeight(iconWH);
            icon.setFitWidth(iconWH);
            icon.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
                startGame(game);
            });
            
            getChildren().add(icon);
            
            count ++;
            if(count == iconsInARow) {
                count = 0;
                rowCount += 1;
            }
        }
        
        Group g = new Group();
        MenueBar bar = new MenueBar();
        optionBtn = new Button(optionButton);
        optionBtn.setLayoutX(ScreenResolution.getScreenWidth()/2 -30);
        optionBtn.setLayoutY(ScreenResolution.getScreenHeigth()-50);
        
        TranslateTransition tt = new TranslateTransition(Duration.millis(500), g);
        tt.setCycleCount(1);
        tt.setAutoReverse(true);  
        
        optionBtn.setOnAction((ActionEvent t) -> 
        {
            if(!aktivated)
            {
             tt.setByY(-(120)); 
             aktivated = true;
            } else if(aktivated)
            {
             tt.setByY(120);  
             aktivated = false;
            }
           tt.play(); 
        });
        
        g.getChildren().add(bar.getRoot());
        
        Pane soundPane = new Pane();
        soundPane.setPrefSize(160, 100);
        soundPane.setLayoutX((int)(rowStart));
        soundPane.setLayoutY(ScreenResolution.getScreenHeigth());
        createVolumeOption(soundPane);
        
        Pane characterPane = new Pane();
        characterPane.setPrefSize(160, 100);
        characterPane.setLayoutX((int)(rowStart+(2*iconWH)));
        characterPane.setLayoutY(ScreenResolution.getScreenHeigth());
        //createCharacterOption(characterPane);
        
        Pane textPane = new Pane();
        textPane.setPrefSize(160, 100);
        textPane.setLayoutX((int)(rowStart+(4.2*iconWH)));
        textPane.setLayoutY(ScreenResolution.getScreenHeigth());
        createTextSizeOption(textPane);
        
        Pane languagePane = new Pane();
        languagePane.setPrefSize(160, 100);
        languagePane.setLayoutX((int)(rowStart+(6.2*iconWH)));
        languagePane.setLayoutY(ScreenResolution.getScreenHeigth());
        createLanguageOption(languagePane);
        
        g.getChildren().addAll(optionBtn,soundPane,characterPane,textPane, languagePane);
        this.getChildren().add(g);
        setLanguage();
    }
    
    public void startGame(GameFactory game) {
        
        ArrayList<User> al = new ArrayList<User>();
        for(User systemUser : manager.getMobileManager().getUserManager().getSystemClients()) {
            if(systemUser != null) {
                al.add(systemUser);
            }
        }
        
        try {
            if(manager.getGui().getGameView().getGameModel() == null || !(manager.getGui().getGameView().getGameModel().getGameLogic() instanceof D_GameLogic)) {
                
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
    
    final public void createVolumeOption(Pane m)
    {
        Pane p = m;
        
        soundLbl = new Label(soundLabel);
        soundLbl.setLayoutX(50);
        soundLbl.setLayoutY(10);
        
        soundSlider = new Slider(0,100,0);
        soundSlider.setLayoutX(10);
        soundSlider.setLayoutY(35);
        soundSlider.setPadding(new Insets(5));
        soundSlider.setShowTickLabels(false);
        soundSlider.setShowTickMarks(true);
        soundSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> 
        {
            int diff = (new_val.intValue() - soundValue);
            try {
                if (diff > 0) {
                    Process p1 = Runtime.getRuntime().exec("amixer -D pulse sset Master " + diff + "%+");
                } else {
                    Process p2 = Runtime.getRuntime().exec("amixer -D pulse sset Master " + diff + "%-");
                }
            }catch (IOException ex) 
            {
                System.out.println(ex);
            }
            soundValue = new_val.intValue();
        });
        
        muteBtn = new Button(muteButton);
        muteBtn.setLayoutX(55);
        muteBtn.setLayoutY(80);
        muteBtn.setOnAction((ActionEvent t) -> 
        {
            int sound;
            if(muted) 
            {
                sound = soundValue;
                muted = false;
            } else
            {
                sound = 0;
                muted = true;
            }
            //System.out.println(muted);
            try {
                Process p1 = Runtime.getRuntime().exec("amixer -D pulse sset Master " + sound + "%+");
            }catch (IOException ex) 
            {
                ex.printStackTrace();
            }
        });
        p.getChildren().addAll(soundLbl,soundSlider, muteBtn);
    }
    
    /*final public void createCharacterOption(Pane m)
    {
        Pane p = m;
        
        Rectangle2D dimension = Screen.getPrimary().getBounds();
        int x = (int)(dimension.getWidth()*0.4);
        int y = (int)(dimension.getHeight()*0.6);
        
        Pane characterPane = new Pane();
        characterPane.setStyle("-fx-background-color: white;");
        characterPane.setPrefSize(x, y);
        characterPane.setLayoutX((dimension.getWidth()/2)-(x/2));
        characterPane.setLayoutY((dimension.getHeight()/2)-(y/2));
        
        characterBtn = new Button(characterButton);
        characterBtn.setLayoutX(20);
        characterBtn.setLayoutY(40);
        characterBtn.setOnAction((ActionEvent t) -> 
        {
            if(!charWindow)
            {
                this.getChildren().add(characterPane);
                charWindow = true;
            } else if(charWindow)
            {
                this.getChildren().remove(characterPane);
                charWindow = false;
            }
        });
        
        characterLbl = new Label(characterLabel);
        characterLbl.setLayoutX(55);
        characterLbl.setLayoutY(10);
        
        p.getChildren().addAll(characterLbl, characterBtn);
    }*/
    
    final public void createTextSizeOption(Pane m)
    {
        Pane p = m;
        
        textSizeSlider = new Slider(0,200,0);
        textSizeSlider.setLayoutX(15);
        textSizeSlider.setLayoutY(35);
        textSizeSlider.setPadding(new Insets(5));
        textSizeSlider.setShowTickLabels(true);
        textSizeSlider.setShowTickMarks(true);
        textSizeSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> 
        {
            textScaleFactor = (double)new_val/100;
            //System.out.println(textScaleFactor);
        });
        textSizeLbl = new Label(textSizeLabel);
        textSizeLbl.setLayoutX(55);
        textSizeLbl.setLayoutY(10);

        p.getChildren().addAll(textSizeLbl, textSizeSlider);
    }
    
    final public void createLanguageOption(Pane m)
    {
        Pane p = m;
        
        languageLbl = new Label(languageLabel);
        languageLbl.setLayoutX(60);
        languageLbl.setLayoutY(10);
        
        ObservableList<String> options = 
        FXCollections.observableArrayList
        (
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
        btn1.setOnAction((ActionEvent t) -> 
        {
            int index = options.indexOf(label.getText())-1;
            int maxIndex = options.size();
            if(index < 0)
            {
                index = maxIndex-1;
            }
            label.setText(options.get(index));
            chooseLanguage(options.get(index));
        });
        btn1.setLayoutX(25);
        btn1.setLayoutY(50);
        Button btn2 = new Button(">");
        btn2.setId("next_language");
        btn2.setOnAction((ActionEvent t) -> 
        {
            int index = options.indexOf(label.getText())+1;
            int maxIndex = options.size();
            if(index > maxIndex-1)
            {
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
        languageCBox.valueProperty().addListener(new ChangeListener<String>() 
        {    
            @Override 
            public void changed(ObservableValue ov, String t, String t1) 
            {
                try
                {
                    if(t1.equals(language1))
                    {
                        Manager.rb = PropertyResourceBundle.getBundle(Manager.LANGUAGE, Locale.GERMAN);
                        setLanguage();
                    }
                    else if(t1.equals(language2))
                    {
                        Manager.rb = PropertyResourceBundle.getBundle(Manager.LANGUAGE, Locale.ENGLISH);
                        setLanguage();
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }    
        });
        p.getChildren().addAll(languageLbl, label, btn1, btn2);
    } 
    
    public void chooseLanguage(String lang)
    {
        System.out.println(lang + " " + language1 + " " + language2 + " " + Manager.LANGUAGE);
        if(lang.equals(language1))
        {
            Manager.rb = PropertyResourceBundle.getBundle(Manager.LANGUAGE, Locale.GERMAN);
            setLanguage();
        }
        else if(lang.equals(language2))
        {
            Manager.rb = PropertyResourceBundle.getBundle(Manager.LANGUAGE, Locale.ENGLISH);
            setLanguage();
        }
    }
    
    public void setLanguage()
    {
        optionButton = Manager.rb.getString("Options");
        soundLabel = Manager.rb.getString("Sound");
        muteButton = Manager.rb.getString("Mute");
        //characterButton = Manager.rb.getString("CharacterOption");
        //characterLabel = Manager.rb.getString("Character");
        textSizeLabel = Manager.rb.getString("TextSize");
        languageLabel = Manager.rb.getString("Language");
        
        optionBtn.setText(optionButton);
        muteBtn.setText(muteButton);
        //characterBtn.setText(characterButton);
        soundLbl.setText(soundLabel);
        //characterLbl.setText(characterLabel);
        textSizeLbl.setText(textSizeLabel);
        languageLbl.setText(languageLabel);
    }
}
