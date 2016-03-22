package thegamebrett.network.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Christian Colbach
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

        } // can't open ServerSocket
        catch (IOException ioe) {

            // log [
            ioe.printStackTrace();
            System.err.println("(Socket is probably already in use)");
			//Logger.log(ioe, "-> Socket is probably already in use!");
            // ]

        }
    }

    @Override
    public void run() {

        while (true) {

            try {

                // Warten auf naechsten Zugriff
                Socket client = serverSocket.accept();
                System.out.println("*");

                // Zugriff mittels Handler abarbeiten
                new HttpRequestHandler(client, director).start();

            } // Error at accepting Request
            catch (IOException ioe) {

                // log [
                ioe.printStackTrace();
				//Logger.log(ioe);
                // ]

            }
        }

    }

    public void closeServerSocket() {

        if (serverSocket != null && !serverSocket.isClosed()) {

            try { // try to close ServerSocket
                serverSocket.close();

            } // can't close ServerSocket
            catch (IOException ioe) {

                // log [
                ioe.printStackTrace();
				//Logger.log(ioe);
                // ]
            }
        }
    }

}
