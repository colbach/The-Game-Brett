/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Manager;

import Start.StartGame;
import GUI.ScreenManager;
import Actions.ActionRequest;
import Model.Model;
import javafx.scene.Group;

/**
 *
 * @author christiancolbach
 */
public class Manager {
    
    //private Model m;
    private ScreenManager sm;
    private StartGame main;
    
    
    public Manager(StartGame main) {
       this.main = main;
       this.sm = new ScreenManager(this);
       
    }
    
    public void bla(ActionRequest i) {
        
    }
    
    public void setView(Group view) {
        main.setView(view);
    }
    
    public ScreenManager getScreenManager() {
        return sm;
    }
}