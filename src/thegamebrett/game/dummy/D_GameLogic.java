package thegamebrett.game.dummy;

import java.util.ArrayList;
import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.action.request.GUIUpdateRequest;
import thegamebrett.action.request.GameEndRequest;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.action.request.PlaySoundRequest;
import thegamebrett.action.request.RemoveScreenMessageRequest;
import thegamebrett.action.request.ScreenMessageRequest;
import thegamebrett.action.response.InteractionResponse;
import thegamebrett.action.response.StartPseudoResponse;
import thegamebrett.model.GameLogic;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Field;
import thegamebrett.model.elements.Figure;
import thegamebrett.model.mediaeffect.SoundEffect;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class D_GameLogic extends GameLogic {

    private InteractionRequest sent;

    public D_GameLogic(Model dependingModel) {
        super(dependingModel);
    }

    private boolean gameEnded = false;
    private boolean messageShown = false;

    @Override
    public ActionRequest[] next(ActionResponse as) {
        if (gameEnded) {
            return null;
        }

        ArrayList<ActionRequest> requests = new ArrayList<>();

        if (messageShown) {
            RemoveScreenMessageRequest rsmr = new RemoveScreenMessageRequest();
            requests.add(rsmr);
            messageShown = false;
        }

        if (as instanceof StartPseudoResponse) {
            requests.add(new GUIUpdateRequest(GUIUpdateRequest.GUIUPDATE_ALL));
            Player p = getDependingModel().getPlayers().get(0);
            InteractionRequest ir = new InteractionRequest("Waehle einen Wert", new Object[]{new Integer(1), new Integer(2), new Integer(3)}, p, false, null);
            sent = ir;
            requests.add(ir);
        } else if (as instanceof InteractionResponse) {
            InteractionResponse res = ((InteractionResponse) as);
            if (res.getConcerningInteractionRequest() == sent) {
                int n = ((Integer) res.getChoice());
                for (int i = 0; i < n; i++) {
                    Figure figure = res.getConcerningInteractionRequest().getPlayer().getFigures()[0];
                    D_Field field = (D_Field) figure.getField().getNext()[0];
                    figure.setField(field);
                    if (field.getIndex() == 4) {
                        //ScreenMessageRequest ger = new ScreenMessageRequest("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet,", res.getConcerningInteractionRequest().getPlayer());
                        ScreenMessageRequest ger = new ScreenMessageRequest(res.getConcerningInteractionRequest().getPlayer().getUser().getUserCharacter().getName() + " hat eine halbe Runde geschafft", res.getConcerningInteractionRequest().getPlayer());
                        messageShown = true;
                        requests.add(ger);
                    }
                    if (field.getIndex() == 9) {
                        GameEndRequest ger = new GameEndRequest(new Player[]{res.getConcerningInteractionRequest().getPlayer()}, res.getConcerningInteractionRequest().getPlayer().getUser().getUserCharacter().getName() + " hat gewonnen!", 1000);
                        gameEnded = true;
                        return new ActionRequest[]{new RemoveScreenMessageRequest(), new GUIUpdateRequest(GUIUpdateRequest.GUIUPDATE_ALL), ger};
                    }
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

    public D_Player getNextPlayer(D_Player currentPlayer) {
        int currentPlayerNr = ((D_Player) currentPlayer).getPlayerNr();
        int nextPlayerNr = (currentPlayerNr + 1) % getDependingModel().getPlayers().size();
        for (Player p : getDependingModel().getPlayers()) {
            if (((D_Player) p).getPlayerNr() == nextPlayerNr) {
                return (D_Player) p;
            }
        }
        throw new RuntimeException();
    }

    @Override
    public Field getNextStartPositionForPlayer(Player player) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
