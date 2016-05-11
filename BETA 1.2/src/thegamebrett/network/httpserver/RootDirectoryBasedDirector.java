package thegamebrett.network.httpserver;

import java.io.File;
import java.net.Socket;
import java.net.URLDecoder;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class RootDirectoryBasedDirector implements Director {

    public class NotFoundException extends QueryException {

        @Override
        public int getStatusCode() {
            return HttpResponseHeader.STATUSCODE_NOT_FOUND;
        }

        @Override
        public Object getErrorDocument() {
            return "<b> Error 404 (Not Found) </b>";
        }
    }

    private String rootDirectory;

    public RootDirectoryBasedDirector(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public Object query(String request, Socket clientSocket) throws QueryException {
        if (request.equals("/")) {
            if (new File(rootDirectory + "/index.html").exists()) {
                return new File(rootDirectory + "index.html");
            } else {
                return "<center> <h1> Hello here is Game Brett ! </h1> </center>";
            }
        } else {
            File file = new File(rootDirectory + URLDecoder.decode(request));
            if (new File(rootDirectory + request).isFile()) {
                return file;
            } else {
                throw new NotFoundException();
            }
        }
    }
}