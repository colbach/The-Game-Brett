/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Cenk
 */
public class UserImageCircle extends Pane {
    
    private Image image;
    
    public UserImageCircle(Image image, Color color) {
        this.image = image;
        /*double scaleFactor = 200 / image.getWidth();
        int size = 200;
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(this.getLayoutX()+size*0.05);
        imageView.setLayoutY(this.getLayoutY()+size*0.05);
        
        Circle border = new Circle(size*0.55,size*0.55,size*0.4);
        Circle space = new Circle(size*0.51,size*0.51,size*0.37);
        border.setFill(color);
        space.setFill(Color.WHITE);
        Circle clip = new Circle(size+10,size,size);
             
        
        imageView.setClip(clip);
        imageView.setScaleX(scaleFactor);
        imageView.setScaleY(scaleFactor);
        border.setLayoutX(this.getLayoutX());
        border.setLayoutY(this.getLayoutY());
        space.setLayoutX(this.getLayoutX()+image.getWidth()*0.04);
        space.setLayoutY(this.getLayoutY()+image.getHeight()*0.04);
        this.getChildren().addAll(border, space,imageView);*/
    }
    
    public void getCanvas() {
        
    }
    
}
