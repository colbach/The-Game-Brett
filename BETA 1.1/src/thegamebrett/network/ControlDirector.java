package thegamebrett.network;

import thegamebrett.network.HTMLHelper;
import thegamebrett.network.httpserver.Director;
import java.net.Socket;
import thegamebrett.action.request.InteractionRequest;

/**
 * @author Christian Colbach
 */
public class ControlDirector implements Director {

    UserManager clientManager;
    
    public ControlDirector(UserManager clientManager) {
        this.clientManager = clientManager;
    }
    
    @Override
    public Object query(String request, Socket clientSocket) throws QueryException {
        System.out.println(request);
        User client = clientManager.getOrAddClientForInetAddress(clientSocket.getInetAddress());
        
        if (request.equals("/") || request.equals("/index.html")) {
            return HTMLHelper.HTML;
        } else if (request.equals("/style.css")) {
            return HTMLHelper.CSS;
        /*} else if(request.startsWith("/refresh?start")) {
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
            }*/
        } else if (request.startsWith("/refresh")) {
            if(!clientManager.isSystemClient(client)) {
                return clientManager.getHTMLSystemClientChoser();
            } else {
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
            }
        } else if (request.startsWith("/reply")) {
            if(request.startsWith("/reply?login:")) {
                try {
                    clientManager.tryToSetSystemClient(request.substring("/reply?login:".length()), client);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                int messageID = Integer.valueOf(request.substring("/reply?".length(), request.lastIndexOf("?")));
                int answerID = Integer.valueOf(request.substring(request.lastIndexOf("?")+1));
                client.replyFromHTTP(messageID, answerID);
            }
            return ":)";
        } else {
            return "Fehler :(";
        }
        
        
    }
    

}
