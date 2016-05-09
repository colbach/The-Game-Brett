/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.gui;

import com.sun.javafx.tk.Toolkit;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import thegamebrett.model.Layout;

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
        this.gc.strokeRect(0, 0, width, height);
        this.gc.setFill(Color.BLACK);
        
        double down = 30;
        width = width-60;
        
        gc.setFill(Color.BLACK);
        final double titelSize = 30;
        gc.setFont(Font.font("Arial", FontWeight.THIN, titelSize));
        
        String line = "";
        for (String word : text.split(" ")) {
            if (textWidth(line + " " + word, gc) > width - 2 && line.length() != 0) {
                down += textHeight(line, gc);
                double off = 30;
                //off = (width - textWidth(line, gc)) / 2d;
                gc.fillText(line, off, down);
                line = "";
            } else {
                line += " " + word;
            }
        }
        if (line.length() != 0) {
            down += textHeight(line, gc);
            double off = 30;
            //off = (width - textWidth(line, gc)) / 2d;
            gc.fillText(line, off, 2 + down);
            line = "";
        }
    }
    
    private static float textWidth(String s, GraphicsContext gc) {
        return Toolkit.getToolkit().getFontLoader().computeStringWidth(s, gc.getFont());
    }

    private static float textHeight(String s, GraphicsContext gc) {
        return Toolkit.getToolkit().getFontLoader().getFontMetrics(gc.getFont()).getLineHeight();
    }
    
    public void rotate(double degree) {
        this.setRotate(degree);
    }
}
