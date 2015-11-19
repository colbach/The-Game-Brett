package thegamebrett.sound;

import thegamebrett.Manager;
import thegamebrett.action.request.SoundRequest;
import thegamebrett.model.mediaeffect.SoundEffect;

/**
 * Kuemmert sich um Soundwiedergabe.
 *
 * @author Christian Colbach
 */
public class SoundManager {

    /*
    TODO:
    - Etablieren eines Threadsystems zum Management Wiedergabe der Soundelemente.
    */
    
    public static void playSoundEffect(SoundEffect se) {
        SoundHelper.playSound(se.getResource());
    }

    public SoundManager(Manager aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void react(SoundRequest soundRequest) {
        // mach was
    }

}
