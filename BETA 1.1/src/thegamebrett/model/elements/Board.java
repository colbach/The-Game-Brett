package thegamebrett.model.elements;

import thegamebrett.model.Layout;

/**
 * Definition des Spielbretts
 *
 * @author Christian Colbach
 */
public abstract class Board extends Element {
    
    //Anz der Felder
    public abstract int getFieldLength();
    
    public abstract Field getField(int i);
    
    public abstract Layout getLayout();
    
    public abstract float getRatioX();
    
    public abstract float getRatioY();
    
    
}