package thegamebrett.network.test;

import thegamebrett.network.HTMLGenerator;
import thegamebrett.network.httpserver.Director;

/**
 * @author Christian Colbach
 */
public class ControlDirector implements Director {

    @Override
    public Object query(String request) throws QueryException {
        
        if(request.equals("/") || request.equals("/index.html")) {
            return HTMLGenerator.generateHTML(null, null);
        } else if(request.equals("/style.css")) {
            return HTMLGenerator.CSS;
        } else if(request.startsWith("/refresh")) {
            return Test.usertext;
        } else if(request.startsWith("/reply")) {
            return "Thanks " + Math.random();
        } else {
            return "Fehler :(";
        }
    }
    
}
