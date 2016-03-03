package thegamebrett.gui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
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
    private Manager manager;
    
    public static void main(String[] args) {
        launch(new String[0]);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        stage.setTitle("The Game Brett");
        
        manager = new Manager(this);
        gameView = new GameView();
        
        ArrayList<User> al = new ArrayList<User>();
        al.add(new User(null));
        al.add(new User(null));
        al.add(new User(null));
        al.add(new User(null));
        Model gameModel = D_GameFactory.createGame(al);
        gameView.setGameModel(gameModel);
        
        stage.setOnCloseRequest((WindowEvent we) -> {
            Platform.exit();
            System.exit(0);
        });
        
        Scene scene = new Scene(gameView, 500, 500, Color.DARKGRAY);
        
        stage.setScene(scene);
        stage.show();
        
        manager.startGame(gameModel);
        
    }

    public GameView getGameView() {
        return gameView;
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
