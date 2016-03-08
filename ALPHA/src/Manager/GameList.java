/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import GUI.ScreenManager;
import GUI.GameView;
import MADN.MADN_Board;
import MADN.MADN_Field;
import MADN.MADN_GameFactory;
import Model.Board_Creator;
import Model.Board_Creator1;
import Model.Elements.Field;
import Model.GameFactory;
import java.util.ArrayList;
import javafx.application.Platform;
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

//                MADN_Board mb = new MADN_Board();
    
//                ArrayList<MADN_Field> fields = mb.getFields();
//                System.out.println(mb.getFieldLength());
                
                Board_Creator1 bc = new Board_Creator1(140,200,30, g); 
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
