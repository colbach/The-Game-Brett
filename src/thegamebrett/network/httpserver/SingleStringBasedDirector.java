package thegamebrett.network.httpserver;

import java.net.Socket;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class SingleStringBasedDirector implements Director {

    private String html;

    public SingleStringBasedDirector(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Override
    public Object query(String request, Socket clientSocket) throws QueryException {
        return html;
    }
}