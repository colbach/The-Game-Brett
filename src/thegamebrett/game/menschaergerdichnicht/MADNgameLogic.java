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
 * @author Koré
 */
public class MADNgameLogic extends GameLogic{

    private final int maximumPlayers = 4;
    private final int minimumPlayers = 2;
    
    public final static Integer INTERACTIONRESPONSE_CHOICES_DICE = new Integer(0);
    public final static Integer INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE = new Integer(1);
    
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
                if(previous.getUserData() == INTERACTIONRESPONSE_CHOICES_DICE){
                    int dicedNr = dice();
                    MADNfigure[] figures = movableFigures((MADNplayer)previous.getPlayer(), dicedNr);
                    nextRequest = new InteractionRequest("Sie haben eine "+dicedNr+" gewürfelt! Bitte eine Figur wählen!",
                            figures, (MADNplayer)previous.getPlayer(), false);
                } else if(previous.getUserData() == INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE){
                    
                } else{
                    throw new IllegalArgumentException("Illegal InteractionResponse Type");
                }
            }
        } else if(as instanceof TimerResponse){
            
        } else{
            //schmeiße exception
        } 
        return requests.toArray(new ActionRequest[0]);
    }
    
    private MADNfigure[] movableFigures(MADNplayer player, int dicedNr) {
        
        ArrayList<MADNfigure> figures = new ArrayList<>();
        MADNfield destField;
        for(int i = 0; i<player.getFigures().length; i++){
            destField = (MADNfield)player.getFigures()[i].getField();
            if(dicedNr == 6){
                
            } else{
                for(int j = 0; j<dicedNr; j++){
                    if((destField != null)&&(destField.getNext().length==1))
                        destField = destField.getNext()[0];
                    else if((destField != null)&&(destField.getNext().length>1)){
                        if(player.getLastField() == destField){
                            destField = destField.getNext()[1];                            
                        } else {
                            destField = destField.getNext()[0];                                                    
                        }
                    } else{
                        break;
                    }
                }
                if(destField != null){
                    figures.add(player.getFigures()[i]);
                }
            }
        }
        
        return figures.toArray(new MADNfigure[0]);
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
