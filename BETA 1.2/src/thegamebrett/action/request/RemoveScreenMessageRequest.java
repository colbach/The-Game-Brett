package thegamebrett.action.request;

import thegamebrett.model.Player;

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
