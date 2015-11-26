package thegamebrett.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christiancolbach
 */
public class HTMLHelper {

    public static String HTML = null;
    static {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource("thegamebrett/network/assets/index.html");
        try {
            String content = new Scanner(new File(url.toURI())).useDelimiter("\\Z").next();
            HTML = content;
        } catch (URISyntaxException | FileNotFoundException ex) {
            Logger.getLogger(ControlDirector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String CSS = null;
    static {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource("thegamebrett/network/assets/style.css");
        try {
            String content = new Scanner(new File(url.toURI())).useDelimiter("\\Z").next();
            CSS = content;
        } catch (URISyntaxException | FileNotFoundException ex) {
            Logger.getLogger(ControlDirector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String generateHTMLContent(String titel, String[] choices, long messageID) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>" + titel + "</h1>");
        for(int i=0; i<choices.length; i++) {
            sb.append(generateHTMLButton(choices[i], messageID, i));
        }
        return sb.toString();
    }
    
    public static String generateHTMLContent(String titel, String acknowledgment, long messageID) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>" + titel + "</h1>");
        
        return "<h1>" + titel + "</h1><p>" + acknowledgment + "</p>";
    }
    
    private static String generateHTMLButton(String choice, long messageID, int anwerID) {
        return "<div align=\"center\"><button class=\"choices\" onclick=\"reply('" + messageID + "?" + anwerID+ "')\">" + choice + "</button></div>";
    }
}
