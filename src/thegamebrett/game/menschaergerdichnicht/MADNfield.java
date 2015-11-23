/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.menschaergerdichnicht;

import thegamebrett.model.Layout;
import thegamebrett.model.RelativePoint;
import thegamebrett.model.elements.Field;
import thegamebrett.model.mediaeffect.MediaEffect;

/**
 *
 * @author Korè
 */
public class MADNfield extends Field{

    private double width;
    private double height;
    private RelativePoint position;
    private MADNfield[] next;
    private Layout layout;
    private MediaEffect me;
    
    
    public MADNfield(double width, double height, RelativePoint position, MADNfield[] next, Layout layout, MediaEffect me){
        this.width = width;
        this.height = height;
        this.position = position;
        this.next = next;
        this.layout = layout;
        this.me = me;
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
    public Field[] getNext() {
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

    public void setMediaEffect(MediaEffect me) {
        this.me = me;
    }
    
    
    //nicht implementiert da nicht notwendig
    @Override
    public Field[] getPrevious() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
