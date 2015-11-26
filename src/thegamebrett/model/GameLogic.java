package thegamebrett.model;

import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.model.elements.Field;

/**
 * @author Christian Colbach
 */
public abstract class GameLogic {
    
    private final Model dependingModel;

    public GameLogic(Model dependingModel) {
        this.dependingModel = dependingModel;
    }

    public Model getDependingModel() {
        return dependingModel;
    }
    
    public abstract int getMaximumPlayers();
    public abstract int getMinimumPlayers();
    
    public abstract Field getNextStartPositionForPlayer(Player player);
    
    /** Kuemmert sich um alle Actionen jedweiliger Art */
    public abstract ActionRequest[] next(ActionResponse as);
    
}