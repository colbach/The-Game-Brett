package thegamebrett.action.response;

import thegamebrett.action.ActionResponse;
import thegamebrett.action.request.InteractionRequest;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class InteractionResponse implements ActionResponse {

    private final InteractionRequest concerningInteractionRequest;
    private final int choiceIndex;

    public InteractionResponse(InteractionRequest concerningInteractionRequest, int choiceIndex) {
        this.concerningInteractionRequest = concerningInteractionRequest;
        this.choiceIndex = choiceIndex;
    }

    public InteractionRequest getConcerningInteractionRequest() {
        return concerningInteractionRequest;
    }

    public int getChoiceIndex() {
        return choiceIndex;
    }

    public Object getChoice() {
        return concerningInteractionRequest.getChoices()[getChoiceIndex()];
    }

    public String getChoiceString() {
        return getChoice().toString();
    }
}
