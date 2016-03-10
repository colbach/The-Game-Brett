package thegamebrett.sound;

import java.util.Timer;
import java.util.TimerTask;
import thegamebrett.Manager;
import thegamebrett.action.request.PlaySoundRequest;
import thegamebrett.action.request.SoundRequest;
import thegamebrett.action.request.StopSoundsRequest;
import thegamebrett.model.mediaeffect.SoundEffect;

/**
 * Kuemmert sich um Soundwiedergabe.
 *
 * @author Christian Colbach
 */
public class SoundManager {

    private static volatile long stopID = 0;

    public static void playSoundEffect(SoundEffect se) {

        if (se.isLoop()) {
            loopSoundEffect(se, se.getLoopInterval(), se.getLoopCount(), stopID);
        } else {
            SoundHelper.playSound(se.getResource());
        }
    }

    public static void loopSoundEffect(SoundEffect se, long delay, int times, long stopIDm) {
        if (stopID <= stopIDm) {
            SoundHelper.playSound(se.getResource());
            if (times > 1) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        loopSoundEffect(se, delay, times - 1, stopIDm);
                    }
                }, delay);
            }
        }
    }

    public SoundManager(Manager aThis) {
    }

    public void react(SoundRequest soundRequest) {
        // mach was
        if (soundRequest instanceof PlaySoundRequest) {
            PlaySoundRequest r = (PlaySoundRequest) soundRequest;
            playSoundEffect(r.getSound());
        } else if(soundRequest instanceof StopSoundsRequest) {
            stopID ++;
            SoundHelper.stopSounds();
        }

    }

}
