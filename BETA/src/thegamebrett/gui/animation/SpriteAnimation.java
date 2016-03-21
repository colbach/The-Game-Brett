/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.gui.animation;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    private int lastIndex;
    
    public SpriteAnimation(ImageView imageView, 
                           Duration duration, 
                           int count,   int columns,
                           int offsetX, int offsetY,
                           int width,   int height) {
        
        this.imageView = imageView;                 // image for animation | bild fuer die animation
        this.count     = count;                     // number of frames | anzahl der frames
        this.columns   = columns;                   // how many frames are in one row | anzahl der frames in einer reihe
        this.offsetX   = offsetX;                   // offset of the first frame | offset bzw. verschiebung inx des ersten frames
        this.offsetY   = offsetY;                   // "    "   "   "   "   "
        this.width     = width;                     // width of all frames | laenge aller frames
        this.height    = height;                    // height of all frames | hoehe aller frames
        
        setCycleDuration(duration);                 // duration of a single cycle ( time through all frames) | dauer eines einzigen durchlaufs
        setInterpolator(Interpolator.LINEAR);       // setting interpolarisation to linear | interpolarisation auf linear gesetzt
    }
    
  protected void interpolate(double k) {
      // Calculation of the needed framerate to display at time X. | berechnung der nötigen frame rate um zum zeitpunkt X anzeigen zu koennen
      // Changes of the needed framerate results in calling interpolate() | veraenderung der framerate resultiert im aufruf von interpolate()
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) { 
      // Calculating new frame position | berechnung der neuen frame position   
            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}
