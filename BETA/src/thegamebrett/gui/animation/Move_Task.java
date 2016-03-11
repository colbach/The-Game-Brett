package thegamebrett.gui.animation;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegamebrett.gui.GameView;
import thegamebrett.model.RelativePoint;
import thegamebrett.model.elements.Field;
import thegamebrett.model.elements.Figure;

/**
 *
 * @author Cenk
 */
public class Move_Task {
    
    private final GameView gameView;
    private Figure figure;
    
    double startX;
    double startY;
    double endX;
    double endY;
    
    
    public Move_Task(GameView view, Figure figure, Field destination) {
        this.gameView   = view;
        this.figure     = figure;
        
        this.startX = figure.getField().getRelativePosition().getXOnScreen();
        this.startY = figure.getField().getRelativePosition().getYOnScreen();
        
        this.endX = destination.getRelativePosition().getXOnScreen();
        this.endY = destination.getRelativePosition().getYOnScreen();
    }
    
    protected Object call() throws Exception {
        double count;
        
        if(endX > endY) {
            count = endX;
        } else {
            count = endY;
        }
        
        for(double d = 0; d < count; d++) { // inkrementierung noch anpassen
            
            updatePosition();
            
            try {
                    Thread.sleep(80);
                } catch (InterruptedException ex) {   
                }
        }
        
        return null;
    }
    
    private void updatePosition() {
        RelativePoint rPosition = figure.getRelativePosition();

        if(startX < endX) {
            gameView.updateFigurePositionX(figure, rPosition.getXOnScreen());
        }
        
        if(startY < endY) {
            gameView.updateFigurePositionY(figure);
        }
    }
}
