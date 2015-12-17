/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testbrett.Manager;

import testbrett.GUI.ScreenManager;
import testbrett.GUI.GameView;
import testbrett.model.Board_Creator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.ListView;

/**
 *
 * @author Cenk
 */
public class GameList extends ListView<String> {
    
    final private ObservableList<String> itemNames; 
    
    final private ObservableList<GameView> items; 
    
    private ScreenManager screenManager;
    
    
    public GameList(ScreenManager screenManager) {
        
        this.screenManager = screenManager;
        
        this.itemNames = FXCollections.observableArrayList();
        
        this.items = FXCollections.observableArrayList();
        
        this.setItems(itemNames);
        
        this.setPrefWidth(200);
        
        this.setPrefHeight(140);
        
        this.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            
            @Override
            public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
                System.out.println(ov.getValue() + " Index: " + getSelectionModel().getSelectedIndex());
                
                GameView g = items.get(getSelectionModel().getSelectedIndex());
                screenManager.setView(g);
                
                Board_Creator bc = new Board_Creator(0, 0, 30, g); // 30 ist die anzahl der felder, muss irgendwie übergeben werden
                bc.start();
            }
            
        });
        
    }

        
    public void addGame(GameView gameView) {
        
        this.items.add(gameView);
        this.itemNames.add(gameView.getName());
    }
    
    public ListView<String> getList() {
        
        return this;
    }
}
