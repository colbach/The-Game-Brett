package thegamebrett.usercharacter;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import thegamebrett.assets.AssetNotExistsException;
import thegamebrett.assets.AssetsLoader;

/**
 * @author christiancolbach
 */
public class UserCharacter {
    
    public static final String[] USER_COLORS = {
        "#FFFFFF", "#C0C0C0", "#808080", "#000000", "#FF0000",
        "#800000", "#FFFF00", "#808000", "#00FF00", "#008000",
        "#00FFFF", "#008080", "#0000FF", "#000080", "#FF00FF",
        "#800080"
    };
    
    public static final String[] USER_AVATARS_NAMES = AssetsLoader.listImagesInFolder("avatars").toArray(new String[0]);
    
    private static boolean[] userColerRegister = new boolean[USER_COLORS.length];
    private static ArrayList<String> userNameRegister = new ArrayList<>(USER_COLORS.length);
    
    private String name; // Name des Spielers
    
    private int colorIndex; // Spielerfarbe
    
    private String avatarName; // Referenz auf Bild auf PC

    public UserCharacter(String name, int colorIndex, String avatar) {
        setColor(colorIndex);
        this.name = name;
        this.avatarName = avatar;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        synchronized(userNameRegister) {
            if(userNameRegister.contains(name)) {
                throw new IllegalArgumentException("Name schon in Verwendung!");
            }
            userNameRegister.add(name);
        }
        this.name = name;
    }

    public Color getColor() {
        return Color.web(USER_COLORS[colorIndex]);
    }

    public void setColor(int colorIndex) {
        synchronized(userColerRegister) {
            if(userColerRegister[colorIndex]) {
                throw new IllegalArgumentException("Farbe schon in Verwendung!");
            }
            userColerRegister[colorIndex] = true;
        }
        this.colorIndex = colorIndex;
    }

    public String getAvatarName() {
        return avatarName;
    }
    
    public Image getAvatar() {
        try {
            return AssetsLoader.loadImage("avatars/"+ avatarName);
        } catch (AssetNotExistsException ex) {
            Logger.getLogger(UserCharacter.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Datei existiert nicht");
        }
    }

    public void setAvatar(String avatarName) {
        this.avatarName = this.avatarName;
    }
    
    public String toCSV() {
        return name + ";" + colorIndex + ";" + avatarName; 
    }
    
    public static UserCharacter fromCSV(String csv) {
        String[] e = csv.split(";");
        return new UserCharacter(e[0], Integer.valueOf(e[1]), e[2]);
    }
    
}
