/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import GUI.GameView;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board_Creator1 extends Thread {
    //private final List<Rectangle> rectangleList;
    private Rectangle rect;
    private GameView group;
    private double width, height;
    private double x, y;                 // werte (10) abhaengig von screenresolution machen
    private double count;  // anzahl der felder
    private double scaleFactor = 1.087;
    private double rectLen, rectWith;
    private double increment;
    
    public Board_Creator1(int w, int h, int c, GameView g) {
        //setDaemon(false);
        setDaemon(true);
        setName("Field " + count);
        
        this.width = w;
        this.height = h;
        this.count = c;
        this.group = g;
        
        this.x = 20  * scaleFactor;
        this.y = 25  * scaleFactor;
        this.rectLen = 95  * scaleFactor;
        this.rectWith = 95  * scaleFactor;
        this.increment = 107 * scaleFactor;
        //this.rectangleList = al;      // anstelle von count die felder mit der liste erzeugen
    }
    
    public void run() {
        // UI Update
        Platform.runLater(new Runnable() {
            
            @Override
            public void run(){
                System.out.println("Create Fields!");
                for(int i = 0; i < count; i++)
                {
                    if(i < 10)
                    {
                      fieldCreator(x, y, i);
                      x = x + increment;                  // werte (110) abhaengig von screenresolution machen
                    }
                    else if((i > 9) && (i < 15))
                    {
                      fieldCreator(x, y, i);
                      y = y + increment;                 // werte (110) abhaengig von screenresolution machen
                    }
                    else if((i > 14) && (i < 25))
                    {
                      fieldCreator(x, y, i);
                      x = x - increment;

                    }
                    else if((i > 24) && (i < 30))
                    {
                      fieldCreator(x, y, i);
                      y = y - increment;
                    }
                }
            }
        });
    }
    
    private void fieldCreator(double x, double y, int i)
    {
        rect = new Rectangle(x, y, rectLen, rectWith);    // werte (100) abhaengig von screenresolution machen
                rect.setFill(Color.WHITESMOKE);
                rect.setOnMouseClicked( click -> {
                    System.out.println("Field " + i);
                    });
        rect.toBack();
        group.getChildren().add(rect);
        group.addField(rect);
    }
}