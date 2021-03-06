package thegamebrett.action.request;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class GUIUpdateRequest implements GUIRequest {

    public static final int GUIUPDATE_FIELDS = 1;
    public static final int GUIUPDATE_FIGURES = 2;
    public static final int GUIUPDATE_BOARDLAYOUT = 4;
    public static final int GUIUPDATE_ALL = GUIUPDATE_FIELDS + GUIUPDATE_FIGURES + GUIUPDATE_BOARDLAYOUT;

    private final boolean updateFields;
    private final boolean updateFigures;
    private final boolean updateBoardLayout;

    private final boolean animated;

    private final int delay;

    public GUIUpdateRequest(int value) {
        this(value, false, 0);
    }

    public GUIUpdateRequest(int value, boolean animated, int delay) {

        if (value < 0 || value > 7) {
            throw new IllegalArgumentException("0 <= value <= 7");
        }

        this.updateBoardLayout = value >= 4;
        if (this.updateBoardLayout) {
            value -= 4;
        }
        this.updateFigures = value >= 2;
        if (this.updateFigures) {
            value -= 2;
        }
        this.updateFields = value == 1;

        this.animated = animated;

        this.delay = delay;

    }

    public boolean isUpdateFields() {
        return updateFields;
    }

    public boolean isUpdateFigures() {
        return updateFigures;
    }

    public boolean isUpdateBoardLayout() {
        return updateBoardLayout;
    }

    public boolean isAnimated() {
        return animated;
    }

    public int getDelay() {
        return delay;
    }
}
