package thegamebrett.gui;


/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 * 
 * Kuemert sich um Skalierungsfaktoren etc
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

    public static void setScreenDimension(int screenWidth, int screenHeigth) {
        ScreenResolution.screenWidth = screenWidth;
        ScreenResolution.screenHeigth = screenHeigth;
    }

    public static void setBoardRatios(float ratioX, float ratioY) {
        vhFactior = ratioY / ratioX;
        contentHeigth = screenHeigth;
        contentWidth = screenHeigth / vhFactior;
        contentXOff = (int) ((screenWidth - contentWidth) / 2);
        contentYOff = (int) ((screenHeigth - contentHeigth) / 2);
    }

    public static int getContentXOff() {
        return contentXOff;
    }

    public static int getContentYOff() {
        return contentYOff;
    }

    /**
     * Konvertiert relative Groesse (Wertebereich [0,1]) zu Pixelgroesse
     */
    public static int relativeToPixelX(double relative) {
        return (int) ((relative * contentWidth));
    }

    /**
     * Konvertiert relative Groesse (Wertebereich [0,1]) zu Pixelgroesse
     */
    public static int relativeToPixelY(double relative) {
        return (int) ((relative * contentHeigth));
    }

}
