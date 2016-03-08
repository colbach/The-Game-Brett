package thegamebrett.game.MADN;

import thegamebrett.model.Layout;
import thegamebrett.model.RelativePoint;
import thegamebrett.model.elements.Field;
import thegamebrett.model.mediaeffect.MediaEffect;

/**
 * @author Koré
 */
public class MADNfield extends Field{

    private double width;
    private double height;
    private RelativePoint position;
    private MADNfield[] next;
    private Layout layout;
    private MediaEffect me;
    private int fieldType;
    
    public final static int FIELD_TYPE_NORMAL = 0;
    public final static int FIELD_TYPE_START = 1;
    public final static int FIELD_TYPE_END = 2;
    
    
    
    public MADNfield(double width, double height, RelativePoint position, MADNfield[] next, Layout layout, MediaEffect mediaEffect, int fieldType){
        this.width = width;
        this.height = height;
        this.position = position;
        this.next = next;
        this.layout = layout;
        this.me = mediaEffect;
        this.fieldType = fieldType;
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
    public MADNfield[] getNext() {
        return next;
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
        this.position = position;
    }

    public void setNext(MADNfield[] next) {
        this.next = next;
    }
    public void addNext(MADNfield nextOne) {

        MADNfield[] newNext;
        if(next != null){
            newNext = new MADNfield[next.length+1];
            for(int i=0; i<next.length; i++){
                newNext[i] = next[i];
            }
        newNext[next.length] = nextOne;
        } else {
            newNext = new MADNfield[1];
            newNext[0] = nextOne;
        }
        next = newNext;
    }
    public void setMediaEffect(MediaEffect me) {
        this.me = me;
    }

    public RelativePoint getPosition() {
        return position;
    }

    public int getFieldType() {
        return fieldType;
    }
    
    
    
    
    //nicht implementiert da nicht notwendig
    @Override
    public Field[] getPrevious() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
