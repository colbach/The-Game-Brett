package thegamebrett.game.MADN;
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
 * @author KorÃ©
 */
public class MADN_GameLogic extends GameLogic {

    /* maximal und minimale Anzahl an Spielern die im Spiel mÃ¶glich sind**/
    private final int maximumPlayers = 4;
    private final int minimumPlayers = 2;
    
    /*Fall unterscheidung die angibt ob response wÃ¼rfeln oder bewegen ist**/
    public final static Integer INTERACTIONRESPONSE_CHOICES_DICE = new Integer(0);
    public final static Integer INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE = new Integer(1);
    public final static Integer INTERACTIONRESPONSE_NO_RESPONSE = new Integer(2);
    public final static Integer INTERACTIONRESPONSE_SOMEONE_WON = new Integer(3);

    
    
    private ActionRequest expected;
    private ArrayList<ActionRequest> requests;
    private static int lastDice = 0;
    private boolean someoneWon = false;
    private MADN_Board board;
    private static boolean secondChance = false;
    private int anzPlayer;

    
    public MADN_GameLogic(Model dependingModel) {
        super(dependingModel);
        this.board = null;
    }

    @Override
    public ActionRequest[] next(ActionResponse as) {
        
        requests = new ArrayList<>();
        
        /*Spielbeginn*/
        if(as instanceof StartPseudoResponse){
            requests.add(new GUIUpdateRequest(GUIUpdateRequest.GUIUPDATE_ALL));
            requests.add(gameStart(as));
        }
        
        /* abfragen ob interaction **/
        else if(as instanceof InteractionResponse){
            
            InteractionRequest previous = ((InteractionResponse) as).getConcerningInteractionRequest();
            /* abfragen ob die response eine antwort auf die erwartete request ist **/
            if(previous.equals(expected)){
                /* abfragen ob anfrage zum wÃ¼rfeln **/
                if(previous.getUserData().equals(INTERACTIONRESPONSE_CHOICES_DICE)){
                   requests.add(nextDice(as, previous));
                /* abfragen ob anfrage auswÃ¤hlen einer bestimmten figur **/
                } else if(previous.getUserData().equals(INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE)){
                    requests.add(nextChoose(as,previous));
                    
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
            //schmeiÃŸe exception
        } 
        
        requests.add(new GUIUpdateRequest(GUIUpdateRequest.GUIUPDATE_ALL));
        return requests.toArray(new ActionRequest[0]);
    }
    
    public ActionRequest nextChoose(ActionResponse as, InteractionRequest previous){
        ActionRequest nextRequest = null;
        MADN_Figure figure = (MADN_Figure)(previous.getChoices()[((InteractionResponse) as).getChoiceIndex()]);
        //soviele felder wie gewÃ¼rfelt wurden vorrÃ¼cken oder vom start aufs erste feld        
        if(((MADN_Field)figure.getField()).getFieldType() == MADN_Field.FIELD_TYPE_INIT){
            figure.setField(figure.getStartField());
        } else {
            MADN_Field field = (MADN_Field)figure.getField();
            for(int i = 0; i<lastDice; i++){
                if(field.getNext()[0]==figure.getStartField()){
                    field = field.getNext()[1];
                } else {
                    field = field.getNext()[0];
                }
            }
            if(occupiedWith(field)!=null){
                occupiedWith(field).setField(occupiedWith(field).getInitField());
            }
            
            for(int i = 0; i<lastDice; i++){
                    if(figure.getField().getNext()[0]==figure.getStartField()){
                        figure.setField(figure.getField().getNext()[1]);
                    } else {
                        figure.setField(figure.getField().getNext()[0]);
                    }
            }
        }

        //abfragen ob gewonnen
        if(((MADN_Field)figure.getField()).getFieldType() == MADN_Field.FIELD_TYPE_END){
            for (Figure figure1 : previous.getPlayer().getFigures()) {
                if (((MADN_Field) figure1.getField()).getFieldType() == MADN_Field.FIELD_TYPE_END) {
                    someoneWon = true;
                } else {
                    someoneWon = false;
                    break;
                }
            }
        }

        //request zum beenden senden
        if(someoneWon){
            InteractionRequest req;
            
            for(int i = 0; i<anzPlayer-1;i++){
                req = new InteractionRequest("Spieler "+((MADN_Player)previous.getPlayer()).getPlayerNr()+" hat gewonnen! Das Spiel ist vorbei!",
                    new String[]{"Spielende"}, (MADN_Player)getDependingModel().getPlayers().get(i), false,INTERACTIONRESPONSE_SOMEONE_WON);
                requests.add(req);
            }
            nextRequest = new InteractionRequest("Spieler "+((MADN_Player)previous.getPlayer()).getPlayerNr()+" hat gewonnen! Das Spiel ist vorbei!",
                    new String[]{"Spielende"}, (MADN_Player)getDependingModel().getPlayers().get(anzPlayer), false,INTERACTIONRESPONSE_SOMEONE_WON);
                
        
        } else {
            //neue wÃ¼rfelrequest senden 
            if(lastDice==6){
                nextRequest = new InteractionRequest("Du bist nocheinmal dran. Bitte wuerfeln!",
                    new String[]{"Wuerfeln"}, (MADN_Player)previous.getPlayer(), false,INTERACTIONRESPONSE_CHOICES_DICE);                    
                
            
            } else {
                nextRequest = new InteractionRequest("Du bist dran. Bitte wuerfeln!",
                    new String[]{"Wuerfeln"}, getNextPlayer((MADN_Player)previous.getPlayer()), false,INTERACTIONRESPONSE_CHOICES_DICE);                    
            }
            secondChance = false;
        }
        expected = nextRequest;
        return nextRequest;
    }
    
    public InteractionRequest nextDice(ActionResponse as, InteractionRequest previous){
         /* wÃ¼rfelanfrage bearbeiten **/
        int dicedNr = dice();
        InteractionRequest nextRequest;

        //MÃ¶gliche Figuren herausfinden
        MADN_Figure[] figures = movableFigures((MADN_Player)previous.getPlayer(), dicedNr);
        
        if(figures != null){
            nextRequest = new InteractionRequest("Du hast eine "+dicedNr+" gewuerfelt! Bitte eine Figur waehlen!",
                figures, (MADN_Player)previous.getPlayer(), false, INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE);
        } else if(secondChance) {
            InteractionRequest req = new InteractionRequest("Du hast eine "+lastDice+" gewuerfelt und kannst keine Figur bewegen! ",
                new String[]{"OK!"}, (MADN_Player)previous.getPlayer(), false,INTERACTIONRESPONSE_CHOICES_DICE);
            requests.add(req);
            
            nextRequest = new InteractionRequest("Du bist dran. Bitte wuerfeln!",
                new String[]{"Wuerfeln"}, getNextPlayer((MADN_Player)previous.getPlayer()), false,INTERACTIONRESPONSE_CHOICES_DICE);                    
            secondChance = false;
        } else {
            nextRequest = new InteractionRequest("Du hast eine "+lastDice+" gewuerfelt und kannst keine Figur bewegen! Wuerfle nocheinmal!",
                new String[]{"Wuerfeln"}, (MADN_Player)previous.getPlayer(), false,INTERACTIONRESPONSE_CHOICES_DICE);
            secondChance = true;
        }
        
        expected = nextRequest;
        return nextRequest;
    }
    
    public InteractionRequest gameStart(ActionResponse as){
        
        InteractionRequest nextRequest = new InteractionRequest("Du bist dran. Bitte wuerfeln!",
                new String[]{"Wuerfeln"}, getNextPlayer(null), false,INTERACTIONRESPONSE_CHOICES_DICE);
        expected = nextRequest;
        return nextRequest;
    }
    
    public MADN_Figure[] movableFigures(MADN_Player player, int dicedNr) {
        
        ArrayList<MADN_Figure> figures = new ArrayList<>();
        MADN_Field destField;
        
        
        //Steht eine Figur auf dem Startfeld und ist es die eigene?
        //Wenn ja, muss diese Figur wenn mÃ¶glich bewegt werden
        if(occupiedWith(player.getStartField())!=null){
            if(occupiedWith(player.getStartField()).getStartField() == player.getStartField()){
                destField = player.getStartField();
                for(int i = 0; i<dicedNr; i++){
                    destField = destField.getNext()[0];
                }
                if((occupiedWith(destField) == null)||(occupiedWith(destField).getStartField()!=player.getStartField())){
                    figures.add(occupiedWith(player.getStartField()));
                    return figures.toArray(new MADN_Figure[0]);
                }
            }
        }
        
        //Wenn keine eigene Figur auf dem Startfeld steht: Wurde eine 6 gewÃ¼rfelt?
        //Wenn ja und figuren auf dem initfeld stehen sind nur die als auswahl mÃ¶glich
        else if((dicedNr == 6)&&(occupiedWith(player.getStartField())==null)){
            for(MADN_Figure figure : player.getFigures()){
                if(((MADN_Field)figure.getField()).getFieldType() == MADN_Field.FIELD_TYPE_INIT){
                    figures.add(figure);
                }
            }
            if(figures.size()>0){
                return figures.toArray(new MADN_Figure[0]);
            }
        }
        
        //Sonst werden alle Figuren fÃ¼r die ein bewegen mÃ¶glich ist in das array gelegt

        for (MADN_Figure figure : player.getFigures()) {
            destField = (MADN_Field)figure.getField();

            if ((destField.getFieldType() == MADN_Field.FIELD_TYPE_START)||
                    (destField.getFieldType() == MADN_Field.FIELD_TYPE_NORMAL)) {
                if(checkField(dicedNr, figure)){
                    figures.add(figure);
                }
            }

        }
        
        
        
        if(figures.size()>0){
            return figures.toArray(new MADN_Figure[0]);
        }
        return null;
    }
    
    //schaut ob es mÃ¶glich fÃ¼r die figur ist das feld zu betreten
    public boolean checkField(int dicedNr, MADN_Figure figure){
        MADN_Field ldestField;
        if((MADN_Field)figure.getField().getNext()[0]==figure.getStartField()){
            ldestField = (MADN_Field)figure.getField().getNext()[1];
        }else{
            ldestField = (MADN_Field)figure.getField().getNext()[0];
        }
        for(int j = 0; j<dicedNr-1; j++){
                                    
            if((ldestField != null)&&(ldestField.getNext()!= null)){
                if(ldestField.getNext().length<=1){
                    ldestField = ldestField.getNext()[0];
                } else if(ldestField.getNext().length>1) {
                    if(ldestField.getNext()[0] == figure.getStartField()){
                        ldestField = ldestField.getNext()[1];
                    } else {
                        ldestField = ldestField.getNext()[0];
                    }
                }
            } else {
                return false;
            }
        }
        if(occupiedWith(ldestField)!=null){
            return (occupiedWith(ldestField).getStartField() != figure.getStartField());
        } else {
            return true;
        }
    }
    
    public MADN_Figure occupiedWith(MADN_Field field){
        for(int i = 0; i<getDependingModel().getPlayers().size(); i++){
            for(int j=0; j<4; j++){
                if(field == getDependingModel().getPlayers().get(i).getFigures()[j].getField()){
                    return (MADN_Figure)getDependingModel().getPlayers().get(i).getFigures()[j];
                }          
            }
        }
        return null;
    }
    
    public MADN_Player getNextPlayer(MADN_Player currentPlayer){
        if(currentPlayer == null){
            return (MADN_Player)getDependingModel().getPlayers().get(0);
        }
        
        int currentPlayerNr = ((MADN_Player)currentPlayer).getPlayerNr();
        for(int i = 0; i<getDependingModel().getPlayers().size(); i++){
            if(((MADN_Player)getDependingModel().getPlayers().get(i)).getPlayerNr()==(currentPlayerNr+1)%anzPlayer){
                return (MADN_Player)getDependingModel().getPlayers().get(i);
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
    
    public void setBoard(MADN_Board b){
     this.board = b;
    }
    
    public int getAnzPlayer() {
        return anzPlayer;
    }

    public void setAnzPlayer(int anzPlayer) {
        this.anzPlayer = anzPlayer;
    }
 
}
