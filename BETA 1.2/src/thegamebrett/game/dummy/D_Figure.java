
package thegamebrett.game.dummy;

import thegamebrett.model.Layout;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Figure;

public class D_Figure extends Figure{
    
    private String description;
    
    public D_Figure(Player owner, Layout layout) {
        super(owner, layout, 0.06f / D_Board.RATIO_X, 0.06f);
        this.description = description;
    }

    @Override
    public String toString() {
        return "Figur";
    }
    
}