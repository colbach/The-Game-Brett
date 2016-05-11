package thegamebrett.action.request;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
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
