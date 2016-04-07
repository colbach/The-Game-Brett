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
 * @author Koré
 */
public class MADN_GameLogic extends GameLogic {

    /* maximal und minimale Anzahl an Spielern die im Spiel möglich sind**/
    private final int maximumPlayers = 4;
    private final int minimumPlayers = 2;
    
    /*Fall unterscheidung die angibt ob response würfeln oder bewegen ist**/
    public final static Integer INTERACTIONRESPONSE_CHOICES_DICE = new Integer(0);
    public final static Integer INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE = new Integer(1);
    
    
    private ActionRequest expected;
    private ArrayList<ActionRequest> requests;
    private static int lastDice = 0;
    private boolean someoneWon = false;
    private MADN_Board board;
    private static boolean secondChance = false;
    
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
                /* abfragen ob anfrage zum würfeln **/
                if(previous.getUserData().equals(INTERACTIONRESPONSE_CHOICES_DICE)){
                   requests.add(nextDice(as, previous));
                /* abfragen ob anfrage auswählen einer bestimmten figur **/
                } else if(previous.getUserData().equals(INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE)){
                    requests.add(nextChoose(as,previous));
                    
                } else{
                    throw new IllegalArgumentException("Illegal InteractionResponse Type");
                }
            }
            
        } else if(as instanceof TimerResponse){
            //noch nicht implementiert
        } else{
            //schmeiße exception
        } 
        
        requests.add(new GUIUpdateRequest(GUIUpdateRequest.GUIUPDATE_ALL));
        return requests.toArray(new ActionRequest[0]);
    }
    
    public ActionRequest nextChoose(ActionResponse as, InteractionRequest previous){
        ActionRequest nextRequest;
        MADN_Figure figure = (MADN_Figure)(previous.getChoices()[((InteractionResponse) as).getChoiceIndex()]);
        //soviele felder wie gewürfelt wurden vorrücken oder vom start aufs erste feld        
        if(((MADN_Field)figure.getField()).getFieldType() == MADN_Field.FIELD_TYPE_INIT){
            figure.setField(figure.getStartField());
        } else {
            MADN_Field field = (MADN_Field)figure.getField();
//            if((occupiedWith(field)!=null)&&(occupiedWith(field).getStartField()!=figure.getStartField())){
                for(int i = 0; i<lastDice; i++){
                    field = field.getNext()[0];
                }
                if(occupiedWith(field)!=null)
                    occupiedWith(field).setField(occupiedWith(field).getInitField());
//            }
            for(int i = 0; i<lastDice; i++){
                if(figure.getField().getNext().length>1){
                    if(figure.getField().getNext()[0]==figure.getStartField()){
                        figure.setField(figure.getField().getNext()[1]);
                    } else {
                        figure.setField(figure.getField().getNext()[0]);
                    }
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
            nextRequest = new GameEndRequest(new Player[] { previous.getPlayer() });                    
        } else {
            //neue würfelrequest senden 
            if(lastDice==6){
                nextRequest = new InteractionRequest("Spieler "+((MADN_Player)previous.getPlayer()).getPlayerNr()+" ist nocheinmal dran. Bitte wuerfeln!",
                    new String[]{"Wuerfeln"}, (MADN_Player)previous.getPlayer(), false,INTERACTIONRESPONSE_CHOICES_DICE);                    
            } else {
                nextRequest = new InteractionRequest("Spieler "+getNextPlayer((MADN_Player)previous.getPlayer()).getPlayerNr()+" ist dran. Bitte wuerfeln!",
                    new String[]{"Wuerfeln"}, getNextPlayer((MADN_Player)previous.getPlayer()), false,INTERACTIONRESPONSE_CHOICES_DICE);                    
            }
            secondChance = false;
        }
        expected = nextRequest;
        return nextRequest;
    }
    
    public InteractionRequest nextDice(ActionResponse as, InteractionRequest previous){
         /* würfelanfrage bearbeiten **/
        int dicedNr = dice();
        InteractionRequest nextRequest;

        //Mögliche Figuren herausfinden
        MADN_Figure[] figures = movableFigures((MADN_Player)previous.getPlayer(), dicedNr);
        
        if(figures!=null){
            for(MADN_Figure f : figures){
                System.err.println(f.toString());
            }
        }
        
        
        if(figures != null){
            nextRequest = new InteractionRequest("Du hast eine "+dicedNr+" gewuerfelt! Bitte eine Figur waehlen!",
                figures, (MADN_Player)previous.getPlayer(), false, INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE);
        } else if(secondChance) {
            nextRequest = new InteractionRequest("Du kannst keine Figur bewegen! "+"Spieler "+getNextPlayer((MADN_Player)previous.getPlayer()).getPlayerNr()+" ist dran. Bitte wuerfeln!",
                new String[]{"Wuerfeln"}, getNextPlayer((MADN_Player)previous.getPlayer()), false,INTERACTIONRESPONSE_CHOICES_DICE);                    
            secondChance = false;
        } else {
            nextRequest = new InteractionRequest("Du kannst keine Figur bewegen! Würfle nocheinmal!",
                new String[]{"Wuerfeln"}, (MADN_Player)previous.getPlayer(), false,INTERACTIONRESPONSE_CHOICES_DICE);
            secondChance = true;
        }
        
        expected = nextRequest;
        return nextRequest;
    }
    
    public InteractionRequest gameStart(ActionResponse as){
        
        InteractionRequest nextRequest = new InteractionRequest("Player "+getNextPlayer(null).getPlayerNr()+" ist dran. Bitte wuerfeln!",
                new String[]{"Wuerfeln"}, getNextPlayer(null), false,INTERACTIONRESPONSE_CHOICES_DICE);
        expected = nextRequest;
        return nextRequest;
    }
    
    public MADN_Figure[] movableFigures(MADN_Player player, int dicedNr) {
        
        ArrayList<MADN_Figure> figures = new ArrayList<>();
        MADN_Field destField;
        
        //Steht eine Figur auf dem Startfeld und ist es die eigene?
        //Wenn ja, muss diese Figur wenn möglich bewegt werden
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
        
        //Wenn keine eigene Figur auf dem Startfeld steht: Wurde eine 6 gewürfelt?
        //Wenn ja und figuren auf dem initfeld stehen sind nur die als auswahl möglich
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
        
        //Sonst werden alle Figuren für die ein bewegen möglich ist in das array gelegt
        else{
            for (MADN_Figure figure : player.getFigures()) {
                destField = (MADN_Field)figure.getField();

                if ((destField.getFieldType() == MADN_Field.FIELD_TYPE_START)||
                        (destField.getFieldType() == MADN_Field.FIELD_TYPE_NORMAL)) {
                    if(checkField(dicedNr, figure)){
                        figures.add(figure);
                    }
                }

            }
        }
        
        
        if(figures.size()>0){
            return figures.toArray(new MADN_Figure[0]);
        }
        return null;
    }
    
    //schaut ob es möglich für die figur ist das feld zu betreten
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
            return false;
        }
    }
    
    public MADN_Figure occupiedWith(MADN_Field field){
        for(int i = 0; i<getDependingModel().getPlayers().size(); i++){
            for(int j=0; j<4; j++){
                if(field == getDependingModel().getPlayers().get(i).getFigures()[j].getField()){
                    System.err.println("feld ist belegt mit "+getDependingModel().getPlayers().get(i).getFigures()[j].toString()+i);
                    return (MADN_Figure)getDependingModel().getPlayers().get(i).getFigures()[j];
                }          
            }
        }
        System.err.println("zielfeld ist leer");
        return null;
    }
    
    public MADN_Player getNextPlayer(MADN_Player currentPlayer){
        if(currentPlayer == null){
            return (MADN_Player)getDependingModel().getPlayers().get(0);
        }
        
        int currentPlayerNr = ((MADN_Player)currentPlayer).getPlayerNr();
        for(int i = 0; i<getDependingModel().getPlayers().size(); i++){
            if(((MADN_Player)getDependingModel().getPlayers().get(i)).getPlayerNr()==(currentPlayerNr+1)%4){
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
 
}
