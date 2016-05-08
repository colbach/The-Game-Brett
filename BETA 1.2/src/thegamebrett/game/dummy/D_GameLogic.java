package thegamebrett.game.dummy;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.action.request.GUIUpdateRequest;
import thegamebrett.action.request.GameEndRequest;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.action.request.PlaySoundRequest;
import thegamebrett.action.response.InteractionResponse;
import thegamebrett.action.response.StartPseudoResponse;
import thegamebrett.model.GameLogic;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Field;
import thegamebrett.model.elements.Figure;
import thegamebrett.model.mediaeffect.SoundEffect;

public class D_GameLogic extends GameLogic{
    
    /*Fall unterscheidung die angibt ob response würfeln oder bewegen ist**/
    public final static Integer INTERACTIONRESPONSE_CHOICES_DICE = new Integer(0);
    public final static Integer INTERACTIONRESPONSE_CHOICES_CHOOSE_FIGURE = new Integer(1);
    
    int tt = 0;
        
    private InteractionRequest sent;
    
    
    public D_GameLogic(Model dependingModel) {
        super(dependingModel);
    }
    
    private boolean gameEnded = false;
    @Override
    public ActionRequest[] next(ActionResponse as) {
        if(gameEnded) {
            return null;
        }
        
        tt++;
        if(tt == 4) {
            GameEndRequest ger = new GameEndRequest(getDependingModel().getPlayers().toArray(new Player[0]), "Jeder gewinnt", 5000);
            gameEnded = true;
            return new ActionRequest[] {ger};
        }
        
        ArrayList<ActionRequest> requests = new ArrayList<>();

        if(as instanceof StartPseudoResponse){
            requests.add(new GUIUpdateRequest(GUIUpdateRequest.GUIUPDATE_ALL));
            Player p = getDependingModel().getPlayers().get(0);
            InteractionRequest ir = new InteractionRequest("Waehle einen Wert", new Object[]{new Integer(1), new Integer(2), new Integer(3)}, p, false, null);
            sent = ir;
            requests.add(ir);
        } else if(as instanceof InteractionResponse) {
            InteractionResponse res = ((InteractionResponse) as);
            if(res.getConcerningInteractionRequest() == sent) {
                int n = ((Integer)res.getChoice());
                for(int i=0; i<n; i++) {
                    Figure figure = res.getConcerningInteractionRequest().getPlayer().getFigures()[0];
                    //figure.setRelativeHeight(figure.getRelativeHeight() + 0.005);
                    figure.setField(figure.getField().getNext()[0]);
                    //((D_Field)figure.getField()).blub();
                    //figure.getField().getLayout().setBackgroundColor(Color.RED);
                }
                requests.add(new GUIUpdateRequest(GUIUpdateRequest.GUIUPDATE_ALL));
                requests.add(new PlaySoundRequest(new SoundEffect("sounds/Mouth_45.wav")));
                Player p = getNextPlayer((D_Player) res.getConcerningInteractionRequest().getPlayer());
                InteractionRequest ir = new InteractionRequest("Waehle einen Wert", new Object[]{new Integer(1), new Integer(2), new Integer(3)}, p, false, null);
                sent = ir;
                requests.add(ir);
            } else {
                throw new RuntimeException("Unbekannte Antwort");
            }
        }
        
        return requests.toArray(new ActionRequest[0]);
    }
    
    public D_Player getNextPlayer(D_Player currentPlayer){
        int currentPlayerNr = ((D_Player)currentPlayer).getPlayerNr();
        int nextPlayerNr = (currentPlayerNr + 1) % getDependingModel().getPlayers().size();
        for(Player p : getDependingModel().getPlayers())
            if(((D_Player)p).getPlayerNr() == nextPlayerNr)
                return (D_Player) p;
        throw new RuntimeException();
    }
    
    @Override
    public Field getNextStartPositionForPlayer(Player player) { // DEL
        throw new UnsupportedOperationException("Not supported yet.");
    }
 
}
