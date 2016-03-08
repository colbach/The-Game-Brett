package thegamebrett.gui;

import javafx.geometry.Dimension2D;


/**
 * Kuemert sich um Skalierungsfaktoren etc
 *
 * @author Christian Colbach
 */
public class ScreenResolution {
    
    private static int screenWidth = 500;
    private static int screenHeigth = 500;
    
    private static double vhFactior = 1;
    private static double contentWidth = screenWidth;
    private static double contentHeigth = screenHeigth;
    private static int contentXOff;
    private static int contentYOff;
            
    public static int getScreenWidth() {
        return screenWidth;
    }
    
    public static int getScreenHeigth() {
        return screenHeigth;
    }
    
    public static int getBoardWidth() {
        throw new RuntimeException("Noch nicht implementiert");
    }
    
    public static int getBoardHeigth() {
        throw new RuntimeException("Noch nicht implementiert");
    }
    
    public static void setScreenDimension(int screenWidth, int screenHeigth) {
        ScreenResolution.screenWidth = screenWidth;
        ScreenResolution.screenHeigth = screenHeigth;
    }
    
    public static void setBoardRatios(float ratioX, float ratioY) {
        vhFactior = ratioY / ratioX;
        if(vhFactior >= 1) {
            contentWidth = screenHeigth / vhFactior;
            
            //System.out.println(contentWidth +"="+ screenWidth +"/"+ vhFactior);
            contentHeigth = screenHeigth;
        } else {
            contentHeigth = screenWidth * vhFactior;
            
            //System.out.println(contentHeigth +"="+ screenHeigth +"*"+ vhFactior);
            contentWidth = screenWidth;
        }
        contentXOff = (int)((screenWidth - contentWidth) / 2);
        contentYOff = (int)((screenHeigth - contentHeigth) / 2);
    }

    public static int getContentXOff() {
        return contentXOff;
    }

    public static int getContentYOff() {
        return contentYOff;
    }
    
    /** Konvertiert relative Groesse (Wertebereich [0,1]) zu Pixelgroesse */
    public static int relativeToPixelX(double relative) {
        return (int)((relative*contentWidth));
    }
    
    /** Konvertiert relative Groesse (Wertebereich [0,1]) zu Pixelgroesse */
    public static int relativeToPixelY(double relative) {
        return (int)((relative*contentHeigth));
    }
    
}
