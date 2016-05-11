package thegamebrett.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class Layout {

    private volatile Boolean changeRegistrar = true;

    /**
     * Diese Methode muss bei JEDER AENDERUNG aufgerufen werden damit UI diese
     * Aktualisiert.
     */
    public void registerChange() {
        changeRegistrar = true;
    }

    public boolean isChangedSinceLastCall() {
        synchronized (changeRegistrar) {
            if (changeRegistrar) {
                changeRegistrar = false;
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Hintergrungbild füllt ganzes Feld (Bild wird abgeschnitten damit es
     * passt)
     */
    public static final int BACKGROUND_IMAGE_FILL_FACTOR_FILL = 0;
    /**
     * Hintergrungbild wird ganz angezeigt (Bild ist auf jeden Fall ganz im
     * Feld)
     */
    public static final int BACKGROUND_IMAGE_FILL_FACTOR_REPEAT = 1;

    /**
     * Formfaktor quadratisch
     */
    public static final int FORM_FACTOR_SQUARE = 3;
    /**
     * Formfaktor rund
     */
    public static final int FORM_FACTOR_OVAL = 4;

    /**
     * gibt an ob Feld sichtbar ist
     */
    private boolean visible = true;

    /**
     * Hintergrundbild
     */
    private Image backgroundImage = null;

    /**
     * Hintergrundbild Fuelfaktor. BACKGROUND_IMAGE_FILL_FACTOR_FILL oder
     * BACKGROUND_IMAGE_FILL_FACTOR_ASPECT
     */
    private int backgroundImageFillFactor = BACKGROUND_IMAGE_FILL_FACTOR_FILL;

    /**
     * Icon
     */
    private Image iconImage = null;

    /**
     * Hintergrundfarbe falls kein Hintergrundbild gesetzt ist
     */
    private Color backgroundColor = Color.WHITE;

    /**
     * Farbe fuer Titel
     */
    private Color titleColor = Color.BLACK;

    /**
     * Vergroesserungs / Verkleinerungsfaktor fuer Titel. Wert [-1, 1]
     */
    private float titleScaleFactor = 0;

    private boolean centerTitle = true;

    /**
     * Titel
     */
    private String title;

    /**
     * Farbe fuer Subtext
     */
    private Color subtextColor = Color.BLACK;

    /**
     * Vergroesserungs / Verkleinerungsfaktor fuer Subtext. Wert [-1, 1]
     */
    private float subtextScaleFactor = 0;

    private boolean centerSubtext = true;

    /**
     * Rand IN PIXEL!
     */
    private float border = 1;

    /**
     * Randfarbe
     */
    private Color borderColor = Color.BLACK;

    /**
     * Subtext
     */
    private String subtext;

    /**
     * Form des Feldes. FORM_FACTOR_SQUARE oder FORM_FACTOR_OVAL
     */
    private int formFactor = FORM_FACTOR_SQUARE;

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        registerChange();
        this.backgroundImage = backgroundImage;
    }

    public void setBackgroundImage(Canvas backgroundImage) {
        WritableImage wi = new WritableImage((int) iconImage.getWidth(), (int) iconImage.getHeight());
        backgroundImage.snapshot(null, wi);
        setBackgroundImage(wi);
    }

    public int getBackgroundImageFillFactor() {
        return backgroundImageFillFactor;
    }

    public void setBackgroundImageFillFactor(int backgroundImageFillFactor) {
        registerChange();
        this.backgroundImageFillFactor = backgroundImageFillFactor;
    }

    public Image getIconImage() {
        return iconImage;
    }

    public void setIconImage(Image iconImage) {
        registerChange();
        this.iconImage = iconImage;
    }

    public void setIconImage(Canvas iconImage) {
        WritableImage wi = new WritableImage((int) iconImage.getWidth(), (int) iconImage.getHeight());
        iconImage.snapshot(null, wi);
        setIconImage(wi);
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        registerChange();
        this.backgroundColor = backgroundColor;
    }

    public Color getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(Color titleColor) {
        registerChange();
        this.titleColor = titleColor;
    }

    public float getTitleScaleFactor() {
        return titleScaleFactor;
    }

    public void setTitleScaleFactor(float titleScaleFactor) {
        registerChange();
        this.titleScaleFactor = titleScaleFactor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        registerChange();
        this.title = title;
    }

    public Color getSubtextColor() {
        return subtextColor;
    }

    public void setSubtextColor(Color subtextColor) {
        registerChange();
        this.subtextColor = subtextColor;
    }

    public float getSubtextScaleFactor() {
        return subtextScaleFactor;
    }

    public void setSubtextScaleFactor(float subtextScaleFactor) {
        registerChange();
        this.subtextScaleFactor = subtextScaleFactor;
    }

    public String getSubtext() {
        return subtext;
    }

    public void setSubtext(String subtext) {
        registerChange();
        this.subtext = subtext;
    }

    public int getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(int formFactor) {
        registerChange();
        this.formFactor = formFactor;
    }

    public Layout(String title, String subtext) {
        this.title = title;
        this.subtext = subtext;
    }

    public Layout() {
        this.title = "";
        this.subtext = "";
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        registerChange();
        this.visible = visible;
    }

    public float getBorder() {
        return border;
    }

    public void setBorder(float border) {
        registerChange();
        this.border = border;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        registerChange();
        this.borderColor = borderColor;
    }

    public boolean isCenterTitle() {
        return centerTitle;
    }

    public void setCenterTitle(boolean centerTitle) {
        this.centerTitle = centerTitle;
    }

    public boolean isCenterSubtext() {
        return centerSubtext;
    }

    public void setCenterSubtext(boolean centerSubtext) {
        this.centerSubtext = centerSubtext;
    }
}
