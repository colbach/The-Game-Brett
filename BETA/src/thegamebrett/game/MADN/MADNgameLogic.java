package thegamebrett.game.MADN;
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

/**
 * @author Koré
 */
public class MADNgameLogic extends GameLogic{

    
    /* maximal und minimale Anzahl an Spielern die im Spiel möglich sind**/
    private final int maximumPlayers = 4;
    private final int minimumPlayers = 2;
    
    /*Fall unterscheidung die angibt ob response würfeln oder bewegen ist**/
    public final static Integer INTERACTIONRESPONSE_CHOICES_DICE = new Integer(0);
    public final static Integer INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE = new Integer(1);
    
    
    private ActionRequest expected;
    private int lastDice;
    private boolean someoneWon = false;
    
    public MADNgameLogic(Model dependingModel) {
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
            if(previous.equals(expected)){
                /* abfragen ob anfrage zum würfeln **/
                if(previous.getUserData().equals(INTERACTIONRESPONSE_CHOICES_DICE)){
                    /* würfelanfrage bearbeiten **/
                    int dicedNr = dice();
                    MADNfigure[] figures = movableFigures((MADNplayer)previous.getPlayer(), dicedNr);
                    nextRequest = new InteractionRequest("Sie haben eine "+dicedNr+" gewuerfelt! Bitte eine Figur waehlen!",
                            figures, (MADNplayer)previous.getPlayer(), false, INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE);
                    requests.add(nextRequest);
                    
                /* abfragen ob anfrage auswählen einer bestimmten figur **/
                } else if(previous.getUserData().equals(INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE)){
                    
                    MADNfigure figure = (MADNfigure)(previous.getChoices()[((InteractionResponse) as).getChoiceIndex()]);
                    //soviele felder wie gewürfelt wurden vorrücken oder vom start aufs erste feld        
                    if(((MADNfield)figure.getField()).getFieldType() == 1){
                        figure.setField(figure.getField().getNext()[0]);
                    } else {
                        for(int i = 0; i<lastDice; i++){
                            figure.setField(figure.getField().getNext()[0]);
                        }
                    }
                    
                    //abfragen ob gewonnen
                    if(((MADNfield)figure.getField()).getFieldType() == 2){
                        for (Figure figure1 : previous.getPlayer().getFigures()) {
                            if (((MADNfield) figure1.getField()).getFieldType() == 2) {
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
                        nextRequest = new InteractionRequest(getNextPlayer((MADNplayer)previous.getPlayer())+" ist dran. Bitte wuerfeln!",
                                new String[]{"Wuerfeln"}, getNextPlayer((MADNplayer)previous.getPlayer()), false,INTERACTIONRESPONSE_CHOICES_DICE);                    
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
    
    private MADNfigure[] movableFigures(MADNplayer player, int dicedNr) {
        
        ArrayList<MADNfigure> figures = new ArrayList<>();
        MADNfield destField;
        if(player != null){
            for (MADNfigure figure : player.getFigures()) {
                destField = (MADNfield) figure.getField();
                if ((dicedNr == 6) && (((MADNfield) figure.getField()).getFieldType() == 1)) {
                    if (occupiedWith((MADNfield)player.getFirstField())!= null) {
                        break;
                    } else {
                        figures.add(figure);
                    }
                } else {
                    if (((MADNfield) figure.getField()).getFieldType() == 1) {
                        break;
                    } else {
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
                        if (destField != null) {
                            figures.add(figure);
                        }
                    }
                }
            }
        }
        
        return figures.toArray(new MADNfigure[0]);
    }
    
    public MADNfigure occupiedWith(MADNfield field){
        for(int i = 0; i<getDependingModel().getPlayers().size(); i++){
            for(int j=0; j<4; i++){
                if(getDependingModel().getPlayers().get(i).getFigures()[j].getField() == field){
                    return (MADNfigure)getDependingModel().getPlayers().get(i).getFigures()[j];
                }          
            }
        }
        return null;
    }
    
    public MADNplayer getNextPlayer(MADNplayer currentPlayer){
        int currentPlayerNr = ((MADNplayer)currentPlayer).getPlayerNr();
        for(int i = 0; i<getDependingModel().getPlayers().size(); i++){
            if(((MADNplayer)getDependingModel().getPlayers().get(i)).getPlayerNr()==(currentPlayerNr+1)%4){
                return (MADNplayer)getDependingModel().getPlayers().get(i);
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
    
    public void setExpected(InteractionRequest quest){
        this.expected = quest;
    }
 
}
