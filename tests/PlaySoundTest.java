package tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import thegamebrett.sound.SoundManager;

/**
 * @author christiancolbach
 */
public class PlaySoundTest {
    
    public static void main(String[] args) {
        SoundManager.playSound("thegamebrett/sound/assets/glitch.wav");
        
        try { // Nötig weil Programm sonst fertig bevor Sound fertig abgespielt ist
            Thread.sleep(100000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
