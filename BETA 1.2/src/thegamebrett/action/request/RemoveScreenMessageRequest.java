package thegamebrett.action.request;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class RemoveScreenMessageRequest implements GUIRequest {

    private final ScreenMessageRequest screenMessageRequest;

    public RemoveScreenMessageRequest(ScreenMessageRequest screenMessageRequest) {
        this.screenMessageRequest = screenMessageRequest;
    }

    public RemoveScreenMessageRequest() {
        this(null);
    }

    public ScreenMessageRequest getScreenMessageRequest() {
        return screenMessageRequest;
    }

}
