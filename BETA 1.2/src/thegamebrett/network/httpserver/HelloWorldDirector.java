package thegamebrett.network.httpserver;

import java.net.Socket;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class HelloWorldDirector implements Director {

    public Object query(String request) {
        return "Hello World!";
    }

    @Override
    public Object query(String request, Socket clientSocket) throws QueryException {
        return query(request);
    }

}
