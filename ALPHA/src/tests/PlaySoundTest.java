package tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import thegamebrett.sound.*;

/**
 * @author christiancolbach
 */
public class PlaySoundTest {
    
    public static void main(String[] args) {
        SoundHelper.playSound("thegamebrett/sound/assets/glitch.wav");
        SoundHelper.playSound("thegamebrett/sound/assets/Mouth_45.wav");
        
        try { // Nötig weil Programm sonst fertig bevor Sound fertig abgespielt ist
            Thread.sleep(100000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
