/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.PSS;

import java.util.ArrayList;
import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.action.request.GUIUpdateRequest;
import thegamebrett.action.request.GameEndRequest;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.action.response.*;
import thegamebrett.model.GameLogic;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Field;
import thegamebrett.model.elements.Figure;
/**
 *
 * @author Korè
 */
public class PSS_GameLogic extends GameLogic{
    
    
    private static String[] fieldContent = {
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
    };
    
    
    /* maximal und minimale Anzahl an Spielern die im Spiel mÃ¶glich sind**/
    private final int maximumPlayers = 8;
    private final int minimumPlayers = 1;
    
    /*Fall unterscheidung die angibt ob response wÃ¼rfeln oder bewegen ist**/
    public final static Integer INTERACTIONRESPONSE_CHOICES_DICE = new Integer(0);
    public final static Integer INTERACTIONRESPONSE_CHOICES_OK = new Integer(1);
    public final static Integer INTERACTIONRESPONSE_NO_RESPONSE = new Integer(2);
    public final static Integer INTERACTIONRESPONSE_SOMEONE_WON = new Integer(3);

    
    
    private ActionRequest expected;
    private ArrayList<ActionRequest> requests;
    private static int lastDice = 0;
    private boolean someoneWon = false;
    private PSS_Board board;
    private static boolean diceAgain = false;
    private int anzPlayer;

    
    public PSS_GameLogic(Model dependingModel) {
        super(dependingModel);
        this.board = null;
    }
    

    @Override
    public ActionRequest[] next(ActionResponse as) {
        requests = new ArrayList<>();
        
        if(as instanceof StartPseudoResponse){
            requests.add(new GUIUpdateRequest(GUIUpdateRequest.GUIUPDATE_ALL));
            requests.add(gameStart(as));
        }
        
        else if(as instanceof InteractionResponse){
            
            InteractionRequest previous = ((InteractionResponse) as).getConcerningInteractionRequest();
            /* abfragen ob die response eine antwort auf die erwartete request ist **/
            if(previous.equals(expected)){
                /* abfragen ob anfrage zum wuerfeln **/
                if(previous.getUserData().equals(INTERACTIONRESPONSE_CHOICES_DICE)){
                   requests.add(nextDice(as, previous));
                /* abfragen ob anfrage auswÃ¤hlen einer bestimmten figur **/
                } else if(previous.getUserData().equals(INTERACTIONRESPONSE_CHOICES_OK)){
                    requests.add(nextOK(as,previous));
                    
                }else if(previous.getUserData().equals(INTERACTIONRESPONSE_SOMEONE_WON)){
                    requests.add(new GameEndRequest(new Player[] { previous.getPlayer() }));                    
                }
                else{
                    throw new IllegalArgumentException("Illegal InteractionResponse Type");
                }
            }
            
        } else if(as instanceof TimerResponse){
            //nicht implementiert
        } else{
            throw new IllegalArgumentException("Illegal Response Type");
        } 
        
        
        requests.add(new GUIUpdateRequest(GUIUpdateRequest.GUIUPDATE_ALL));
        return requests.toArray(new ActionRequest[0]);
    }

    private ActionRequest gameStart(ActionResponse as) {

        InteractionRequest nextRequest = new InteractionRequest("Und los gehts! Du bist dran mit wuerfeln!",
                new String[]{"WUHU!"}, getNextPlayer(null), false,INTERACTIONRESPONSE_CHOICES_DICE);
        expected = nextRequest;
        return nextRequest;
        
    }
    
    private ActionRequest nextDice(ActionResponse as, InteractionRequest previous) {
        dice();
        InteractionRequest nextRequest;
        
        PSS_Figure figure = ((PSS_Player)previous.getPlayer()).getFigure();
        
        PSS_Field field = (PSS_Field)figure.getField();
        boolean toFar = false;
        for(int i = 0; i<lastDice; i++){
            if(field.getNext() != null)
                field = field.getSingleNext();
            else
                toFar = true;
        }
        
        if(!toFar){
            for(int i = 0; i<lastDice; i++){
                figure.setField(((PSS_Field)figure.getField()).getSingleNext());
            }
            
            if(figure.getField().getNext() == null){
                InteractionRequest req;
            
                for(int i = 0; i<anzPlayer-1;i++){
                    req = new InteractionRequest(((PSS_Player)previous.getPlayer()).getPlayerName()+" hat gewonnen! Das Spiel ist vorbei!",
                        new String[]{"Meh"}, (PSS_Player)getDependingModel().getPlayers().get(i), false,INTERACTIONRESPONSE_SOMEONE_WON);
                    requests.add(req);
                }
                nextRequest = new InteractionRequest(((PSS_Player)previous.getPlayer()).getPlayerName()+" hat gewonnen! Das Spiel ist vorbei!",
                        new String[]{"Meh"}, (PSS_Player)getDependingModel().getPlayers().get(anzPlayer), false,INTERACTIONRESPONSE_SOMEONE_WON);

            }else{
                String quest = figure.getField().getLayout().getTitle();
            
                nextRequest = new InteractionRequest("Du hast eine "+lastDice+" gewuerfelt! "
                    + "Deine Aufgabe: " + quest,
                    new String[]{"OK!"}, (PSS_Player)previous.getPlayer(), false, INTERACTIONRESPONSE_CHOICES_OK);
            
            }
            
        }else{
            InteractionRequest req = new InteractionRequest("Schon so nah dran! Aber du hast eine "+lastDice+" gewuerfelt und kannst dich nicht bewegen!",
                new String[]{"Verdammt!"}, (PSS_Player)previous.getPlayer(), false,INTERACTIONRESPONSE_NO_RESPONSE);
            requests.add(req);
            nextRequest = new InteractionRequest("Du bist dran mit wuerfeln!",
                    new String[]{"GOGOGO"}, getNextPlayer((PSS_Player)previous.getPlayer()), false,INTERACTIONRESPONSE_CHOICES_DICE);                    

        }
                
        return nextRequest;
    }

    private ActionRequest nextOK(ActionResponse as, InteractionRequest previous) {

        InteractionRequest nextRequest;
        PSS_Figure figure = ((PSS_Player)previous.getPlayer()).getFigure();
        PSS_Field field = (PSS_Field)figure.getField();
        int questIndex = field.getFieldIndex();
        
        switch(questIndex){
        
            case 3: 
                field = (PSS_Field)board.getField(questIndex-2);
                ((PSS_Player)previous.getPlayer()).getFigure().setField(field);
                nextRequest = getOkRequest(as,previous, questIndex-2, field.getLayout().getTitle());
                break;
            case 5: case 66:
                field = (PSS_Field)board.getField(32);
                ((PSS_Player)previous.getPlayer()).getFigure().setField(field);
                nextRequest = getOkRequest(as,previous, 32, field.getLayout().getTitle());
                break;
            case 13: case 21: case 70:
                nextRequest = new InteractionRequest("Du bist wieder auf START!",
                    new String[]{"Der Hammer!"}, (PSS_Player)previous.getPlayer(), false, INTERACTIONRESPONSE_NO_RESPONSE);
                requests.add(new InteractionRequest("Du bist dran mit wuerfeln!",
                    new String[]{"GOGOGO"}, getNextPlayer((PSS_Player)previous.getPlayer()), false,INTERACTIONRESPONSE_CHOICES_DICE));                    
                break;
            case 36: 
                int lastIndex = 71;
                PSS_Player leastPlayer = null;
                for(int i=0;i<anzPlayer; i++){
                    int index=((PSS_Field)(((PSS_Player)(getDependingModel().getPlayers().get(i))).getFigure().getField())).getFieldIndex();
                    if(index<lastIndex){
                        leastPlayer = (PSS_Player)(getDependingModel().getPlayers().get(i));
                        lastIndex = index;
                    }
                }
                nextRequest = new InteractionRequest("Du bist wieder auf START!",
                    new String[]{"Der Hammer!"}, (PSS_Player)previous.getPlayer(), false, INTERACTIONRESPONSE_NO_RESPONSE);
                requests.add(new InteractionRequest("Du bist dran mit wuerfeln!",
                    new String[]{"GOGOGO"}, getNextPlayer((PSS_Player)previous.getPlayer()), false,INTERACTIONRESPONSE_CHOICES_DICE));                    
                break;
                
            default:
                break;

        }
        

        return null;
    }
    
    
    private InteractionRequest getOkRequest(ActionResponse as, InteractionRequest previous, int fieldIndex, String quest) {
        return new InteractionRequest("Du bist auf das Feld "+fieldIndex+" gekommen! "
                    + "Deine Aufgabe: " + quest,
                    new String[]{"Na gut..."}, (PSS_Player)previous.getPlayer(), false, INTERACTIONRESPONSE_CHOICES_OK);
    }

    
    private Player getNextPlayer(PSS_Player currentPlayer) {
        if(currentPlayer == null){
            return (PSS_Player)getDependingModel().getPlayers().get(0);
        }
        
        int currentPlayerNr = ((PSS_Player)currentPlayer).getPlayerNr();
        for(int i = 0; i<getDependingModel().getPlayers().size(); i++){
            if(((PSS_Player)getDependingModel().getPlayers().get(i)).getPlayerNr()==(currentPlayerNr+1)%anzPlayer){
                return (PSS_Player)getDependingModel().getPlayers().get(i);
            }
        }
        throw new IllegalArgumentException("Not a legal player.");
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
    
    public void setBoard(PSS_Board b){
     this.board = b;
    }
    
    public int getAnzPlayer() {
        return anzPlayer;
    }

    public void setAnzPlayer(int anzPlayer) {
        this.anzPlayer = anzPlayer;
    }
 
    @Override
    public Field getNextStartPositionForPlayer(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
