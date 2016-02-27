package thegamebrett.game.dummy;
import thegamebrett.game.dummy.*;
import java.util.ArrayList;
import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.action.request.GUIUpdateRequest;
import thegamebrett.action.request.GameEndRequest;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.action.response.InteractionResponse;
import thegamebrett.action.response.StartPseudoResonse;
import thegamebrett.action.response.TimerResponse;
import thegamebrett.model.GameLogic;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Field;
import thegamebrett.model.elements.Figure;

public class D_GameLogic extends GameLogic{
    
    /*Fall unterscheidung die angibt ob response würfeln oder bewegen ist**/
    public final static Integer INTERACTIONRESPONSE_CHOICES_DICE = new Integer(0);
    public final static Integer INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE = new Integer(1);
    
    
    private ActionRequest expected;
    
    public D_GameLogic(Model dependingModel) {
        super(dependingModel);
    }
    
    @Override
    public ActionRequest[] next(ActionResponse as) {
        
        ArrayList<ActionRequest> requests = new ArrayList<>();

        if(as instanceof StartPseudoResonse){
            requests.add(new GUIUpdateRequest(0))
        }
        
        
        
        
        /* abfragen ob interaction **/
        if(as instanceof InteractionResponse){
            
            InteractionRequest previous = ((InteractionResponse) as).getConcerningInteractionRequest();
            /* abfragen ob die response eine antwort auf die erwartete request ist **/
            if(previous == expected){
                /* abfragen ob anfrage zum würfeln **/
                if(previous.getUserData() == INTERACTIONRESPONSE_CHOICES_DICE){
                    /* würfelanfrage bearbeiten **/
                    int dicedNr = dice();
                    D_Figure[] figures = movableFigures((D_Player)previous.getPlayer(), dicedNr);
                    nextRequest = new InteractionRequest("Sie haben eine "+dicedNr+" gewuerfelt! Bitte eine Figur waehlen!",
                            figures, (D_Player)previous.getPlayer(), false, INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE);
                    requests.add(nextRequest);
                    
                /* abfragen ob anfrage auswählen einer bestimmten figur **/
                } else if(previous.getUserData() == INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE){
                    
                    D_Figure figure = (D_Figure)(previous.getChoices()[((InteractionResponse) as).getChoiceIndex()]);
                    soviele felder wie gewürfelt wurden vorrücken oder vom start aufs erste feld        
                    if(((D_Field)figure.getField()).getFieldType() == 1){
                        figure.setField(figure.getField().getNext()[0]);
                    } else {
                        for(int i = 0; i<lastDice; i++){
                            figure.setField(figure.getField().getNext()[0]);
                        }
                    }
                    
                    abfragen ob gewonnen
                    if(((D_Field)figure.getField()).getFieldType() == 2){
                        for (Figure figure1 : previous.getPlayer().getFigures()) {
                            if (((D_Field) figure1.getField()).getFieldType() == 2) {
                                someoneWon = true;
                            } else {
                                someoneWon = false;
                                break;
                            }
                        }
                        
                        request zum beenden senden
                        if(someoneWon){
                            nextRequest = new GameEndRequest(previous.getPlayer());                    
                            requests.add(nextRequest);
                        }
                        
                            
                    } else {
                        neue würfelrequest senden 
                        nextRequest = new InteractionRequest(getNextPlayer((D_Player)previous.getPlayer())+" ist dran. Bitte wuerfeln!",
                                new String[]{"Wuerfeln"}, getNextPlayer((D_Player)previous.getPlayer()), false,INTERACTIONRESPONSE_CHOICES_DICE);                    
                        requests.add(nextRequest);
                    }
                    
                } else{
                    throw new IllegalArgumentException("Illegal InteractionResponse Type");
                }
            }
            
        } else if(as instanceof TimerResponse){
            
        } else{
            schmeiße exception
        } 
        
        /*2 richtig?? @Colbi**/
        requests.add(new GUIUpdateRequest(GUIUpdateRequest.GUIUPDATE_FIGURES));
        return requests.toArray(new ActionRequest[0]);
    }
    
    public D_Player getNextPlayer(D_Player currentPlayer){
        int currentPlayerNr = ((D_Player)currentPlayer).getPlayerNr();
        int nextPlayerNr = currentPlayerNr + 1 % getDependingModel().getPlayers().size();
        for(Player p : getDependingModel().getPlayers())
            if(((D_Player)p).getPlayerNr() == nextPlayerNr)
                return (D_Player) p;
        throw new RuntimeException();
    }
    
    @Override
    public Field getNextStartPositionForPlayer(Player player) { // DEL
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public int getMaximumPlayers() { // DEL
        return -1;
    }

    @Override
    public int getMinimumPlayers() { // DEL
        return -1;
    }  
 
}
