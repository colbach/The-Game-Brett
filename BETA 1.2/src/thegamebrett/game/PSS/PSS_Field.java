/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.PSS;

import thegamebrett.game.MADN.MADN_Field;
import thegamebrett.model.Layout;
import thegamebrett.model.RelativePoint;
import thegamebrett.model.elements.Field;
import thegamebrett.model.mediaeffect.MediaEffect;

/**
 *
 * @author Korè
 */
public class PSS_Field extends Field{
    
    
    private double width;
    private double height;
    private RelativePoint position;
    private PSS_Field next;
    private Layout layout;
    private MediaEffect me;
    private int fieldIndex;

    public PSS_Field(int fieldIndex, double width, double height, RelativePoint position, PSS_Field next, Layout layout, MediaEffect mediaEffect) {
        this.fieldIndex = fieldIndex;
        this.height = height;
        this.width = width;
        this.layout = layout;
        this.position = position;
        this.me = mediaEffect;
        this.next = next;
    }

    public void addNext(PSS_Field newField) {
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

    public PSS_Field getSingleNext(){
        return next;
    }
    
    @Override
    public MediaEffect getMediaEffect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Field[] getNext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Field[] getPrevious() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Layout getLayout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    int getFieldIndex() {
        return fieldIndex;
    }

}
