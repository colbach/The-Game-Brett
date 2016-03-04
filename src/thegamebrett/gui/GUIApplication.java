package thegamebrett.gui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tests.FirstTest;
import thegamebrett.Manager;
import thegamebrett.action.request.GUIRequest;
import thegamebrett.action.request.GUIUpdateRequest;
import thegamebrett.game.dummy.D_GameFactory;
import thegamebrett.model.Model;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;

public class GUIApplication extends Application{

    private GameView gameView;
    private MenueView menuView;
    private Manager manager;
    
    private Stage stage;
    
    private Scene menueScene;
    private Scene gameScene;
    
    public static void main(String[] args) {
        launch(new String[0]);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        
        stage.setTitle("The Game Brett");
        
        manager = new Manager(this);
        gameView = new GameView();
        menuView = new MenueView(manager);
        
        
        
        stage.setOnCloseRequest((WindowEvent we) -> {
            Platform.exit();
            System.exit(0);
        });
        
        
        
        //stage.setFullScreen(true);
        
        Rectangle2D dimension = Screen.getPrimary().getBounds();
        ScreenResolution.setScreenDimension((int)dimension.getWidth(), (int)dimension.getHeight());
        ScreenResolution.setBoardRatios(1, 1);
        
        showMenuScene();
        stage.show();
        
        
    }

    public GameView getGameView() {
        return gameView;
    }
    
    public void showMenuScene() {
        stage.setFullScreen(true);
        if(menueScene == null)
            menueScene = new Scene(menuView, Color.GOLD);
        stage.setScene(menueScene);
    }
    
    public void showGameScene() {
        //stage.setFullScreen(true);
        if(gameScene == null)
            gameScene = new Scene(gameView, Color.BLACK);
        stage.setScene(gameScene);
        
        
        //stage.sizeToScene();
        //stage.setFullScreen(false);
        stage.setFullScreen(true);
    }
    
    public void react(GUIRequest r) {
        if(r instanceof GUIUpdateRequest) {
            GUIUpdateRequest ur = (GUIUpdateRequest) r;
            int value = 0;
            if(ur.isUpdateBoardLayout())
                value += GameView.GUIUPDATE_BOARDLAYOUT;
            if(ur.isUpdateFields())
                value += GameView.GUIUPDATE_FIELDS;
            if(ur.isUpdateFigures())
                value += GameView.GUIUPDATE_FIGURES;
            
            System.out.println(value);
            gameView.updateOnFXThread(value, ur.isAnimated(), ur.getDelay());
        }
    }
    
}
