package thegamebrett.timer;

import thegamebrett.Manager;
import thegamebrett.action.request.GUIRequest;
import thegamebrett.action.request.TimerRequest;
import thegamebrett.action.response.TimerResponse;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Christian Colbach
 */
public class TimeManager {
    
    private class Task extends TimerTask {

        private final Manager manager;
        private final TimerRequest concerningTimerRequest;

        public Task(Manager manager, TimerRequest concerningTimerRequest) {
            this.manager = manager;
            this.concerningTimerRequest = concerningTimerRequest;
        }
        
        public void run() {
            manager.react(new TimerResponse(concerningTimerRequest));
        }
        
    }
    
    private final Manager manager;

    public TimeManager(Manager manager) {
        this.manager = manager;
    }
    
    public void react(TimerRequest request) {
        Timer timer = new Timer();
        timer.schedule(new Task(manager, request), request.getMillis());
    }
    
}
