package thegamebrett.network.test;

import thegamebrett.network.httpserver.HttpServer;
import thegamebrett.network.httpserver.RootDirectoryBasedDirector;

/**
 *
 * @author christiancolbach
 */
public class Test {
    public static void main(String[] args) {
        HttpServer server = new HttpServer(8112, new ControlDirector());
        server.enableServer();
    }
}
