package thegamebrett.game.dummy;
import thegamebrett.game.dummy.*;
import java.util.ArrayList;
import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.action.request.GUIUpdateRequest;
import thegamebrett.action.request.GameEndRequest;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.action.response.InteractionResponse;
import thegamebrett.action.response.TimerResponse;
import thegamebrett.model.GameLogic;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Field;
import thegamebrett.model.elements.Figure;

public class D_GameLogic extends GameLogic{

    /* maximal und minimale Anzahl an Spielern die im Spiel möglich sind**/
    public static final int maximumPlayers = 4;
    public static final int minimumPlayers = 2;
    
    /*Fall unterscheidung die angibt ob response würfeln oder bewegen ist**/
    public final static Integer INTERACTIONRESPONSE_CHOICES_DICE = new Integer(0);
    public final static Integer INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE = new Integer(1);
    
    
    private ActionRequest expected;
    private int lastDice;
    private boolean someoneWon = false;
    
    public D_GameLogic(Model dependingModel) {
        super(dependingModel);
    }
    
    

    @Override
    public ActionRequest[] next(ActionResponse as) {
        
        ArrayList<ActionRequest> requests = new ArrayList<>();
        ActionRequest nextRequest;

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
                    //soviele felder wie gewürfelt wurden vorrücken oder vom start aufs erste feld        
                    if(((D_Field)figure.getField()).getFieldType() == 1){
                        figure.setField(figure.getField().getNext()[0]);
                    } else {
                        for(int i = 0; i<lastDice; i++){
                            figure.setField(figure.getField().getNext()[0]);
                        }
                    }
                    
                    //abfragen ob gewonnen
                    if(((D_Field)figure.getField()).getFieldType() == 2){
                        for (Figure figure1 : previous.getPlayer().getFigures()) {
                            if (((D_Field) figure1.getField()).getFieldType() == 2) {
                                someoneWon = true;
                            } else {
                                someoneWon = false;
                                break;
                            }
                        }
                        
                        //request zum beenden senden
                        if(someoneWon){
                            nextRequest = new GameEndRequest(previous.getPlayer());                    
                            requests.add(nextRequest);
                        }
                        
                            
                    } else {
                        //neue würfelrequest senden 
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
            //schmeiße exception
        } 
        
        /*2 richtig?? @Colbi**/
        requests.add(new GUIUpdateRequest(GUIUpdateRequest.GUIUPDATE_FIGURES));
        return requests.toArray(new ActionRequest[0]);
    }
    
    private D_Figure[] movableFigures(D_Player player, int dicedNr) {
        
        ArrayList<D_Figure> figures = new ArrayList<>();
        D_Field destField;
        for(int i = 0; i<player.getFigures().length; i++){
            destField = (D_Field)player.getFigures()[i].getField();
            if((dicedNr == 6)&&(((D_Field)player.getFigures()[i].getField()).getFieldType()==1)){
                if(occupiedWith((D_Field)player.getFirstField())!= null){
                    break;
                } else {
                    figures.add(player.getFigures()[i]);
                }
            } else{
                if(((D_Field)player.getFigures()[i].getField()).getFieldType()==1){
                    break;
                }else{
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
        }
        
        return figures.toArray(new D_Figure[0]);
    }
    
    public D_Figure occupiedWith(D_Field field){
        for(int i = 0; i<getDependingModel().getPlayers().size(); i++){
            for(int j=0; j<4; i++){
                if(getDependingModel().getPlayers().get(i).getFigures()[j].getField() == field){
                    return (D_Figure)getDependingModel().getPlayers().get(i).getFigures()[j];
                }          
            }
        }
        return null;
    }
    
    public D_Player getNextPlayer(D_Player currentPlayer){
        int currentPlayerNr = ((D_Player)currentPlayer).getPlayerNr();
        for(int i = 0; i<getDependingModel().getPlayers().size(); i++){
            if(((D_Player)getDependingModel().getPlayers().get(i)).getPlayerNr()==(currentPlayerNr+1)%4){
                return (D_Player)getDependingModel().getPlayers().get(i);
            }
        }
        throw new IllegalArgumentException("kein weiterer Player");
    }
    
    @Override
    public Field getNextStartPositionForPlayer(Player player) {
        //was ist der sinn hiervon? Wenn ihr die Figuren meint muss das ein array sein, da es davon 4 gibt
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int dice(){
        //random nr zwischen 1 und 6
        int random = (int)(Math.random()*6)+1;
        lastDice = random;
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
