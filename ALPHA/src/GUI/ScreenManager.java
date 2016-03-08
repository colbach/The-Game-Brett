/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import Actions.ActionRequest;
import Manager.Manager;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.stage.Screen;

/**
 *
 * @author christiancolbach
 */
public class ScreenManager {
    
    private GameView screenView;
    private MenuView menuView;
    private Manager manager;
    private final Rectangle2D primaryScreenBounds;
    
    public ScreenManager(Manager manager) {
        
        this.manager = manager;
        this.primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        this.screenView = new GameView(this);  
    }
    
    public void bla(ActionRequest i) {
        
    }
    
    public void setScreenView(GameView screenView) {
        this.screenView = screenView;
    }
    public GameView getScreenView() {
        return screenView;
    }
    
    public void setMenuView(MenuView menuView) {
        this.menuView = menuView;
    }
    
    public MenuView getMenuView() {
        return menuView;
    }
    
    public void setView(Group view) {
        manager.setView(view);
    }
    
    public int getScreenWidth() {
        return (int)primaryScreenBounds.getWidth();
    }
    
    public int getScreenHeight() {
        return (int)primaryScreenBounds.getHeight();
    }
}