package testbrett.model.elements;

import testbrett.model.Layout;

/**
 * Definition des Spielbretts
 * 
 * @author christiancolbach
 */
public abstract class Board implements Element {
    
    public abstract int getFieldLength();
    public abstract Field getField(int i);
    
    public abstract Layout getLayout();
}