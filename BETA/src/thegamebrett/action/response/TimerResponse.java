package thegamebrett.action.response;

import thegamebrett.action.ActionResponse;
import thegamebrett.action.request.TimerRequest;

/**
 * @author Christian Colbach
 */
public class TimerResponse implements ActionResponse {
    
    private final TimerRequest concerningTimerRequest;

    public TimerResponse(TimerRequest concerningTimerRequest) {
        this.concerningTimerRequest = concerningTimerRequest;
    }

    public TimerRequest getConcerningTimerRequest() {
        return concerningTimerRequest;
    }
}
