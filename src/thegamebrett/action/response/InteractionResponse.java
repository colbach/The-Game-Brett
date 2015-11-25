package thegamebrett.action.response;

import thegamebrett.action.request.InteractionRequest;

/**
 * @author Christian Colbach
 */
public class InteractionResponse {

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
    
}
