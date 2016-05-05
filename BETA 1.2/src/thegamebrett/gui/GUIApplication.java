package thegamebrett.gui;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import thegamebrett.Manager;
import thegamebrett.action.request.GUIRequest;
import thegamebrett.action.request.GUIUpdateRequest;
import thegamebrett.action.request.TimerRequest;
import thegamebrett.action.response.TimerResponse;
import thegamebrett.network.User;
import thegamebrett.timer.TimeManager;

public class GUIApplication extends Application{

    private Group root;
    private GameView gameView;
    private MenueView menuView;
    private Manager manager;
    private Stage stage;
    
    private String title = "The Game Brett";

    private ArrayList<UserImageCircle> allUserImageCircles = new ArrayList<>();
    public void updateUserImageCircles() {
        System.err.println("updateUserImageCircles()");
        User[] users = manager.getMobileManager().getUserManager().getSystemClients();
        ArrayList<UserImageCircle> uics = new ArrayList<>();
        Platform.runLater(() -> {
            for(int i=0; i<users.length; i++) {
                if(users[i] != null && users[i].getUserCharacter() != null) {
                    UserImageCircle uic = users[i].getUserCharacter().getUserImageCircle();
                    switch(i) {
                            case(0):
                                uic.setLayoutX(ScreenResolution.getScreenWidth()-(uic.getWidth()/2));
                                uic.setLayoutY(0-(uic.getHeight()/2));
                                break;
                            case(1):
                                uic.setLayoutX(ScreenResolution.getScreenWidth()-(uic.getWidth()/2));
                                uic.setLayoutY(ScreenResolution.getScreenHeigth()/2-(uic.getHeight()/2));
                                break;
                            case(2):
                                uic.setLayoutX(ScreenResolution.getScreenWidth()-(uic.getWidth()/2));
                                uic.setLayoutY(ScreenResolution.getScreenHeigth()-(uic.getHeight()/2));
                                break;
                            case(3):
                                uic.setLayoutX(ScreenResolution.getScreenWidth()/2-(uic.getWidth()/2));
                                uic.setLayoutY(ScreenResolution.getScreenHeigth()-(uic.getHeight()/2));
                                break;
                            case(4):
                                uic.setLayoutX(0-(uic.getWidth()/2));
                                uic.setLayoutY(ScreenResolution.getScreenHeigth()-(uic.getHeight()/2));
                                break;
                            case(5):
                                uic.setLayoutX(0-(uic.getWidth()/2));
                                uic.setLayoutY(ScreenResolution.getScreenHeigth()/2-(uic.getHeight()/2));
                                break;
                            case(6):
                                uic.setLayoutX(0-(uic.getWidth()/2));
                                uic.setLayoutY(0-(uic.getHeight()/2));
                                break;
                            case(7):
                                uic.setLayoutX(ScreenResolution.getScreenWidth()/2-(uic.getWidth()/2));
                                uic.setLayoutY(0-(uic.getHeight()/2));
                                break;
                    }
                    uics.add(uic);
                }
            }
            allUserImageCircles.addAll(uics);
        root.getChildren().addAll(uics);
            if(menuView != null) {
                menuView.getChildren().addAll(uics);
            } else {
                System.err.println("menuView ist null");
            }
            if(gameView != null) {
                gameView.getChildren().addAll(uics);
                
            } else {
                System.err.println("gameView ist null");
            }
        });
        
        
    }
    
    private class UserImageCirclesUpdateTask extends TimerTask {
        protected UserImageCirclesUpdateTask() {}
        public void run() {
            updateUserImageCircles();
            Timer timer = new Timer();
            timer.schedule(new UserImageCirclesUpdateTask(), 5000);
        }
    }
    
    public static void main(String[] args) {
        launch(new String[0]);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        
        Rectangle2D dimension = Screen.getPrimary().getBounds();
        ScreenResolution.setScreenDimension((int)dimension.getWidth(), (int)dimension.getHeight());
        ScreenResolution.setBoardRatios(1, 1);
        
        
        stage.setTitle(title);
        
        manager = new Manager(this);
        
        gameView = new GameView();
        menuView = new MenueView(manager);
        
        stage.setOnCloseRequest((WindowEvent we) -> {
            Platform.exit();
            System.exit(0);
        });
                
        stage.setFullScreen(true);
        root = new Group();
        Scene scene = new Scene(root, Color.DARKGRAY);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.BACK_SPACE) {
                    if(go != null)
                        hideOptions();
                    else 
                        showMenuScene();
                    
                } else if(event.getCode() == KeyCode.SPACE) {
                    showOptions();
                }
            }
        });
        showMenuScene();
        scene.getStylesheets().add(getClass().getResource("GUIStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        
        
        Timer timer = new Timer();
        timer.schedule(new UserImageCirclesUpdateTask(), 5000);
        
    }

    public GameView getGameView() {
        return gameView;
    }
    
    public void showMenuScene() {
        root.getChildren().clear();
        root.getChildren().add(menuView);
    }
    
    private GameOption go;
    public void showOptions() {
        go = new GameOption();
        root.getChildren().add(go);
    }
    
    public void hideOptions() {
        root.getChildren().remove(go);
        go = null;
    }
    
    public void showGameScene() {
        root.getChildren().clear();
        root.getChildren().add(gameView);
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
            
            //System.out.println(value);
            gameView.updateOnFXThread(value, ur.isAnimated(), ur.getDelay());
        }
    }
    
}
