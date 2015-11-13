/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.model;

/**
 *
 * @author christiancolbach
 */
public class RelativePoint {
    
    private double xRelative;
    private double yRelative;

    public double getXRelative() {
        return xRelative;
    }

    public void setXRelative(double xRelative) {
        this.xRelative = xRelative;
    }

    public double getYRelative() {
        return yRelative;
    }

    public void setYRelative(double yRelative) {
        this.yRelative = yRelative;
    }

    public RelativePoint(double xRelative, double yRelative) {
        this.xRelative = xRelative;
        this.yRelative = yRelative;
    }
    
    
}
