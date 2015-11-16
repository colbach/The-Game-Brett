package thegamebrett.network.httpserver;

/**
 * @author Christian Colbach
 */
public interface Director {

	public abstract class QueryException extends Exception {
		public abstract int getStatusCode();
		public abstract Object getErrorDocument();
	}

	/**
	 * Returns 'File'-Object for file-transfer, 'String'-Object for html-transfer or "Integer" (show Results).
	 *          Other Objects are (at this time) not allowed as return value.
	 * @param request request-String received by client
     */
	public abstract Object query(String request) throws QueryException;
	
}