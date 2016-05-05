package thegamebrett.action.request;

import thegamebrett.action.ActionRequest;

/**
 * @author Christian Colbach
 */
public class TimerRequest implements ActionRequest {
    
    private final int millis;

    /** Zeit die gewartet werden soll in Millisekunden (1000ms = 1s) */
    public TimerRequest(int millis) {
        this.millis = millis;
    }

    public int getMillis() {
        return millis;
    }
    
}
