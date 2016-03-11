package thegamebrett.model.elements;

import thegamebrett.model.Layout;
import thegamebrett.model.Player;

/**
 * @author Christian Colbach
 */
public class Figure extends Element {
    
    /** Besitzer dieser Figur */
    private Player owner;
    
    /** Feld auf dem die Figur steht */
    private Field field;
    
    /** Layout von Figur */
    private Layout layout;

    private double relativeWidth;
    private double relativeHeight;

    public Figure(Player owner, Layout layout, double relativeWidth, double relativeHeight) {
        this.owner = owner;
        this.layout = layout;
        this.relativeWidth = relativeWidth;
        this.relativeHeight = relativeHeight;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        registerChange();
        this.layout = layout;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        registerChange();
        this.field = field;
    }

    public double getRelativeWidth() {
        registerChange();
        return relativeWidth;
    }

    public void setRelativeWidth(double relativeWidth) {
        registerChange();
        this.relativeWidth = relativeWidth;
    }

    public double getRelativeHeight() {
        return relativeHeight;
    }

    public void setRelativeHeight(double relativeHeight) {
        registerChange();
        this.relativeHeight = relativeHeight;
    }
    
    
    
}
