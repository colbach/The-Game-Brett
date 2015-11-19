package thegamebrett.action;

/**
 *
 * @author christiancolbach
 */
public class InteractionResponse {

    private final InteractionRequest concerningInteractionRequest;
    private final int responseIndex;

    public InteractionResponse(InteractionRequest concerningInteractionRequest, int responseIndex) {
        this.concerningInteractionRequest = concerningInteractionRequest;
        this.responseIndex = responseIndex;
    }

    public InteractionRequest getConcerningInteractionRequest() {
        return concerningInteractionRequest;
    }

    public int getResponseIndex() {
        return responseIndex;
    }
    
}
