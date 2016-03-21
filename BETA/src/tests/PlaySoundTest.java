package tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import thegamebrett.action.request.PlaySoundRequest;
import thegamebrett.action.request.StopSoundsRequest;
import thegamebrett.model.mediaeffect.SoundEffect;
import thegamebrett.sound.*;

/**
 * @author christiancolbach
 */
public class PlaySoundTest {
    
    public static void main(String[] args) {
        //SoundHelper.playSound("thegamebrett/sound/assets/glitch.wav");
        //SoundHelper.stopSounds();
        //SoundHelper.playSound("thegamebrett/sound/assets/Mouth_45.wav");
        
        PlaySoundRequest pst1 = new PlaySoundRequest(new SoundEffect("thegamebrett/sound/assets/Mouth_45.wav", true, 2000, 5));
        //PlaySoundRequest pst2 = new PlaySoundRequest(new SoundEffect("thegamebrett/sound/assets/Mouth_45.wav", 0, true));
        SoundManager sm = new SoundManager(null);
        
        sm.react(pst1);
        //sm.react(pst2);
        
        //sm.react(new StopSoundsRequest());
        
        
        try { // Nötig weil Programm sonst fertig bevor Sound fertig abgespielt ist
            Thread.sleep(100000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
