/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.menschaergerdichnicht;

import thegamebrett.model.Layout;
import thegamebrett.model.RelativePoint;
import thegamebrett.model.mediaeffect.MediaEffect;

/**
 *
 * @author Korè
 */
public class MADNendField extends MADNfield{
    
    public MADNendField(double width, double height, RelativePoint position, MADNfield[] next, Layout layout, MediaEffect me) {
        super(width, height, position, next, layout, me);
    }
    
}
