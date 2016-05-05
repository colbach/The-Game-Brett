package thegamebrett.network.httpserver;

import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author Christian Colbach
 */
public class HttpServer {

    public HttpServer(final int port, final Director director) {
        this.port = port;
        this.director = director;
    }

    private Thread requestWaiterThread;
    private HttpRequestWaiter requestWaiter;

    private int port;
    private final Director director;

    public void setPort(int port) {
        this.port = port;

        if (isServerEnabled()) { // update state
            requestWaiterThread.stop();
            requestWaiter.closeServerSocket();
            requestWaiter.serverPort = port;
            requestWaiterThread = new Thread(requestWaiter);
            requestWaiterThread.start();
        }
    }

    public int getPort() {
        return port;
    }

    public boolean isServerEnabled() {

        return requestWaiterThread != null;
    }

    public void enableServer() {

        if (!isServerEnabled()) { // create & start RequestHandler

            requestWaiter = new HttpRequestWaiter(director, port);
            requestWaiterThread = new Thread(requestWaiter);
            requestWaiterThread.start();

            // log [
            System.out.println("Server started (" + new Date().toString() + ")");
			//Logger.log(Logger.LOGTYPE_NOTE, "Server started");

            String localHost = "Unknown";
            try {
                localHost = java.net.InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException uhe) {
                System.out.println("Networkcard not active or not found!");
                uhe.printStackTrace();
            }

            System.out.println("webserver local available under: " + localHost + ":" + port);
			//Logger.log(Logger.LOGTYPE_NOTE, "webserver local available under: " + SystemInformation.getLocalHost() + ":" + port);
            // ]

        } // it is already enabled
        else {
            // log [
            System.err.println("Cannot enable Server because it is already enabled");
			//Logger.log(Logger.LOGTYPE_WARNING, "Cannot enable Server because it is already enabled");
            // ]
        }
    }

    public void disableServer() {

        if (isServerEnabled()) { // stop & destroy RequestHandler

            requestWaiterThread.stop();
            requestWaiter.closeServerSocket();
            requestWaiterThread = null;
            requestWaiter = null;

            // log [
            System.out.println("Server stopped (" + new Date().toString() + ")");
            //Logger.log(Logger.LOGTYPE_NOTE, "Server stopped");
			// ]

        } // it is already disabled
        else {
            // log [
            System.err.println("Cannot disable Server because it is already disabled");
            //Logger.log(Logger.LOGTYPE_WARNING, "Cannot disable Server because it is already disabled");
            // ]
        }

    }

}
