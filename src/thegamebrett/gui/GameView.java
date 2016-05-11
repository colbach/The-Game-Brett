package thegamebrett.gui;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import thegamebrett.Manager;
import thegamebrett.action.request.GameEndRequest;
import thegamebrett.action.request.InteractionRequestFromGUI;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.network.PlayerNotRegisteredException;
import thegamebrett.usercharacter.UserCharacter;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class GameView extends Group {

    public static final int GUIUPDATE_FIELDS = 1;
    public static final int GUIUPDATE_FIGURES = 2;
    public static final int GUIUPDATE_BOARDLAYOUT = 4;
    public static final int GUIUPDATE_ALL = GUIUPDATE_FIELDS + GUIUPDATE_FIGURES + GUIUPDATE_BOARDLAYOUT;

    public Rectangle r = new Rectangle();

    private Model gameModel = null;
    private final Manager manager;

    private RotatingTextField rotatingTextField;

    Group groupBack;
    Group groupMid;
    Group groupTop;
    Group groupUserImageCicles;

    public GameView(Manager manager) {
        super();
        this.manager = manager;
        groupBack = new Group();
        groupMid = new Group();
        groupTop = new Group();
        groupUserImageCicles = new Group();
        getChildren().add(groupBack);
        getChildren().add(groupMid);
        getChildren().add(groupTop);
        getChildren().add(groupUserImageCicles);

    }

    public void setRotatingTextField(String message, Player p) {
        int width = ScreenResolution.getScreenWidth() * 1 / 3;
        int height = ScreenResolution.getScreenHeigth() * 1 / 3;

        Platform.runLater(() -> {
            rotatingTextField = new RotatingTextField(width, height, message);

            rotatingTextField.setLayoutX(ScreenResolution.getScreenWidth() / 2 - width / 2);
            rotatingTextField.setLayoutY(ScreenResolution.getScreenHeigth() / 2 - height / 2);
            if (p != null) {
                int playerPosition = p.getUser().getSittingPlace();

                switch (playerPosition) {
                    case (0):
                        rotatingTextField.rotate(240);
                        break;
                    case (1):
                        rotatingTextField.rotate(270);
                        break;
                    case (2):
                        rotatingTextField.rotate(320);
                        break;
                    case (3):
                        rotatingTextField.rotate(0);
                        break;
                    case (4):
                        rotatingTextField.rotate(60);
                        break;
                    case (5):
                        rotatingTextField.rotate(90);
                        break;
                    case (6):
                        rotatingTextField.rotate(150);
                        break;
                    case (7):
                        rotatingTextField.rotate(180);
                        break;
                }
            } else {
                rotatingTextField.rotate(0);
            }
            groupUserImageCicles.getChildren().add(rotatingTextField);
        });

    }

    public void removeRotatingTextField() {
        if (rotatingTextField != null) {
            Platform.runLater(() -> {
                groupUserImageCicles.getChildren().remove(rotatingTextField);
            });
        }
    }

    public void setGameModel(Model gameModel) {
        this.gameModel = gameModel;
        groupBack.getChildren().clear();
        groupMid.getChildren().clear();
        groupTop.getChildren().clear();
        groupUserImageCicles.getChildren().clear();

        update(GUIUPDATE_ALL, false, 0);
        addUserImageCircles(gameModel);

    }

    public Model getGameModel() {
        return gameModel;
    }

    public void updateOnFXThread(int value, boolean animated, int delay) {
        Platform.runLater(() -> {
            update(value, animated, delay);
        });
    }

    private void update(int value, boolean animated, int delay) {

        if (gameModel != null) {
            if (value < 0 || value > 7) {
                throw new IllegalArgumentException("0 <= value <= 7");
            }

            boolean updateBoardLayout = value >= 4;
            if (updateBoardLayout) {
                value -= 4;
            }
            boolean updateFigures = value >= 2;
            if (updateFigures) {
                value -= 2;
            }
            boolean updateFields = value == 1;

            if (updateFields) {
                Canvas[] updatedFields = GUILoader.createFields(gameModel.getBoard()).getFirst();
                groupMid.getChildren().clear();
                groupMid.getChildren().addAll(updatedFields);
            }

            if (updateBoardLayout) {
                Canvas updatedBoardBackground = GUILoader.createBoardBackground(gameModel.getBoard().getLayout());
                groupBack.getChildren().clear();
                groupBack.getChildren().add(updatedBoardBackground);
            }

            if (updateFigures) {
                GUILoader.createFigures(gameModel, groupTop.getChildren());

                for (Node c : groupTop.getChildren()) {
                    Transition t = (Transition) c.getUserData();
                    if (t.getNewX() != t.getOldX() || t.getNewY() != t.getOldY()) {
                        TranslateTransition tt = new TranslateTransition(Duration.millis(2000), c);

                        tt.setByX(t.getNewX() - t.getOldX());
                        tt.setByY(t.getNewY() - t.getOldY());
                        tt.play();
                    }

                }
            }
        }

    }

    private class GameEndTask extends TimerTask {

        private final GameView gv;
        private final GameEndRequest ger;

        public GameEndTask(GameView gv, GameEndRequest ger) {
            this.gv = gv;
            this.ger = ger;
        }

        public void run() {
            gv.gameEnd(ger);
        }
    }

    public void handleGameEndRequest(GameEndRequest ger) {
        Timer timer = new Timer();
        timer.schedule(new GameEndTask(this, ger), ger.getDelay());
    }

    private void gameEnd(GameEndRequest ger) {
        System.out.println("Game Over");

        Platform.runLater(() -> {
            groupUserImageCicles.getChildren().add(0, GUILoader.createGameEndScreen(ger));
            Button b = new Button(Manager.rb.getString("Ok"));
            b.setPrefWidth(50d);
            b.setLayoutX(ScreenResolution.getScreenWidth() / 2 - 25d);
            b.setLayoutY(ScreenResolution.getScreenHeigth() / 3 * 2 + 60);

            for (Player player : gameModel.getPlayers()) {
                try {
                    manager.getMobileManager().react(new InteractionRequestFromGUI(ger.getAcknowledgment(), new Object[]{Manager.rb.getString("Ok")}, player, true, gameModel));
                } catch (PlayerNotRegisteredException ex) {
                    System.err.println("PlayerNotRegisteredException");
                }
            }
            b.setOnAction((e) -> {
                gameEndButtonClick(gameModel);
            });
            groupUserImageCicles.getChildren().add(b);
        });
    }

    public void gameEndButtonClick(Model concerningModel) {
        if (concerningModel == gameModel) {
            manager.getMobileManager().getNetworkManager().getNetworkGameSelector().endGame();
            Platform.runLater(() -> {
                manager.getGui().showMenuScene();
            });
        }
    }

    public void addUserImageCircles(Model model) {
        ArrayList<Player> us = model.getPlayers();
        ArrayList<Canvas> uics = new ArrayList<>();
        Platform.runLater(() -> {
            for (int i = 0; i < us.size(); i++) {
                if (us.get(i) != null && us.get(i).getUser() != null && us.get(i).getUser().getUserCharacter() != null) {
                    UserCharacter uic = us.get(i).getUser().getUserCharacter();
                    int size = 130;
                    int placing = 40;
                    Canvas c = GUILoader.createUserImageCircle(uic, size, size);
                    int sp = us.get(i).getUser().getSittingPlace();
                    switch (sp) {
                        case (0):
                            c.setLayoutX(ScreenResolution.getScreenWidth() - (size / 2) - placing);
                            c.setLayoutY(0 - (size / 2) + placing);
                            break;
                        case (1):
                            c.setLayoutX(ScreenResolution.getScreenWidth() - (size / 2) - placing);
                            c.setLayoutY(ScreenResolution.getScreenHeigth() / 2 - (size / 2));
                            break;
                        case (2):
                            c.setLayoutX(ScreenResolution.getScreenWidth() - (size / 2) - placing);
                            c.setLayoutY(ScreenResolution.getScreenHeigth() - (size / 2) - placing);
                            break;
                        case (3):
                            c.setLayoutX(ScreenResolution.getScreenWidth() / 2 - (size / 2));
                            c.setLayoutY(ScreenResolution.getScreenHeigth() - (size / 2) - placing);
                            break;
                        case (4):
                            c.setLayoutX(0 - (size / 2) + placing);
                            c.setLayoutY(ScreenResolution.getScreenHeigth() - (size / 2) - placing);
                            break;
                        case (5):
                            c.setLayoutX(0 - (size / 2) + placing);
                            c.setLayoutY(ScreenResolution.getScreenHeigth() / 2 - (size / 2));
                            break;
                        case (6):
                            c.setLayoutX(0 - (size / 2) + placing);
                            c.setLayoutY(0 - (size / 2) + placing);
                            break;
                        case (7):
                            c.setLayoutX(ScreenResolution.getScreenWidth() / 2 - (size / 2));
                            c.setLayoutY(0 - (size / 2) + placing);
                            break;
                    }
                    uics.add(c);
                }
            }
            System.out.println("uics.size() " + uics.size());
            groupUserImageCicles.getChildren().addAll(uics);
        });

    }

}
