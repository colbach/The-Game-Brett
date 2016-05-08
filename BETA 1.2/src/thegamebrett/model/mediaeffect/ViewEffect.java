package thegamebrett.model.mediaeffect;

import thegamebrett.model.RelativePoint;

/**
 * Diese Klasse ist nicht implementiert, aber soll die Grundlage fuer spaetere Erweiterungen legen
 * 
 * @author Christian Colbach
 */
public abstract class ViewEffect {
   
   public abstract void startAnimation(RelativePoint start, RelativePoint end, float duration); // GraphicsContext fehlt hier noch!!!
   
}