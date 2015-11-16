package thegamebrett.sound;

import thegamebrett.model.mediaeffect.SoundEffect;

/**
 * Kuemmert sich um Soundwiedergabe.
 *
 * @author Christian Colbach
 */
public class SoundManager {

    public static void playSoundEffect(SoundEffect se) {
        SoundHelper.playSound(se.getResource());
    }

}
