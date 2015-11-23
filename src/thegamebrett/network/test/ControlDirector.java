package thegamebrett.network.test;

import java.net.URL;
import thegamebrett.network.HTMLGenerator;
import thegamebrett.network.httpserver.Director;
import java.io.*;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 * @author Christian Colbach
 */
public class ControlDirector implements Director {
    
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
    
    @Override
    public Object query(String request) throws QueryException {

        if (request.equals("/") || request.equals("/index.html")) {
            return HTML;
        } else if (request.equals("/style.css")) {
            return CSS;
        } else if (request.startsWith("/refresh")) {
            return HTMLGenerator.generateHTMLContent("Wie geht es dir?", new String[]{"gut", "schlecht", "es geht"}, 1001);

            //return Test.usertext;
        } else if (request.startsWith("/reply")) {
            
            
            return "Thanks " + Math.random();
        } else {
            return "Fehler :(";
        }
    }

}
