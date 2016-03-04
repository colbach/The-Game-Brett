package thegamebrett.gui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import thegamebrett.Manager;
import thegamebrett.game.dummy.D_GameFactory;
import thegamebrett.model.Model;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;
import thegamebrett.network.User;

/**
 *
 * @author christiancolbach
 */
public class MenueView extends Group {

    Manager manager;
    
    public MenueView(Manager manager) {
        
        this.manager = manager;
        
        Canvas can1 = new Canvas(100,100);
        Canvas can2 = new Canvas(100,100);
        Canvas can3 = new Canvas(100,100);
        
        can1.setLayoutX(10);
        can1.setLayoutY(10);
        
        can2.setLayoutX(100);
        can2.setLayoutY(100);
        
        can3.setLayoutX(300);
        can3.setLayoutY(300);
        
        
        GraphicsContext gc1 = can1.getGraphicsContext2D();
        gc1.setFill(Color.ALICEBLUE);
        gc1.fillRect(0, 0, 100, 100);
        GraphicsContext gc2 = can2.getGraphicsContext2D();
        gc2.setFill(Color.BLACK);
        gc2.fillRect(0, 0, 100, 100);
        GraphicsContext gc3 = can3.getGraphicsContext2D();
        gc3.setFill(Color.CRIMSON);
        gc3.fillRect(0, 0, 100, 100);
        
        can3.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            startDummy();
        });
        
        can1.toFront();
        
        this.getChildren().addAll(can1,can2,can3);
        
        
        
    }
    
    public void startDummy() {
        ArrayList<User> al = new ArrayList<User>();
        al.add(new User(null));
        al.add(new User(null));
        al.add(new User(null));
        al.add(new User(null));
        
        try {
            Model gameModel = D_GameFactory.createGame(al);
            manager.getGui().getGameView().setGameModel(gameModel);
            manager.startGame(gameModel);
        } catch (TooMuchPlayers ex) {
            Logger.getLogger(MenueView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TooFewPlayers ex) {
            Logger.getLogger(MenueView.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        
        
        
    }
    
    
    
    
}
