package thegamebrett.gui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import thegamebrett.Manager;
import thegamebrett.game.GameCollection;
import thegamebrett.game.dummy.D_GameLogic;
import thegamebrett.model.GameFactory;
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
        
        double d = Math.min(ScreenResolution.getScreenWidth(), ScreenResolution.getScreenHeigth());
        double iconWH = d/5.3;
        int iconsInARow = Math.round((ScreenResolution.getScreenWidth() / (float)ScreenResolution.getScreenHeigth()) * 3);
        double rowWidth = iconsInARow*iconWH + (iconsInARow-1)*(iconWH/2);
        double rowMid = ScreenResolution.getScreenWidth()/2;
        double rowStart = rowMid-(rowWidth/2);
        
        int count = 0;
        int rowCount = 0;
        for(GameFactory game : GameCollection.gameFactorys) {
            
            ImageView icon = new ImageView(game.getGameIcon());
            
            icon.setLayoutX(rowStart + (1.5*count*iconWH));
            icon.setLayoutY(rowStart + (1.5*rowCount*iconWH));
            
            icon.setFitHeight(iconWH);
            icon.setFitWidth(iconWH);
            icon.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
                startGame(game);
            });
            
            getChildren().add(icon);
            
            count ++;
            if(count == iconsInARow) {
                count = 0;
                rowCount += 1;
            }
        }
        
        /*Canvas can1 = new Canvas(100,100);
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
            startGame(GameCollection.gameFactorys[0]);
        });
        
        can1.toFront();
        
        this.getChildren().addAll(can1,can2,can3);
        
        */
        
    }
    
    public void startGame(GameFactory game) {
        
        ArrayList<User> al = new ArrayList<User>();
        for(User systemUser : manager.getMobileManager().getUserManager().getSystemClients()) {
            if(systemUser != null) {
                al.add(systemUser);
            }
        }
        
        try {
            if(manager.getGui().getGameView().getGameModel() == null || !(manager.getGui().getGameView().getGameModel().getGameLogic() instanceof D_GameLogic)) {
                
                Model gameModel = game.createGame(al);
                manager.getGui().getGameView().setGameModel(gameModel);
                manager.startGame(gameModel);
                
            }
            
            manager.getGui().showGameScene();
            
        } catch (TooMuchPlayers ex) {
            Logger.getLogger(MenueView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TooFewPlayers ex) {
            Logger.getLogger(MenueView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
