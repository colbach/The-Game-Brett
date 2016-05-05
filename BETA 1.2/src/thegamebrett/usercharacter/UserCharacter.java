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
    
    /*public static ArrayList<String> getAvaibleColors() {
        ArrayList<String> cs = new ArrayList<String>();
        synchronized(userColerRegister) {
            for(int i=0; i<USER_COLORS.length; i++) {
                if(!userColerRegister[i])
                    cs.add(USER_COLORS[i]);
            }
        }
        return cs;
    }*/
    
    /*public static final String[] USER_COLORS = {
        "#FFFFFF", "#C0C0C0", "#808080", "#000000", "#FF0000",
        "#800000", "#FFFF00", "#808000", "#00FF00", "#008000",
        "#00FFFF", "#008080", "#0000FF", "#000080", "#FF00FF",
        "#800080"
    };*/
    
    //public static final String[] USER_AVATARS_NAMES = AssetsLoader.listImagesInFolder("avatars").toArray(new String[0]);
    
    //private static boolean[] userColerRegister = new boolean[USER_COLORS.length];
    //private static ArrayList<String> userNameRegister = new ArrayList<>(USER_COLORS.length);
    
    private boolean inUse = false;
    
    private String name; // Name des Spielers
    
    private String color; // Spielerfarbe
    
    private String avatarFileName; // Dateinamen von Datei 

    public UserCharacter(String name, String color, String avatarFileName) {
        this.name = name;
        this.color = color;
        this.avatarFileName = avatarFileName;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getFXColor() {
        return Color.web(color);
    }
    
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAvatarName() {
        return avatarFileName;
    }
    
    public Image getAvatar() {
        try {
            return AssetsLoader.loadImage("avatars/"+ avatarFileName);
        } catch (AssetNotExistsException ex) {
            Logger.getLogger(UserCharacter.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Datei existiert nicht");
        }
    }

    public void setAvatar(String avatarName) {
        this.avatarFileName = this.avatarFileName;
    }

    public synchronized boolean isInUse() {
        return inUse;
    }

    public synchronized void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
    
    public synchronized boolean tryToUse() {
        if(!isInUse()) {
            setInUse(true);
            return true;
        } else {
            return false;
        }
    }
    
    public String toCSV() {
        return name + ";" + color + ";" + avatarFileName; 
    }
    
    public static UserCharacter fromCSV(String csv) {
        try {
            if (csv == null) {
                throw new IllegalArgumentException();
            }
            String[] e = csv.split(";");
            if (e.length != 3) {
                throw new IllegalArgumentException();
            }
            return new UserCharacter(e[0].trim(), e[1].trim(), e[2].trim());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }
    
}
