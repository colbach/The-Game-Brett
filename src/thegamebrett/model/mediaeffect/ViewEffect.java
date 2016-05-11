package thegamebrett.model.mediaeffect;

import thegamebrett.model.RelativePoint;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 *
 * Diese Klasse ist nicht implementiert, aber soll die Grundlage fuer spaetere
 * Erweiterungen legen
 */
public abstract class ViewEffect {

    public abstract void startAnimation(RelativePoint start, RelativePoint end, float duration); // GraphicsContext fehlt hier noch!!!

}
