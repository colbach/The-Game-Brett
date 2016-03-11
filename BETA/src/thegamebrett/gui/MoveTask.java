package thegamebrett.gui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import thegamebrett.gui.GameView;

public class MoveTask extends Task {
        
    private Canvas c;
    
    private double startX;
    private double startY;
    private double endX;
    private double endY;
        
    private static final long updateRate = 100;
    private long delay;
    
    private double moveX;
    private double moveY;
    
    public MoveTask(Canvas c, Transition t, long delay) {
                
        c.setLayoutX(t.getOldX());
        c.setLayoutX(t.getOldY());
        this.c = c;
        
        this.startX = t.getOldX();
        this.startY = t.getOldY();
        
        this.endX = t.getNewX();
        this.endY = t.getNewY();
        
        this.moveX = t.getNewX() - t.getOldX();
        this.moveY = t.getNewY() - t.getOldY();
        
        this.delay = delay;
        
        System.out.println("moveX=" + moveX + " moveY=" + moveY);
    }
    
    @Override
    protected Object call() throws Exception {
        
        long startTime = System.currentTimeMillis();
        long endTime = startTime+delay;
        
        System.out.println("startTime="+startTime+" endTime="+endTime);
        long currentMillis;
        while (endTime > (currentMillis = System.currentTimeMillis())) {
            System.out.println("*");
            long passedTime = currentMillis - startTime;
            double part = passedTime / (double)delay;
            
            c.setLayoutX(startX + part*moveX);
            c.setLayoutY(startY + part*moveY);            
            
            try {
                    Thread.sleep(updateRate);
            } catch (InterruptedException ex) { }
        }
        System.out.println("=");
        c.setLayoutX(endX);
        c.setLayoutY(endY);
        
        return null;
    }
    
}