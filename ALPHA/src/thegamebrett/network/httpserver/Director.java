package thegamebrett.network.httpserver;

import java.net.Socket;

/**
 * @author Christian Colbach
 */
public interface Director {

	public abstract class QueryException extends Exception {
		public abstract int getStatusCode();
		public abstract Object getErrorDocument();
	}

	/**
	 * Gibt 'File'-Object fuer File-transfer, 'String'-Object fuer html-transfer oder "Integer" (siehe Results) zuruek.
         */        
	public abstract Object query(String request, Socket clientSocket) throws QueryException;
	
}