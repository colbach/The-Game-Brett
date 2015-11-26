package thegamebrett.network;

import java.net.URL;
import thegamebrett.network.HTMLHelper;
import thegamebrett.network.httpserver.Director;
import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.network.httpserver.HttpResponseHeader;

/**
 * @author Christian Colbach
 */
public class ControlDirector implements Director {

    ClientManager clientManager;
    
    ControlDirector(ClientManager clientManager) {
        this.clientManager = clientManager;
    }
    
    
    
    @Override
    public Object query(String request, Socket clientSocket) throws QueryException {
        
        Client client = clientManager.getOrAddClientForInetAddress(clientSocket.getInetAddress());
        
        if (request.equals("/") || request.equals("/index.html")) {
            return HTMLHelper.HTML;
        } else if (request.equals("/style.css")) {
            return HTMLHelper.CSS;
        } else if (request.startsWith("/refresh?start")) {
            // messageID und InteractionRequest sicher aus client laden
            long messageID;
            InteractionRequest ir;
            do {
                messageID = client.getMessageId();
                ir = client.getActualInteractionRequest();
            } while(messageID != client.getMessageId());
            // ausgabe
            if(ir == null) {
                return "connected";
            } else {
                
                return HTMLHelper.generateHTMLContent(ir.getTitel(), ir.getChoices(), messageID);
            }
        } else if (request.startsWith("/refresh")) {
            // messageID und InteractionRequest sicher aus client laden
            long messageID;
            InteractionRequest ir;
            do {
                messageID = client.getMessageId();
                ir = client.getActualInteractionRequest();
            } while(messageID != client.getMessageId());
            // ausgabe
            if(ir == null) {
                return "connected";
            } else {
                return HTMLHelper.generateHTMLContent(ir.getTitel(), ir.getChoices(), messageID);
            }
        } else if (request.startsWith("/reply")) {
            System.out.println(request);
            return null;
            //return "Thanks " + Math.random();
        } else {
            return "Fehler :(";
        }
        
        
    }
    

}
