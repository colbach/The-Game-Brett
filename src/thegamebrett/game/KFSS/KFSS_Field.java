package thegamebrett.game.KFSS;

import thegamebrett.model.Layout;
import thegamebrett.model.RelativePoint;
import thegamebrett.model.elements.Field;
import thegamebrett.model.mediaeffect.MediaEffect;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class KFSS_Field extends Field{
    
    
    private double width;
    private double height;
    private RelativePoint position;
    private KFSS_Field next;
    private Layout layout;
    private MediaEffect me;
    private int fieldIndex;

    public KFSS_Field(int fieldIndex, double width, double height, RelativePoint position, KFSS_Field next, Layout layout, MediaEffect mediaEffect) {
        this.fieldIndex = fieldIndex;
        this.height = height;
        this.width = width;
        this.layout = layout;
        this.position = position;
        this.me = mediaEffect;
        this.next = next;
    }

    public void addNext(KFSS_Field newField) {
        this.next = newField;
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

    public KFSS_Field getSingleNext(){
        return next;
    }
    
    @Override
    public MediaEffect getMediaEffect() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Field[] getNext() {
        KFSS_Field[] fields = {next};
        return fields;
    }

    @Override
    public Field[] getPrevious() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    int getFieldIndex() {
        return fieldIndex;
    }

}
