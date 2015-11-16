package thegamebrett.network.httpserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Christian Colbach
 */
public class HttpRequestHandler extends Thread {

	private final Director director;
	private final Socket clientSocket;
		
	private DataOutputStream outputStream;

	private String method;
	private String request;

	HttpRequestHandler(final Socket clientSocket, final Director director) {
		this.clientSocket = clientSocket;
		this.director = director;
	}

	public void run() {
		
		try {
			
			// initialize Stream for outgoing bytes
			outputStream = new DataOutputStream(clientSocket.getOutputStream());

			// scan Request [
			Scanner scanner = new Scanner(
				new BufferedReader(
					new InputStreamReader( clientSocket.getInputStream() )
				).readLine()
			);
			method = scanner.next();
			request = scanner.next();
			
			// ]
			
			// log
			//Logger.log(Logger.LOGTYPE_QUERY, "ip=" + InetAddressFormatter.formatAddress(clientSocket.getInetAddress()) + " query=" + request.substring(1));
			
			if(method.equals("GET")) {
				
				try {
					
					Object instruction = director.query(request);
					
					if(instruction instanceof String) {
					
						HttpSender.sendText(HttpResponseHeader.STATUSCODE_OK, (String) instruction, outputStream);

					} else if(instruction instanceof File) {

						HttpSender.sendFile((File) instruction, outputStream);

					}
					
				} catch(Director.QueryException qe) {
					
					Object errorDocument = qe.getErrorDocument();
					int statusCode = qe.getStatusCode();
					
					if(errorDocument instanceof String) {
					
						HttpSender.sendText(statusCode, (String) errorDocument, outputStream);

					} else if(errorDocument instanceof File) {

						HttpSender.sendFile(statusCode, (File) errorDocument, outputStream);

					}
				}
				
				
				/*
				
				if(instruction == Director.RESULT_ACCESS_DENIED) {
					
					HttpSender.sendText(HttpResponseHeader.STATUSCODE_BAD_FORBIDDEN, "<b> Error 403 (Access denied) </b>", outputStream);
					
				} else if(instruction instanceof String) {
					
					HttpSender.sendText(HttpResponseHeader.STATUSCODE_OK, (String) instruction, outputStream);
					
				} else if(instruction instanceof File) {
					
					HttpSender.sendFile((File) instruction, outputStream);
					
				} else if(instruction == Director.RESULT_NOT_FOUND) {
					
					HttpSender.sendText(HttpResponseHeader.STATUSCODE_NOT_FOUND, "<b> Error 404 (Not Found) </b>", outputStream);
					
				} else {
					
					HttpSender.sendText(HttpResponseHeader.STATUSCODE_INTERNAL_SERVER_ERROR, "<b> Error 500 (Internal Server Error) </b>", outputStream);
					
				}
				
				
				
				
				/*
				if(request.equals("/")) {

					if(new File(rootDirectory + "/index.html").exists()) {
						HttpSender.sendFile(new File(rootDirectory + "index.html"), outputStream);
					} else {
						
						HttpSender.sendText(HttpResponseHeader.STATUSCODE_OK, "<center> <h1> hello world ! </h1> <h2> @ Christian </h2> </center>", outputStream);
					}
					outputStream.close();
					
				} else {
					
					String fileName = URLDecoder.decode(request);
					System.out.println(rootDirectory + fileName);
					if(new File(rootDirectory + fileName).isFile()) {
						HttpSender.sendFile(new File(rootDirectory + fileName), outputStream);
					} else {
						HttpSender.sendText(HttpResponseHeader.STATUSCODE_NOT_FOUND, "<b> Error 404 (File not found) </b>", outputStream);
					}
				}
				*/
				
			} else { // function is not supported
				
				HttpSender.sendText(HttpResponseHeader.STATUSCODE_INTERNAL_SERVER_ERROR, "<b> Error 500 (Internal Server Error) </b>", outputStream);
			}
			
		} catch(Exception e) {
			
                        e.printStackTrace();
			//Logger.log(e, "ip=" + InetAddressFormatter.formatAddress(clientSocket.getInetAddress()) + " query=" + request);
		}
	}
}
