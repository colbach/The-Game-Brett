
package thegamebrett.game.MADN;

import thegamebrett.model.Layout;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Figure;

/**
 * @author Koré
 */
public class MADN_Figure extends Figure{
    
    private String description;
    
    public MADN_Figure(Player owner, Layout layout, String description) {
        super(owner, layout,1/15,1/15);
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
    
}
