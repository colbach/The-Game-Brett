package thegamebrett.network.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class HttpRequestWaiter implements Runnable {

    private ServerSocket serverSocket;

    private final Director director;
    protected int serverPort;

    public HttpRequestWaiter(final Director director, final int serverPort) {
        this.director = director;
        this.serverPort = serverPort;

        try {
            serverSocket = new ServerSocket(serverPort);

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.err.println("(Socket is probably already in use)");

        }
    }

    @Override
    public void run() {

        while (true) {

            try {
                Socket client = serverSocket.accept();
                new HttpRequestHandler(client, director).start();
            }
            catch (IOException ioe) {

                ioe.printStackTrace();
            }
        }

    }

    public void closeServerSocket() {

        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}
