/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import GUI.GameView;
import MADN.MADN_Field;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board_Creator extends Thread {
    //private final List<Rectangle> rectangleList;
    private ArrayList<MADN_Field> fields = new ArrayList<>();
    private Rectangle rect;
    private GameView group;
    private double width, height;
    private double count;  // anzahl der felder
    private double scaleFactor = 100;
    private double rectLen, rectWith;
    private double increment;
    
    public Board_Creator(ArrayList<MADN_Field> fields, GameView g) {
        setDaemon(true);
        setName("Field " + count);
        
        this.fields = fields;
        this.width = fields.get(0).getWidthRelative();
        this.height = fields.get(0).getHeightRelative();
        this.count = fields.size();
        this.group = g;
        
        this.rectLen = height  * scaleFactor;
        this.rectWith = width  * scaleFactor;
        this.increment = 10 * scaleFactor;
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
                    System.out.println("Feld: " + i);
                    double relativeX = fields.get(i).getPosition().getXRelative() * scaleFactor;
                    double relativeY = fields.get(i).getPosition().getYRelative() * scaleFactor;
                    System.out.println("X: " + relativeX + "   ||   Y: " + relativeY);
                    fieldCreator(relativeX, relativeY, i);
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
