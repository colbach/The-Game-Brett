package thegamebrett.model.mediaeffect;

import thegamebrett.model.RelativePoint;
import thegamebrett.sound.SoundManager;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 * 
 * Animation und Sound Wiedergabe (fuer zukuenftige Implementierung)
 */
public class MediaEffect {
    
    private SoundEffect se;
    private ViewEffect ve;
    
    public void startEffect(RelativePoint point, float duration) {
       this.startEffect(point, point, duration);
    }
   
   public void startEffect(RelativePoint start, RelativePoint end, float duration) {
       SoundManager.playSoundEffect(se);
       ve.startAnimation(start, end, duration);
   }
   
}