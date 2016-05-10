package thegamebrett.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import thegamebrett.assets.AssetsLoader;
import thegamebrett.game.GameCollection;
import thegamebrett.model.GameFactory;
import thegamebrett.usercharacter.UserCharacter;
import thegamebrett.usercharacter.UserCharacterDatabase;

/**
 *
 * @author christiancolbach
 */
public class WebGenerator {
    
    public static final String ON = "ON", OFF = "OFF";
    
    public static String generateHTMLContent(String titel, Object[] choices, long messageID) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>" + titel + "</h1>");
        for(int i=0; i<choices.length; i++) {
            if (choices[i] != null) {
                sb.append(generateHTMLButton(choices[i].toString(), messageID, i));
            }
        }
        return sb.toString();
    }
    
    private static String generateHTMLButton(String choice, long messageID, int anwerID) {
        return "<div align=\"center\"><button class=\"choices\" onclick=\"reply('" + messageID + "?" + anwerID+ "')\">" + choice + "</button></div>";
    }
        
    public static String generateHTMLContent(String titel, String acknowledgment, long messageID) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>" + titel + "</h1>");
        
        return "<h1>" + titel + "</h1><p>" + acknowledgment + "</p>";
    }
    public static String generateUserCharacterChooserAvailabilityInfoForAPI() {
        ArrayList<UserCharacter> cs = UserCharacterDatabase.getUserCharacters();
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for(int i=0; i<cs.size(); i++) {
            
            if(!isFirst) {
                sb.append(" ");
            } else {
                isFirst = false;
            }
            
            if(!cs.get(i).isInUse()) {
                sb.append(ON);
            } else {
                sb.append(OFF);
            }
        }
        return sb.toString();
    }
    
    private static String generateChooseCharacterWebPage() {
        String template = AssetsLoader.loadText_localized_SuppressExceptions("web/chooseCharacter.html");
        return template.replaceFirst("<!--replace-->", generateUserCharacterChooserHTML());
    }

    private static String chooseCharacterWebPageCache = null;
    public static String getChooseCharacterWebPage() {
        if(chooseCharacterWebPageCache == null) {
            chooseCharacterWebPageCache = generateChooseCharacterWebPage();
        }
        return chooseCharacterWebPageCache;
    }
    
    private static HashMap<Integer, File> gameImagesCache = new HashMap<>();
    public static File getGameImage(int i) {
        File f = gameImagesCache.get(i);
        if(f == null) {
            f = AssetsLoader.saveImage("web/temp/gameIcon" + i, GameCollection.imageCache[i]);
            gameImagesCache.put(i, f);
        }
        return f;
    }
    
    
    private static String generateChooseGameWebPage() {
        String template = AssetsLoader.loadText_localized_SuppressExceptions("web/chooseGame.html");
        return template.replaceFirst("<!--replace-->", generateGameChooserHTML());
    }
    
    private static String chooseChooseGameWebPage = null;
    public static String getChooseGameWebPage() {
        if(chooseChooseGameWebPage == null) {
            chooseChooseGameWebPage = generateChooseGameWebPage();
        }
        return chooseChooseGameWebPage;
    }
    
    private static String generateUserCharacterChooserHTML() {
        //<label><input type="radio" name="Avatar" value="Mode"> <img src="avatar1.png" alt="Mode" width="100px" height="100px"></label>
        ArrayList<UserCharacter> cs = UserCharacterDatabase.getUserCharacters();
        
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<cs.size(); i++) {
            sb.append("\n<button class=\"characterButton\" id=\"chooseCharacterButton" + i + "\" onClick=\"tryToGetCharacter(" + i + ")\"><img src=\"avatars/" + cs.get(i).getAvatarName() + "\" alt=\"Character\" class=\"charImage\">" + cs.get(i).getName() + "</button>");
        }
        return sb.toString();
    }
    
    private static String generateGameChooserHTML() {
        GameFactory[] gfs = GameCollection.gameFactorys;
        
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<gfs.length; i++) {
            sb.append("\n<button class=\"gameButton\" id=\"gameButton" + i + "\" onClick=\"tryToCreateGame(" + i + ", '" + gfs[i].getGameName() +"')\"><img src=\"getGameIcon?" + i + "\" alt=\"Game\" class=\"gameImage\">" + gfs[i].getGameName() + "</button>");
        }
        return sb.toString();
    }
    
    public static String generateSystemClientChooserAvailabilityInfoForAPI(UserManager um) {
        
        boolean[] bs = um.canSetSystemClientArray();
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for(int i=0; i<UserManager.SYSTEM_CLIENT_IDS.length; i++) {
            
            if(!isFirst) {
                sb.append(" ");
            } else {
                isFirst = false;
            }
            
            sb.append(um.canSetSystemClient(i) ? ON : OFF);
        }
        
        return sb.toString();
    }

    static String generateFreePositionHTML(UserManager um) {
        StringBuilder sb = new StringBuilder();
        User[] scs = um.getSystemClients();
        for(int i=0; i<scs.length; i++) {
            if(scs[i]!= null && !scs[i].isAlife()) {
                String name = "null";
                try {
                    name = scs[i].getUserCharacter().getName();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                sb.append("<div align=\"center\"><button class=\"choices\" onclick=\"tryToLogIn('" + UserManager.SYSTEM_CLIENT_IDS[i] + "')\">" + name + "</button></div>");
            }
        }
        /*
        for (int i = 0; i < SYSTEM_CLIENT_NAMES.length; i++) {
            if (systemClients[i] == null || !systemClients[i].isAlife()) {
                sb.append("<div align=\"center\"><button class=\"choices\" onclick=\"tryToLogIn('" + SYSTEM_CLIENT_IDS[i] + "')\">" + SYSTEM_CLIENT_NAMES[i] + "</button></div>");
            } else {
                sb.append("<div align=\"center\"><button class=\"choices\" disabled>" + SYSTEM_CLIENT_NAMES[i] + "</button></div>");
            }
        }*/
        return sb.toString();
    }
    
}
