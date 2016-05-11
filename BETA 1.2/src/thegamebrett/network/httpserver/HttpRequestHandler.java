package thegamebrett.network.httpserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
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
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
            Scanner scanner = null;
            try {
                scanner = new Scanner(
                        new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream())
                        ).readLine()
                );
                
            } catch (Exception e) {
                System.err.print("Korrupter Input Stream (" + e.getClass() + ")");
                return;
            }
            
            method = scanner.next();
            request = scanner.next();

            if (method.equals("GET")) {

                try {
                    
                    Object instruction = director.query(request, clientSocket);
                    if (instruction instanceof String) {
                        HttpSender.sendText(HttpResponseHeader.STATUSCODE_OK, (String) instruction, /**/ Mimes.getMime(request)/**/, outputStream);

                    } else if (instruction instanceof File) {
                        HttpSender.sendFile((File) instruction, outputStream);
                    }
                    
                } catch (Director.QueryException qe) {
                    Object errorDocument = qe.getErrorDocument();
                    int statusCode = qe.getStatusCode();

                    if (errorDocument instanceof String) {
                        HttpSender.sendText(statusCode, (String) errorDocument, outputStream);

                    } else if (errorDocument instanceof File) {
                        HttpSender.sendFile(statusCode, (File) errorDocument, outputStream);
                    }
                }
                
            } else {
                HttpSender.sendText(HttpResponseHeader.STATUSCODE_INTERNAL_SERVER_ERROR, "<b> Error 500 (Internal Server Error) </b>", outputStream);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
