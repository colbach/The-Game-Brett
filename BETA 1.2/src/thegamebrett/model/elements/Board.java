package thegamebrett.model.elements;

import thegamebrett.model.Layout;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 *
 * Definition des Spielbretts
 */
public abstract class Board extends Element {

    /**
     * Anzahl der Felder
     */
    public abstract int getFieldLength();

    public abstract Field getField(int i);

    public abstract Layout getLayout();

    public abstract float getRatioX();

    public abstract float getRatioY();

}
