package thegamebrett.gui;

import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Screen;
import javafx.util.Duration;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class MenueBar {

    private Rectangle2D dimension;
    private Canvas c;
    private Pane root;
    private boolean aktivated;

    public MenueBar() {
        dimension = Screen.getPrimary().getBounds();
        root = new Pane();
        root.setLayoutX(0);
        root.setLayoutY(dimension.getHeight() - 180);
        root.resize(dimension.getMaxX(), dimension.getMaxY());

        TranslateTransition tt = new TranslateTransition(Duration.millis(1000), root);
        tt.setCycleCount(1);
        tt.setAutoReverse(true);

        Canvas menuBar = new Canvas((int) dimension.getWidth(), 600);
        GraphicsContext gc = menuBar.getGraphicsContext2D();
        drawShapes(gc);
        root.getChildren().add(menuBar);
    }

    private void drawShapes(GraphicsContext gc) {
        gc.setLineWidth(4);
        gc.setFill(Color.gray(0.8));
        gc.fillArc(-100, 100, (int) dimension.getWidth() + 200, 350, 0, 300, ArcType.OPEN);
    }

    public Pane getRoot() {
        return root;
    }

}
