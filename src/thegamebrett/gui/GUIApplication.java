package thegamebrett.gui;

import java.io.File;
import javafx.application.Application;
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
import thegamebrett.action.request.GameEndRequest;
import thegamebrett.action.response.InteractionResponse;
import thegamebrett.model.Model;
import thegamebrett.action.request.RemoveScreenMessageRequest;
import thegamebrett.action.request.ScreenMessageRequest;
import static javafx.application.Application.launch;
import thegamebrett.assets.AssetsLoader;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class GUIApplication extends Application {

    private Group root;
    private GameView gameView;
    private MenueView menuView;
    private Manager manager;
    private Stage stage;

    private String title = "The Game Brett";

    public static void main(String[] args) {
        if(args.length > 0) {
            AssetsLoader.assetsfolder = args[0];
        } else {
            System.err.println("Keine Konsolenargumente angegeben!");
            System.err.println("Suche automatisch nach Ordner...");
            String[] possibleLocations = {
                "./src/assetsfolder/",
                "./dist/assetsfolder/",
                "./assetsfolder/",
                System.getProperty("user.home") + "/The-Game-Brett/src/assetsfolder/",
                System.getProperty("user.home") + "/assetsfolder/",
                System.getProperty("user.home") + "/GitHub/The-Game-Brett/src/assetsfolder/",
                System.getProperty("user.home") + "/Desktop/assetsfolder/",
                System.getProperty("user.home") + "/Schreibtisch/assetsfolder/",
                "/assetsfolder/"
            };
            for(String location : possibleLocations) {
                File test = new File(location);
                if(test.exists()) {
                    System.out.println(location + " existiert!");
                    AssetsLoader.assetsfolder = test.getAbsolutePath();
                    break;
                } else {
                    System.err.println(location + " existiert nicht.");
                }
            }
            if(!AssetsLoader.assetsfolder.endsWith("/")) {
                AssetsLoader.assetsfolder = AssetsLoader.assetsfolder + "/";
            }
        }
        launch(new String[0]);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        Rectangle2D dimension = Screen.getPrimary().getBounds();
        ScreenResolution.setScreenDimension((int) dimension.getWidth(), (int) dimension.getHeight());
        ScreenResolution.setBoardRatios(1, 1);
        stage.setTitle(title);
        manager = new Manager(this);
        gameView = new GameView(manager);
        menuView = new MenueView(manager);

        stage.setOnCloseRequest((WindowEvent we) -> {
            Platform.exit();
            System.exit(0);
        });

        stage.setFullScreen(true);
        root = new Group();
        Scene scene = new Scene(root, Color.LIGHTGREY);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                    if (go != null) {
                        hideOptions();
                    } else {
                        showMenuScene();
                    }

                } else if (event.getCode() == KeyCode.SPACE) {
                    showOptions();
                }
            }
        });
        showMenuScene();
        scene.getStylesheets().add(getClass().getResource("GUIStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    public GameView getGameView() {
        return gameView;
    }

    public MenueView getMenuView() {
        return menuView;
    }

    public void showMenuScene() {
        root.getChildren().clear();
        menuView.refreshGameSelectedScreen();
        root.getChildren().add(menuView);
    }

    private GameOption go;

    public void showOptions() {
        go = new GameOption();
        root.getChildren().add(go);
    }

    public void takeResponse(InteractionResponse ir) {
        if (ir.getConcerningInteractionRequest().getUserData() instanceof Model && gameView != null) {
            gameView.gameEndButtonClick((Model) ir.getConcerningInteractionRequest().getUserData());
        }
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
        if (r instanceof GUIUpdateRequest) {
            GUIUpdateRequest ur = (GUIUpdateRequest) r;
            int value = 0;
            if (ur.isUpdateBoardLayout()) {
                value += GameView.GUIUPDATE_BOARDLAYOUT;
            }
            if (ur.isUpdateFields()) {
                value += GameView.GUIUPDATE_FIELDS;
            }
            if (ur.isUpdateFigures()) {
                value += GameView.GUIUPDATE_FIGURES;
            }
            gameView.updateOnFXThread(value, ur.isAnimated(), ur.getDelay());
        } else if (r instanceof ScreenMessageRequest) {
            ScreenMessageRequest smr = (ScreenMessageRequest) r;

            gameView.setRotatingTextField(smr.getLabel(), smr.getPlayer());
        } else if (r instanceof RemoveScreenMessageRequest) {
            gameView.removeRotatingTextField();
        } else if (r instanceof RemoveScreenMessageRequest) {
            gameView.removeRotatingTextField();
        }
        if (r instanceof GameEndRequest) {
            GameEndRequest ger = (GameEndRequest) r;
            gameView.handleGameEndRequest(ger);
        }
    }

}
