package thegamebrett.gui;


/**
 * Kuemert sich um Skalierungsfaktoren etc
 *
 * @author Christian Colbach
 */
public class ScreenResolution {
    
    public static int getScreenWidth() {
        throw new RuntimeException("Noch nicht implementiert");
    }
    
    public static int getScreenHeigth() {
        throw new RuntimeException("Noch nicht implementiert");
    }
    
    public static int getBoardWidth() {
        throw new RuntimeException("Noch nicht implementiert");
    }
    
    public static int getBoardHeigth() {
        throw new RuntimeException("Noch nicht implementiert");
    }
    
    public static void setBoardRatios(float ratioX, float ratioY) {
        throw new RuntimeException("Noch nicht implementiert");
    }
    
    /** Konvertiert relative Groesse (Wertebereich [0,1]) zu Pixelgroesse */
    public static int relativeToPixelX(double relative) {
        return (int)(relative*500); // zum testen
    }
    
    /** Konvertiert relative Groesse (Wertebereich [0,1]) zu Pixelgroesse */
    public static int relativeToPixelY(double relative) {
        return (int)(relative*500); // zum testen
    }
    
}
