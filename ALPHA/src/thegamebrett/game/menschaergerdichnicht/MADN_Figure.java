
package thegamebrett.game.menschaergerdichnicht;

import thegamebrett.model.Layout;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Figure;

/**
 * @author Koré
 */
public class MADN_Figure extends Figure{
    
    private String description;
    
    public MADN_Figure(Player owner, Layout layout, String description) {
        super(owner, layout, 0.05f, 0.05f);
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
    
}
