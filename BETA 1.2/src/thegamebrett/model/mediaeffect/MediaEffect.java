package thegamebrett.model.mediaeffect;

import thegamebrett.model.RelativePoint;
import thegamebrett.sound.SoundManager;

/**
 * Animation und Sound Wiedergabe
 * 
 * @author Christian Colbach
 */
public class MediaEffect {
    
    private SoundEffect se;
    private ViewEffect ve;
    
    public void startEffect(RelativePoint point, float duration) { // GraphicsContext fehlt hier noch!!!
       this.startEffect(point, point, duration);
    }
   
   public void startEffect(RelativePoint start, RelativePoint end, float duration) { // GraphicsContext fehlt hier noch!!!
       SoundManager.playSoundEffect(se);
       ve.startAnimation(start, end, duration);
   }
   
}