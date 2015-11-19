/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.model;

import thegamebrett.gamescreen.ScreenResolution;

/**
 *
 * @author christiancolbach
 */
public class RelativePoint {
    
    private final double xRelative;
    private final double yRelative;

    public double getXRelative() {
        return xRelative;
    }

    public double getYRelative() {
        return yRelative;
    }
    
    public double getXOnScreen() {
        return xRelative * ScreenResolution.getScreenWidth();
    }

    public double getYOnScreen() {
        return yRelative * ScreenResolution.getScreenHeigth();
    } 

    /** erwartet Werte: [0,1] */
    public RelativePoint(double xRelative, double yRelative) {
        this.xRelative = xRelative;
        this.yRelative = yRelative;
    }
    
    /** (Hilfsconstructuor) erwartet Werte: [0,1000] */
    public RelativePoint(int xRelativeTimes1000, int yRelativeTimes1000) {
        this.xRelative = xRelativeTimes1000 / 1000d;
        this.yRelative = yRelativeTimes1000 / 1000d;
    }
    
    
}
