package thegamebrett.game;

import thegamebrett.game.KFSS.KFSS_GameFactory;
import thegamebrett.game.MADN.MADN_GameFactory;
import thegamebrett.game.PSS.PSS_GameFactory;
import thegamebrett.game.dummy.D_GameFactory;
import thegamebrett.model.GameFactory;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class GameCollection {

    /**
     * Hier muessen alle Spiele(gameFactorys) eingetragen werden
     */
    public static GameFactory[] gameFactorys = {
        new D_GameFactory(), new PSS_GameFactory(), new KFSS_GameFactory(), new MADN_GameFactory(), new D_GameFactory(), new D_GameFactory(), new D_GameFactory()
    };

}
