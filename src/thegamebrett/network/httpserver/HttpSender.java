package thegamebrett.network.httpserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;


/**
 * @author Christian Colbach
 */
public class HttpSender {

	protected static void sendFile(File file, DataOutputStream outputStream) throws Exception {
		sendFile(HttpResponseHeader.STATUSCODE_OK, file, outputStream);
	}

	protected static void sendFile(int statusCode, File file, DataOutputStream outputStream) throws Exception {
		
		// open FileInputStream
		FileInputStream fis = new FileInputStream(file);
		
		// send Header
		outputStream.write(HttpResponseHeader.generateResponseHeader(statusCode, Mimes.getMime(file), fis.available()));

		// send file [
		byte[] buffer = new byte[1024];
		int bytesRead;
		while((bytesRead = fis.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}
		fis.close();
		outputStream.close();
		// ]
		
	}
	
	protected static void sendText(int statusCode, String text, DataOutputStream outputStream) throws Exception {

		outputStream.write(HttpResponseHeader.generateResponseHeader(statusCode, "text/html", text.length()));
		outputStream.write(text.getBytes("UTF-8"));
		outputStream.close();
	}
}
