package thegamebrett.model;

import thegamebrett.action.ActionRequest;
import thegamebrett.action.ActionResponse;
import thegamebrett.model.elements.Field;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public abstract class GameLogic {

    private Model dependingModel;

    public GameLogic(Model dependingModel) {
        this.dependingModel = dependingModel;
    }

    public Model getDependingModel() {
        return dependingModel;
    }

    public void setDependingModel(Model dependingModel) {
        this.dependingModel = dependingModel;
    }

    public abstract Field getNextStartPositionForPlayer(Player player);

    /**
     * Kuemmert sich um alle Actionen jedweiliger Art
     */
    public abstract ActionRequest[] next(ActionResponse as);

}
