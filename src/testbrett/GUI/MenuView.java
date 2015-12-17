/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testbrett.GUI;

import testbrett.Manager.GameList;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Cenk Saatci
 */
public class MenuView extends Group {
    
    final private ScreenManager screenManager;
    
    final private Rectangle r;
    
    final private GameList liste;
    
    final private VBox vb;
    
    public MenuView(ScreenManager screenManager) {
        super();
        
        this.r = new Rectangle();
        
        this.screenManager = screenManager;
        
        this.r.setWidth(screenManager.getScreenWidth());
        
        this.r.setHeight(screenManager.getScreenHeight());
        
        this.liste = new GameList(screenManager);
        
        this.r.setFill(Color.DARKCYAN);
        
        this.setId(String.valueOf(Math.round(Math.random() *100))); // optionale id
        
        fillGameList();
        
        this.vb = new VBox();
        
        vb.setLayoutX(screenManager.getScreenWidth()/2 + 100);
        vb.setLayoutY(screenManager.getScreenHeight()/2);
        
        vb.getChildren().add(liste);
       
        this.getChildren().addAll(r,vb);
        
        this.screenManager.setMenuView(this);
    }
    
    // Spiele in ListView eintragen
    private void fillGameList() {

        liste.addGame(screenManager.getScreenView());
          
        liste.setPrefWidth(200);
        liste.setPrefHeight(140);
    }
    
}
