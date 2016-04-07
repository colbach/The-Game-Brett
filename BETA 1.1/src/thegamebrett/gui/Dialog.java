/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
/**
 *
 * @author Cenk
 */
public  class Dialog extends Pane {
    
    private final Pane p;
    private final Label label;
    private final Button btn;
    private final int x;
    private final int y;
    
    public Dialog(int width, int heigth, String labelText, String buttonText) {
        
        this.x = width;
        this.y = heigth;
        this.setStyle("-fx-background-color: transparent;");
        this.setPrefSize(ScreenResolution.getScreenWidth(), ScreenResolution.getScreenHeigth());
        this.setLayoutX(0);
        this.setLayoutY(0);
        
        
        p = new Pane();
        p.setStyle("-fx-background-color: white;");
        p.setPrefSize(x, y);
        p.setLayoutX((ScreenResolution.getScreenWidth()/2)-(x/2));
        p.setLayoutY((ScreenResolution.getScreenHeigth()/2)-(y/2));
        
        label = new Label(labelText);
        label.setLayoutX(x/3.5);
        label.setLayoutY(y/3);
        
        btn = new Button(buttonText);
        btn.setLayoutX(x/3);
        btn.setLayoutY(y/1.5);
        btn.setOnAction(eh -> {
            buttonAction();
        });
        
        p.getChildren().addAll(label,btn);
        this.getChildren().add(p);
    }
    
    private void buttonAction() {
        // Button Event
    }
    
    private void setButtonPosition(int x, int y) {
        btn.setLayoutX(x);
        btn.setLayoutY(y);
    }
    
    private void setLabelPosition(int x, int y) {
        label.setLayoutX(x);
        label.setLayoutY(y);
    }   
}

