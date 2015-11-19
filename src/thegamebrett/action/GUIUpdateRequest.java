package thegamebrett.action;

/**
 *
 * @author christiancolbach
 */
public class GUIUpdateRequest implements GUIRequest {
    
    public final int GUIUPDATE_FIELDS = 1;
    public final int GUIUPDATE_FIGURES = 2;
    public final int GUIUPDATE_BOARDLAYOUT = 4;
    
    private final boolean updateFields;
    private final boolean updateFigures;
    private final boolean updateBoardLayout;
    
    private final boolean animated;

    public GUIUpdateRequest(int value, boolean animated) {
        
        if(value < 0 || value > 7)
            throw new IllegalArgumentException("0 <= value <= 7");
        
        this.updateBoardLayout = value >= 4;
        value -= 4;
        this.updateFigures = value >= 2;
        value -= 2;
        this.updateFields = value == 1;
        
        this.animated = animated;
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

}
