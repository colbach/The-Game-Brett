/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testbrett.GUI;

import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author 
 */
public class GameView extends Group {
    
    final private ScreenManager screenManager;
    
    final private int fieldCount = 30;
    
    final private String GameName = "Test Spiel";
    
    final private Button btn = new Button("zurueck zum menue!");
    
    final private Button btnStart = new Button("Spiel Starten");
    
    final private Button btnMove = new Button("Bewegen");
    
    final private Rectangle r = new Rectangle();
    
    private Circle circle;
    
    private double x, y;
    
    private int fieldPosition;
    
    final private ObservableList<Rectangle> fields;
    
    
    public GameView(ScreenManager screenManager) {
        super();
        
        /// soll spielfigur darstellen 
        this.circle = new Circle();
        ///
        
        this.fields = FXCollections.observableArrayList();
        
        this.screenManager = screenManager;
                
        this.r.setWidth(screenManager.getScreenWidth());
        
        this.r.setHeight(screenManager.getScreenHeight());
        
        this.r.setFill(Color.LIGHTBLUE);
        
        this.setId(String.valueOf(Math.round(Math.random() *100)));
        
        
        btn.setLayoutX(screenManager.getScreenWidth()/2 - 50);
        
        btn.setLayoutY(screenManager.getScreenHeight()/2);
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //System.out.println("Start MenuView");
                screenManager.setView(screenManager.getMenuView());
            }  
        });
        
        /// Starten
        btnStart.setLayoutX(screenManager.getScreenWidth()/2 - 50);
        
        btnStart.setLayoutY(screenManager.getScreenHeight()/2 - 100);
        
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                startGame();
                
                fieldPosition++;
                
                btnStart.setVisible(false);
                btnMove.setVisible(true);
            }  
        });
        ///
               
        /// Wuerfeln
        btnMove.setVisible(false);
        
        btnMove.setLayoutX(screenManager.getScreenWidth()/2 - 50);
        
        btnMove.setLayoutY(screenManager.getScreenHeight()/2 - 100);
        
        btnMove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
                Random rand = new Random();
               // fieldPosition = fieldPosition + rand.nextInt(6) + 1; // 6er wuerfel
                
                if(fieldPosition >= 30) {
                    fieldPosition -= 30;
                }

                Rectangle rec = fields.get(fieldPosition);

                move(rec);
                fieldPosition++;
            }  
        });
        ///
        
        this.getChildren().addAll(r,btn,btnStart,btnMove);  
    }
    
    private void startGame() {
        
        circle = new Circle(20);
        Rectangle rec = fields.get(0);
        
        circle.setLayoutX(rec.getX() + rec.getHeight()/2);
        circle.setLayoutY(rec.getY() + rec.getWidth()/2);

        circle.toFront();
        
        this.getChildren().add(circle);
    }
    
    private void move(Rectangle rec) {
        circle.setLayoutX(rec.getX() + rec.getHeight()/2);
        circle.setLayoutY(rec.getY() + rec.getWidth()/2);

        circle.toFront();
    }
    
    public String getName() {
        
        return GameName;
    }
    
    public int getFieldCount() {
        
        return fieldCount;
    }
    
    public void addField(Rectangle r) {
        
        fields.add(r);
    }
}