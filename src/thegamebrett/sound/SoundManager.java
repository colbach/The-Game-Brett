package thegamebrett.sound;

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

}
