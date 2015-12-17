package testbrett;


import testbrett.GUI.MenuView;
import testbrett.GUI.GameView;
import testbrett.Manager.Manager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cenk Saatci
 */
public class StartGame extends Application{
    
    private Group menuView;
    private Group gameView;
    private Manager manager;
    private int width;
    private int height;
    private Stage stage;
    private Scene menuScene;
    private Scene gameScene;
    
    
    public static void main(String[] args) {
        
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        
        manager = new Manager(this);
        
        menuView = new MenuView(manager.getScreenManager());
        // evtl veraendern, da gameview aus liste von spielen ausgewaehlt wird
        gameView = new GameView(manager.getScreenManager());
        
        width = (int)primaryScreenBounds.getWidth();
        
        height = (int)primaryScreenBounds.getHeight();
        
        menuScene = new Scene(menuView, width, height, Color.DARKGRAY);       
        
        stage.setTitle("Board Test");
        stage.setScene(menuScene);
        stage.show();
        System.out.println(gameView.getId());
        System.out.println(menuView.getId());
        System.out.println();
    }
    
    public void setView(Group view) {
        System.out.println(view.getId());
        System.out.println(this.gameView.getId());
        System.out.println(this.menuView.getId());
        
        if(view.getId() == this.menuView.getId()) {
            System.out.println("view == menuView");
            stage.setScene(menuScene);
        }
        else if (view.getId() == this.gameView.getId()) {           
            // problem, gameview kann nicht zum zweiten mal nach 
            // wechsel zum menue gestartet werden. abfrage greift nicht!
            System.out.println("view == gameView");
            stage.setScene(gameScene); 
        }
        else {
            System.out.println("view == neueView");
            gameScene = new Scene(view, width, height, Color.DARKGRAY);
            stage.setScene(gameScene);
            //
            
            Button btn2 = new Button("Weiter");
            
            btn2.setLayoutX(width/2 + 100);
            btn2.setLayoutY(height/2 + 140);
        
            btn2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    stage.setScene(gameScene);
                }
            });
            menuView.getChildren().add(btn2);
            //
        }
        System.out.println("");
        stage.show();
    }   
}
