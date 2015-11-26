package thegamebrett.network.test;

import thegamebrett.network.*;
import java.net.URL;
import thegamebrett.network.HTMLHelper;
import thegamebrett.network.httpserver.Director;
import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import thegamebrett.network.httpserver.HttpResponseHeader;

/**
 * @author Christian Colbach
 */
public class ControlDirector implements Director {

    
    
    
    @Override
    public Object query(String request, Socket clientSocket) throws QueryException {
        
        if (request.equals("/") || request.equals("/index.html")) {
            return HTMLHelper.HTML;
        } else if (request.equals("/style.css")) {
            return HTMLHelper.CSS;
        } else if (request.startsWith("/refresh")) {
            //return HTMLHelper.generateHTMLContent("Wie geht es dir?", new String[]{"gut", "schlecht", "es geht"}, 1001);
            return Test.usertext;
            //return Test.usertext;
        } else if (request.startsWith("/reply")) {
            System.out.println(request);
            return null;
            //return "Thanks " + Math.random();
        } else {
            return "Fehler :(";
        }
        
        
    }
    

}
