package thegamebrett.network.test;

import thegamebrett.network.HTMLGenerator;
import thegamebrett.network.httpserver.Director;

/**
 * @author Christian Colbach
 */
public class ControlDirector implements Director {

    @Override
    public Object query(String request) throws QueryException {
        System.out.println(request);
        
        if(request.equals("/") || request.equals("/index.html")) {
            return HTMLGenerator.generateHTML(null, null);
        } else if(request.equals("/style.css")) {
            return HTMLGenerator.CSS;
        } else if(request.startsWith("/refresh")) {
            return "" + Math.random();
        } else {
            return "Fehler :(";
        }
    }
    
}
