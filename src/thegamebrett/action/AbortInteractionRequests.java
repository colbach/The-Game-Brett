package thegamebrett.action;

/**
 * @author Christian Colbach
 */
public class AbortInteractionRequests implements GUIRequest, MobileRequest {
    
    private final InteractionRequest[] concerningInteractionRequests;

    public AbortInteractionRequests(InteractionRequest[] concerningInteractionRequests) {
        this.concerningInteractionRequests = concerningInteractionRequests;
    }

    public InteractionRequest[] getConcerningInteractionRequests() {
        return concerningInteractionRequests;
    }
    
}