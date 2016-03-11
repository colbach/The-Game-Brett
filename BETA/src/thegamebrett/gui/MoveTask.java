package thegamebrett.gui;

import javafx.scene.canvas.Canvas;
import thegamebrett.gui.GameView;

public class MoveTask {
    
    private final GameView gameView;
    private Canvas start;
    
    double startX;
    double startY;
    double endX;
    double endY;
    
    
    public MoveTask(GameView view, Canvas start, Canvas destination) {
        this.gameView   = view;
        this.start     = start;
        
        this.startX = start.getLayoutX();
        this.startY = start.getLayoutY();
        
        this.endX = destination.getLayoutX();
        this.endY = destination.getLayoutY();
    }
    
    protected Object call() throws Exception {
        double count;
        
        if(endX > endY) {
            count = endX;
        } else {
            count = endY;
        }
        
        for(double d = 0; d < count; d+=0.01) { // inkrementierung noch anpassen
            
            updatePosition();
            
            try {
                    Thread.sleep(80);
                } catch (InterruptedException ex) { }
        }
        
        return null;
    }
    
    private void updatePosition() {
        
        if(startX < endX) {
            
        }
        
        if(startY < endY) {
            
        }
    }
}