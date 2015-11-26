/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.menschaergerdichnicht;
import java.util.ArrayList;
import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.action.response.InteractionResponse;
import thegamebrett.action.response.TimerResponse;
import thegamebrett.model.GameLogic;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Field;

/**
 *
 * @author Koré
 */
public class MADNgameLogic extends GameLogic{

    private final int maximumPlayers = 4;
    private final int minimumPlayers = 2;
    
    private ActionRequest expected;
    
    public MADNgameLogic(Model dependingModel) {
        super(dependingModel);
    }

    @Override
    public ActionRequest[] next(ActionResponse as) {
        ArrayList<ActionRequest> requests = new ArrayList<>();
        ActionRequest nextRequest;
        //ein haufen if else anweisungen
        if(as instanceof InteractionResponse){
            InteractionRequest previous = ((InteractionResponse) as).getConcerningInteractionRequest();
            if(previous == expected){
                
            }
        } else if(as instanceof TimerResponse){
            
        } else{
            //schmeiße exception
        } 
        return requests.toArray(new ActionRequest[0]);
    }
    
    @Override
    public Field getNextStartPositionForPlayer(Player player) {
        //was ist der sinn hiervon? Wenn ihr die Figuren meint muss das ein array sein, da es davon 4 gibt
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int dice(){
        //random nr zwischen 1 und 6
        int random = (int)(Math.random()*6)+1;
        return random;
    }
    
    @Override
    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    @Override
    public int getMinimumPlayers() {
        return minimumPlayers;
    }  
}
