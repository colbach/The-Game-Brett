package thegamebrett.model.mediaeffect;

import thegamebrett.model.RelativePoint;

/**
 *
 * @author christiancolbach
 */
public abstract class ViewEffect {
   
   public abstract void startAnimation(RelativePoint start, RelativePoint end, float duration); // GraphicsContext fehlt hier noch!!!
   
}