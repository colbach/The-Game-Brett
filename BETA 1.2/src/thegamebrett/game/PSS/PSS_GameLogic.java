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
/**
 *
 * @author Kore
 */
public class PSS_GameLogic extends GameLogic{
    
    
    private final static String[] FIELD_CONTENT = {
        "START",
        "Alle duerfen trinken!",
        "Du und deine beiden Nebensitzer trinken.",
        "Gehe 2 Felder zurueck.",
        "Stimme ein Lied an. Wer nicht mitsingt trinkt 2.",
        "Trinke und gehe auf Feld 32.",
        "Endlich kannst du wieder trinken!",
        "Alle ausser dir trinken.",
        "8",
        "Bestimme eine Person die trinken soll.",
        "Alle Typen trinken.",
        "Dein rechter Sitznachbar trinkt.",
        "Eine Person deiner Wahl darf 2 trinken.",
        "Trinke so viel wie du gewuerfelt hast und gehe auf START.",
        "Es trinken die Personen die am nächsten dem START und ZIEL sind.",
        "Das Maedchen deiner Wahl darf trinken.",
        "Du trinkst und die erste Person die lacht trinkt 2.",
        "Trinke und setze eine Runde aus.",
        "18",
        "Alle Maedels trinken.",
        "Trinke und wuerfle noch einmal!",
        "Gehe zurueck auf START.",
        "Trinke so viel wie du gewuerfelt hast.",
        "Alle die einen Bruder haben duerfen trinken!",
        "Trinke 3 oder gehe 4 Felder zurueck.",
        "Endlich kannst du wieder trinken!",
        "Es trinken die, die ein gerades Alter haben.",
        "Es trinken die, die ein ungerades Alter haben.",
        "28",
        "Gehe auf Feld 9 und trinke 5.",
        "Jeder, der keine 10 Euro an sich hat, trinkt.",
        "Alle Studenten trinken.",
        "Trinke 3 und kuesse eine Person des anderen Geschlechts.",
        "Trinke und ziehe ein Kleidungsstueck aus!",
        "Trinke und bestimme eine Person die auf Feld 6 zurueck geht.",
        "Gehe auf Feld 6 zurueck.",
        "Es trinkt der, der dem START am naechsten ist und du gehst zurueck auf Start.",
        "Alle die vor dir sind duerfen trinken.",
        "38",
        "Trinke und ziehe ein Kleidungsstueck aus!",
        "Trinke so viel wie du gewuerfelt hast.",
        "Es trinkt die Person, die am wenigsten Muenzen bei sich traegt.",
        "Alle wuerfeln. Alle mit einer 1 duerfen trinken.",
        "Alle die eine Schwester haben trinken.",
        "Trinke und dein linker Sitznachbar entscheidet wer noch trinken darf.",
        "Gehe 2 Felder vor und trinke.",
        "Wirf eine Muenze. Bei Kopf trinken alle, bei Zahl nur du.",
        "Kuesse jemanden nach deiner Wahl und trinke 2!",
        "48",
        "Gehe auf das Feld 28 und wuerfle noch einmal.",
        "Trinke und ziehe ein Kleidungsstueck aus!",
        "Bestimme zwei Personen die sich kuessen sollen und trinke 3.",
        "Alle unter 25 duerfen trinken!",
        "Deine beiden Sitznachbarn trinken.",
        "Alle duerfen trinken!",
        "Alle die eine 6 wuerfeln trinken.",
        "Trinke und gehe auf Feld 18 zurueck.",
        "Alle die in den 90ern geboren sind trinken.",
        "58",
        "Alle wuerfeln. Alle mit einer 1 duerfen trinken.",
        "Stimme ein Lied an. Wer nicht mitsingt trinkt 5.",
        "Alle trinken die weder Brille noch Kontaktlinsen tragen.",
        "Alle die keinen BH tragen trinken.",
        "Jeder, der eine Hose anhat darf trinken!",
        "Alle trinken!",
        "Trinke und ziehe ein Kleidungsstueck aus!",
        "Gehe zurueck auf Feld 32.",
        "Alle trinken 3 und ziehen ein Kleidungsstueck aus!",
        "68",
        "Gehe zurueck auf Feld 54. und trinke 4.",
        "Trinke und gehe zurueck auf START.",
        "ZIEL"
    };
    
    
    
    
    /*Fall unterscheidung die angibt ob response wÃ¼rfeln oder bewegen ist**/
    public final static Integer INTERACTIONRESPONSE_CHOICES_DICE = new Integer(0);
    public final static Integer INTERACTIONRESPONSE_CHOICES_OK = new Integer(1);
    public final static Integer INTERACTIONRESPONSE_NO_RESPONSE = new Integer(2);
    public final static Integer INTERACTIONRESPONSE_SOMEONE_WON = new Integer(3);
    public final static Integer INTERACTIONRESPONSE_EVERYONE_DICES = new Integer(4);


    
    
    private ActionRequest expected;
    private ArrayList<ActionRequest> requests;
    private static int lastDice = 0;
    private boolean someoneWon = false;
    private PSS_Board board;
    private static boolean diceAgain = false;
    private int anzPlayer;
    
    private static int alreadyDiced = 0;
    private static ArrayList<PSS_Player> dicedRight = new ArrayList<>();
    private static boolean field55 = false;

    
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
            
            if(previous.getUserData().equals(INTERACTIONRESPONSE_EVERYONE_DICES)){
                
                dice();
                if((field55&&(lastDice==6))||(lastDice==1)){
                   dicedRight.add((PSS_Player)(previous.getPlayer()));
                }
                
                if(alreadyDiced>=anzPlayer-1){
                    for(PSS_Player p : dicedRight){
                        requests.add(new InteractionRequest("Du darfst trinken!", new String[]{"WUHU!"}, 
                                p, false,INTERACTIONRESPONSE_NO_RESPONSE));
                    }
                    dicedRight = new ArrayList<>();
                    alreadyDiced = 0;
                } else {
                    alreadyDiced++;
                }
                
                if(previous.equals(expected)){
                    InteractionRequest nextRequest = new InteractionRequest("Du bist dran mit wuerfeln!",
                    new String[]{"GOGOGO"}, getNextPlayer((PSS_Player)previous.getPlayer()), 
                            false,INTERACTIONRESPONSE_CHOICES_DICE);
                    requests.add(nextRequest);   
                    expected = nextRequest;

                }
            }
            
            else if(previous.equals(expected)){
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

        InteractionRequest nextRequest = null;
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
                
            case 13: case 21: case 36: case 70:
                field = (PSS_Field)board.getField(0);
                ((PSS_Player)previous.getPlayer()).getFigure().setField(field);
                requests.add(new InteractionRequest("Du bist wieder auf START!",
                    new String[]{"Der Hammer!"}, (PSS_Player)previous.getPlayer(), 
                        false, INTERACTIONRESPONSE_NO_RESPONSE));
                nextRequest = new InteractionRequest("Du bist dran mit wuerfeln!",
                    new String[]{"GOGOGO"}, getNextPlayer((PSS_Player)previous.getPlayer()),
                        false,INTERACTIONRESPONSE_CHOICES_DICE);                    
                break;
                
            case 24: 
                if(previous.getChoices().length>1){
                    if(((InteractionResponse)as).getChoice().equals("PROST!")){
                        nextRequest = new InteractionRequest("Du bist dran mit wuerfeln!",
                        new String[]{"GOGOGO"}, getNextPlayer((PSS_Player)previous.getPlayer()), false,INTERACTIONRESPONSE_CHOICES_DICE);                    
                        break;
                    }else{
                        field = (PSS_Field)board.getField(20);
                        ((PSS_Player)previous.getPlayer()).getFigure().setField(field);
                        nextRequest = getOkRequest(as, previous, 20, field.getLayout().getTitle());
                        break;
                    }
                } else {
                    nextRequest = new InteractionRequest("Willst du trinken oder zurueck?!",
                    new String[]{"PROST!","Lieber zurueck..."}, (PSS_Player)previous.getPlayer(), false,INTERACTIONRESPONSE_CHOICES_OK);                    
                    break;
                }
                
            case 29:
                field = (PSS_Field)board.getField(9);
                ((PSS_Player)previous.getPlayer()).getFigure().setField(field);
                nextRequest = getOkRequest(as,previous, 9, field.getLayout().getTitle());
                break;
                
            case 35:
                field = (PSS_Field)board.getField(6);
                ((PSS_Player)previous.getPlayer()).getFigure().setField(field);
                nextRequest = getOkRequest(as,previous, 6, field.getLayout().getTitle());
                break;
                
            case 49:
                field = (PSS_Field)board.getField(28);
                ((PSS_Player)previous.getPlayer()).getFigure().setField(field);
                nextRequest = new InteractionRequest("Du bist noch einmal dran mit wuerfeln!",
                        new String[]{"DER HAMMER!"}, (PSS_Player)previous.getPlayer(), false,INTERACTIONRESPONSE_CHOICES_DICE);                    
                break;
            
            case 56:
                field = (PSS_Field)board.getField(18);
                ((PSS_Player)previous.getPlayer()).getFigure().setField(field);
                nextRequest = getOkRequest(as,previous, 18, null);
                break;
                
            case 45:
                field = (PSS_Field)board.getField(47);
                ((PSS_Player)previous.getPlayer()).getFigure().setField(field);
                nextRequest = getOkRequest(as,previous, 47, field.getLayout().getTitle());
                break;
              
            case 34:
                if(previous.getChoices().length>1){
                    for(int i = 0; i<anzPlayer;i++){
                        if(((PSS_Player)getDependingModel().getPlayers().get(i)).getPlayerName().equals(((InteractionResponse)as).getChoice())){
                            ((PSS_Player)getDependingModel().getPlayers().get(i)).getFigure().setField(board.getField(6));
                            requests.add(new InteractionRequest("Du bist auf das Feld 6 gekommen! "
                                + "Deine Aufgabe: " + board.getField(6).getLayout().getTitle(),
                                new String[]{"Na gut..."}, (PSS_Player)previous.getPlayer(), 
                                false, INTERACTIONRESPONSE_NO_RESPONSE));
                            break;
                        }
                    }
                    nextRequest = new InteractionRequest("Du bist dran mit wuerfeln!",
                        new String[]{"GOGOGO"}, getNextPlayer((PSS_Player)previous.getPlayer()), 
                        false,INTERACTIONRESPONSE_CHOICES_DICE);
                } else {
                    String[] s = new String[anzPlayer];
                    for(int i = 0; i<anzPlayer;i++){
                        s[i] = ((PSS_Player)getDependingModel().getPlayers().get(i)).getPlayerName();
                    }
                    nextRequest = new InteractionRequest("Du bist auf das Feld 34 gekommen! "
                    + "Deine Aufgabe: " + board.getField(34).getLayout().getTitle(),
                    s, (PSS_Player)previous.getPlayer(), false, INTERACTIONRESPONSE_CHOICES_OK);
                }
                break;                
                
            case 69:
                field = (PSS_Field)board.getField(54);
                ((PSS_Player)previous.getPlayer()).getFigure().setField(field);
                nextRequest = getOkRequest(as,previous, 54, field.getLayout().getTitle());
                break;
                
                
            case 17:
                
                ((PSS_Player)previous.getPlayer()).setSuspended(true);
                requests.add(new InteractionRequest("Du musst eine Runde aussetzen!",
                        new String[]{"-.-"}, (PSS_Player)previous.getPlayer(), 
                        false,INTERACTIONRESPONSE_NO_RESPONSE));
                nextRequest = new InteractionRequest("Du bist dran mit wuerfeln!",
                        new String[]{"GOGOGO"}, getNextPlayer((PSS_Player)previous.getPlayer()), 
                        false,INTERACTIONRESPONSE_CHOICES_DICE);                    
                break;       
                
            case 20:
                nextRequest = new InteractionRequest("Du bist noch einmal dran mit wuerfeln!",
                        new String[]{"DER HAMMER!"}, (PSS_Player)previous.getPlayer(), false,INTERACTIONRESPONSE_CHOICES_DICE);                    
                break;
                
            case 55:
                field55 = true;
                for(Player p : getDependingModel().getPlayers()){
                    requests.add(new InteractionRequest("Wuerfle eine 6!",
                        new String[]{"Wuerfeln!"}, (PSS_Player)p, 
                            false,INTERACTIONRESPONSE_EVERYONE_DICES));                    
                }
                break;
                
            case 59: case 42:
                field55 = false;
                for(Player p : getDependingModel().getPlayers()){
                    requests.add(new InteractionRequest("Wuerfle eine 1!",
                        new String[]{"Wuerfeln!"}, (PSS_Player)p, 
                            false,INTERACTIONRESPONSE_EVERYONE_DICES));                    
                }
                break;                
            
            default:
                nextRequest = new InteractionRequest("Du bist dran mit wuerfeln!",
                        new String[]{"GOGOGO"}, getNextPlayer((PSS_Player)previous.getPlayer()), 
                        false,INTERACTIONRESPONSE_CHOICES_DICE);
                break;

        }
        
        expected = nextRequest;
        return nextRequest;
    }
    
    
    private InteractionRequest getOkRequest(ActionResponse as, InteractionRequest previous, int fieldIndex, String quest) {
        
        if(quest != null){
            return new InteractionRequest("Du bist auf das Feld "+fieldIndex+" gekommen! "
                    + "Deine Aufgabe: " + quest,
                    new String[]{"Na gut..."}, (PSS_Player)previous.getPlayer(), false, INTERACTIONRESPONSE_CHOICES_OK);
        } else {
            return new InteractionRequest("Du bist auf das Feld "+fieldIndex+" gekommen!",
                    new String[]{"Klasse!"}, (PSS_Player)previous.getPlayer(), false, INTERACTIONRESPONSE_CHOICES_OK);
        }
    }

    
    private Player getNextPlayer(PSS_Player currentPlayer) {
        if(currentPlayer == null){
            return (PSS_Player)getDependingModel().getPlayers().get(0);
        }
        
        int currentPlayerNr = ((PSS_Player)currentPlayer).getPlayerNr();
        for(int i = 0; i<getDependingModel().getPlayers().size(); i++){
            if(((PSS_Player)getDependingModel().getPlayers().get(i)).getPlayerNr()==(currentPlayerNr+1)%anzPlayer){
                if(((PSS_Player)getDependingModel().getPlayers().get(i)).isSuspended()){
                    ((PSS_Player)getDependingModel().getPlayers().get(i)).setSuspended(false);
                    return getNextPlayer((PSS_Player)getDependingModel().getPlayers().get(i));
                }
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
    
    public String[] getFieldContent(){
        return FIELD_CONTENT;
    }

}
