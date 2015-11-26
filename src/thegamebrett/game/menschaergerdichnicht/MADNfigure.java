
package thegamebrett.game.menschaergerdichnicht;

import thegamebrett.model.Layout;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Figure;

/**
 * @author Koré
 */
public class MADNfigure extends Figure{
    
    private String description;
    
    public MADNfigure(Player owner, Layout layout, String description) {
        super(owner, layout);
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
    
}
