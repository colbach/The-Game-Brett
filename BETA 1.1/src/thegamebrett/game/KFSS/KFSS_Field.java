/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.KFSS;

import thegamebrett.game.PSS.*;
import thegamebrett.model.Layout;
import thegamebrett.model.RelativePoint;
import thegamebrett.model.elements.Field;
import thegamebrett.model.mediaeffect.MediaEffect;

/**
 *
 * @author Kore
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Field[] getNext() {
        KFSS_Field[] fields = {next};
        return fields;
    }

    @Override
    public Field[] getPrevious() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    int getFieldIndex() {
        return fieldIndex;
    }

}
