package thegamebrett.action.request;

import thegamebrett.action.ActionRequest;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class TimerRequest implements ActionRequest {

    private final int millis;

    /**
     * Zeit die gewartet werden soll in Millisekunden (1000ms = 1s)
     */
    public TimerRequest(int millis) {
        this.millis = millis;
    }

    public int getMillis() {
        return millis;
    }

}
