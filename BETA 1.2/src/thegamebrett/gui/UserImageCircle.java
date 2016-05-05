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
    
    public UserImageCircle(Image image, Color color) {
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(this.getLayoutX()+image.getWidth()*0.05);
        imageView.setLayoutY(this.getLayoutY()+image.getHeight()*0.05);
        Circle border = new Circle(image.getWidth()*0.55,image.getHeight()*0.55,image.getWidth()*0.4);
        Circle space = new Circle(image.getWidth()*0.51,image.getHeight()*0.51,image.getWidth()*0.37);
        border.setFill(color);
        space.setFill(Color.WHITE);
        Circle clip = new Circle(image.getWidth()*0.5,image.getHeight()*0.5,image.getWidth()*0.35);
             
               
        imageView.setClip(clip);
        border.setLayoutX(this.getLayoutX());
        border.setLayoutY(this.getLayoutY());
        space.setLayoutX(this.getLayoutX()+image.getWidth()*0.04);
        space.setLayoutY(this.getLayoutY()+image.getHeight()*0.04);
        this.getChildren().addAll(border, space,imageView);
    }
    
}
