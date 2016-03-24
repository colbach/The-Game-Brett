package thegamebrett.gui;

import java.util.HashMap;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import thegamebrett.model.Model;
import thegamebrett.model.elements.Board;
import thegamebrett.model.elements.Element;
import thegamebrett.model.elements.Figure;

/**
 *
 * @author christiancolbach
 */
public class GameView extends Group {

    public static final int GUIUPDATE_FIELDS = 1;
    public static final int GUIUPDATE_FIGURES = 2;
    public static final int GUIUPDATE_BOARDLAYOUT = 4;
    public static final int GUIUPDATE_ALL = GUIUPDATE_FIELDS + GUIUPDATE_FIGURES + GUIUPDATE_BOARDLAYOUT;

    public Rectangle r = new Rectangle();
    //private Canvas[] fields = new Canvas[0];
    //private Canvas[] figures = new Canvas[0];

    private Model gameModel = null;

    Group groupBack;
    Group groupMid;
    Group groupTop;
    
    public GameView() {
        super();

        groupBack = new Group();
        groupMid = new Group();
        groupTop = new Group();
        getChildren().add(groupBack);
        getChildren().add(groupMid);
        getChildren().add(groupTop);

    }

    public void setGameModel(Model gameModel) {
        this.gameModel = gameModel;
        groupBack.getChildren().clear();
        groupMid.getChildren().clear();
        groupTop.getChildren().clear();

        update(GUIUPDATE_ALL, false, 0);

    }

    public Model getGameModel() {
        return gameModel;
    }

    public void updateOnFXThread(int value, boolean animated, int delay) {
        Platform.runLater(()->{
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
            
            /*try {
                    Thread.sleep(1000);
            } catch (InterruptedException ex) { }*/
            
            if (updateFigures) {
                /*Canvas[] updatedFigures =*/ GUILoader.createFigures(gameModel, groupTop.getChildren());
                //groupTop.getChildren().clear();
                //groupTop.getChildren().addAll(updatedFigures);
                
                for(Node c : groupTop.getChildren()) {
                    Transition t = (Transition)c.getUserData();
                    if(t.getNewX() != t.getOldX() || t.getNewY() != t.getOldY()) {
                        TranslateTransition tt = new TranslateTransition(Duration.millis(2000), c);
                    
                        tt.setByX(t.getNewX()-t.getOldX());
                        tt.setByY(t.getNewY()-t.getOldY());
                        //System.out.println(t.getNewX() + " " + t.getOldX() + " " + t.getNewY() + " " + t.getOldY());
                        //System.out.println("t.newX-t.oldX="+(t.getNewX()-t.getOldX()) + " t.newY-t.oldY=" + (t.getNewY()-t.getOldY()));
                        tt.play();
                    }
                    
                }
            }
        }

    }
    
}
