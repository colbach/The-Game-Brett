/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.gui;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Cenk
 */
public class RotatingTextField extends Canvas {
    
    private final GraphicsContext gc = this.getGraphicsContext2D();
    
    public RotatingTextField(int width, int height, String text) {
        this.setWidth(width);
        this.setHeight(height);
        this.gc.setFill(Color.WHITE);
        this.gc.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.gc.setStroke(Color.BLACK);
        this.gc.setLineWidth(2);
        this.gc.strokeRect(0, 0, 150, 100);
        this.gc.setFill(Color.BLACK);
        this.gc.setTextAlign(TextAlignment.CENTER);
        this.gc.setTextBaseline(VPos.CENTER);
        this.gc.fillText(
            text, 
            Math.round(this.getWidth()  / 2), 
            Math.round(this.getHeight() / 2)
        );
    }
    
    public void rotate(double degree) {
        this.setRotate(degree);
    }
}
