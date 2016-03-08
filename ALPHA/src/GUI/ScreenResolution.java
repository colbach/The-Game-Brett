package GUI;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;


/**
 * Kuemert sich um Skalierungsfaktoren etc
 * 
 * @author christiancolbach
 */
public class ScreenResolution {
    
    private final Rectangle2D primaryScreenBounds;
    
    private static int screenWidth;
    
    private static int screenHeight;
    
    private static int scalefactor;
    
    public ScreenResolution() {
        
        primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        
        this.screenWidth = (int)primaryScreenBounds.getWidth();
        this.screenHeight = (int)primaryScreenBounds.getHeight();
        //this.scalefactor = large length / short length
    }
    
    public static int getScreenWidth() {
        return screenWidth;
    }
    
    public static int getScreenHeight() {
        return screenHeight;
    }
    
}
