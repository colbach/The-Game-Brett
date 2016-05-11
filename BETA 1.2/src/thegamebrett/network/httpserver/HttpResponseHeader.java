package thegamebrett.network.httpserver;

import java.util.Date;


/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class HttpResponseHeader {

	public static int STATUSCODE_OK = 200;
	public static int STATUSCODE_NOT_MODIFIED = 304;
	public static int STATUSCODE_BAD_REQUEST = 400;
	public static int STATUSCODE_BAD_FORBIDDEN = 403;
	public static int STATUSCODE_NOT_FOUND = 404;
	public static int STATUSCODE_INTERNAL_SERVER_ERROR = 500;
	public static int STATUSCODE_NOT_IMPLEMENTED = 501;
	public static int STATUSCODE_GATEWAY_TIMEOUT = 504;

	public static String generateResponseHeaderString(int statusCode, String contentType, long contentLength) {	
		
		return "HTTP/1.1 " + statusCode + " OK\r\n" + "Date: " + new Date().toString() + "\r\n"
			+ "Content-Type: " + contentType + "\r\n"
			+ ((contentLength != -1) ? "Content-Length: " + contentLength + "\r\n" : "")
			+ "\r\n";
	}
	
	protected static byte[] generateResponseHeader(int statusCode, String contentType, long contentLength) {
		
		return generateResponseHeaderString(statusCode, contentType, contentLength).getBytes();
	}
	
}
