package thegamebrett.model.elements;

import thegamebrett.model.Layout;
import thegamebrett.model.mediaeffect.MediaEffect;

/**
 * Defintion eines einzelnen Feldes
 * 
 * @author christiancolbach
 */
public abstract class Field implements Element {
    
    /** gibt relative horizontale und vertikale Position zurueck. Wert [0d, 1d]*/
    public abstract double getRelativePosition();
    
    /** gibt relative Breite zurueck. Wert [0d, 1d]*/
    public abstract double getWidthRelative();
    
    /** gibt relative Hoehe zurueck. Wert [0d, 1d]*/
    public abstract double getHeightRelative();
    
    /** gibt naechste Felder zurueck */
    public abstract Field[] getNext();
    
    /** gibt vorhergehende Felder zurueck */
    public abstract Field[] getPrevious();
    
    /** gibt Layout zurueck */
    public abstract Layout getLayout();
    
    /** gibt Animaton/Sound zurueck */
    public abstract MediaEffect getMediaEffect();
    
}
