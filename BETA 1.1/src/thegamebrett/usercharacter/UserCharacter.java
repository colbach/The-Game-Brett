package thegamebrett.usercharacter;

import javafx.scene.paint.Color;

/**
 * @author christiancolbach
 */
public class UserCharacter {
    
    public String name; // Name des Spielers
    
    public Color color; // Spielerfarbe
    
    public String avatar; // Referenz auf Bild auf PC

    public UserCharacter(String name, Color color, String avatar) {
        this.name = name;
        this.color = color;
        this.avatar = avatar;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
}
