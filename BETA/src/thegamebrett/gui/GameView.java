package thegamebrett.gui;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import thegamebrett.gui.animation.SpriteAnimation;
import thegamebrett.model.Model;
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

        System.out.println(getChildren().size());
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

            if (updateFigures) {
                Canvas[] updatedFigures = GUILoader.createFigures(gameModel);
                groupTop.getChildren().clear();
                groupTop.getChildren().addAll(updatedFigures);
            }

            if (updateFields) {
                Canvas[] updatedFields = GUILoader.createFields(gameModel.getBoard());
                groupMid.getChildren().clear();
                groupMid.getChildren().addAll(updatedFields);
            }

            if (updateBoardLayout) {
                Canvas updatedBoardBackground = GUILoader.createBoardBackground(gameModel.getBoard().getLayout());
                groupBack.getChildren().clear();
                groupBack.getChildren().add(updatedBoardBackground);
            }
        }

    }
    
    private Animation createAnimation(Image img, int columns, int count, int offX, int offY, int width, int height) {
        
        int COLUMNS  =  columns; //   4;
        int COUNT    =  count;   //  10;
        int OFFSET_X =  offX;    //  18;
        int OFFSET_Y =  offY;    //  25;
        int WIDTH    =  width;   // 374;
        int HEIGHT   =  height;  // 243;
        
        
        final ImageView imageView = new ImageView(img);       // create imageView with animation image | ImageView mit animationsbild erstellen
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT)); // create view in an 2D Rectangle | view in einem 2D Rechteck erstellen
        
        // create animation with param. and cycle duration of 1sec.
        // animation mit parameter erstellen und wiederholungsdauer auf 1sec. setzen
        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        animation.setCycleCount(Animation.INDEFINITE);  // set cyclecount to infinity | anzahl wiederholungen auf unendlich setzen
        //animation.setCycleCount(1);
        animation.play();                               // set animation mode to play | animation abspielen

        // keine ahnung wie animation zu canvas bzw. layout zuweisen....
        return animation;
    }

    public void updateFigurePositionX(Figure figure, double positionX) {
        figure.setRelativePositionX(positionX+1); // .setRelativePositionX(double d) implementieren und inkrementierung anpassen
    }

    public void updateFigurePositionY(Figure figure, double positionY) {
        figure.setRelativePositionY(positionY+1); // .setRelativePositionY(double d) implementieren und inkrementierung anpassen
    }
}
