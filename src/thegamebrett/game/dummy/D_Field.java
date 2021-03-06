package thegamebrett.game.dummy;

import thegamebrett.model.Layout;
import thegamebrett.model.RelativePoint;
import thegamebrett.model.elements.Field;
import thegamebrett.model.mediaeffect.MediaEffect;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class D_Field extends Field{

    private double width;
    private double height;
    private RelativePoint position;
    private D_Field next;
    private Layout layout;
    private MediaEffect me;
    private int index;
    
    public D_Field(double width, double height, RelativePoint position, Layout layout, int index) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.layout = layout;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    @Override
    public RelativePoint getRelativePosition() {
        return position;
    }
    
    @Override
    public double getWidthRelative() {
        return width;
    }

    @Override
    public double getHeightRelative() {
        return height;
    }

    @Override
    public D_Field[] getNext() {
        return new D_Field[] { next };
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    @Override
    public MediaEffect getMediaEffect() {
        return me;
    }

    public void setPosition(RelativePoint position) {
        //registerChange(); // IST KEIN CHANGE!!!
        this.position = position;
    }

    public void setNext(D_Field next) {
        this.next = next;
    }
    
    public void addNext(D_Field next) {
        next = this.next;
    }
    
    public void setMediaEffect(MediaEffect me) {
        this.me = me;
    }

    public RelativePoint getPosition() {
        return position;
    }
    
    @Override
    public Field[] getPrevious() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
