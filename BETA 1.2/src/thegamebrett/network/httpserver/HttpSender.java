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

        // FileInputStream oeffnen
        FileInputStream fis = new FileInputStream(file);

        // schreibt Header
        outputStream.write(HttpResponseHeader.generateResponseHeader(statusCode, Mimes.getMime(file), fis.available()));

        // Datei senden [
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        fis.close();
        outputStream.close();
	// ]

    }

    protected static void sendText(int statusCode, String text, DataOutputStream outputStream) throws Exception {

        sendText(statusCode, text, "text/html", outputStream);
    }

    protected static void sendText(int statusCode, String text, String mime, DataOutputStream outputStream) throws Exception {
        byte[] bytes = text.getBytes("UTF-8");
        outputStream.write(HttpResponseHeader.generateResponseHeader(statusCode, mime, /*text.length()*/bytes.length));
        outputStream.write(bytes);
        outputStream.close();
    }
}
