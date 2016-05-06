package thegamebrett.usercharacter;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import thegamebrett.assets.AssetNotExistsException;
import thegamebrett.assets.AssetsLoader;
import thegamebrett.gui.UserImageCircle;
import thegamebrett.model.Layout;

/**
 * @author christiancolbach
 */
public class UserCharacter {
    
    private boolean inUse = false;
    
    private String name; // Name des Spielers
    
    private String color; // Spielerfarbe
    
    private String avatarFileName; // Dateinamen von Datei
    
    private Layout layout;

    public UserCharacter(String name, String color, String avatarFileName) {
        this.name = name;
        this.color = color;
        this.avatarFileName = avatarFileName;
        
    }
    
    public Layout getLayout() {
        if(layout == null) {
            layout = new Layout();
            layout.setIconImage(getAvatar());
            layout.setBorder(4);
            layout.setBorderColor(getFXColor());
        }
        return layout;
    }
    
    private UserImageCircle userImageCircleCache;
    public UserImageCircle getUserImageCircle() {
        if(userImageCircleCache == null) {
            userImageCircleCache = new UserImageCircle(getAvatar(), getFXColor());
        }
        return userImageCircleCache;
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
