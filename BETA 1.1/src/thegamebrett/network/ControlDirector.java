package thegamebrett.network;

import thegamebrett.network.httpserver.Director;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.assets.AssetNotExistsException;
import thegamebrett.assets.AssetsLoader;

/**
 * @author Christian Colbach
 */
public class ControlDirector implements Director {

    UserManager clientManager;
    
    /**  Rueckgabe von Server wenn kein Update noetig ist */
    public static final String NO_UPDATES = "null";
    
    /**  diese Id wird uebergeben um anzugeben dass keine ID verfuegbar ist */
    public static final String NO_MESSAGE_ID = "#########";
    
    /**  diese Id wird uebergeben um anzugeben dass keine Farbe verfuegbar ist */
    public static final String NO_COLOR_SET = "NONE";
    
    public ControlDirector(UserManager clientManager) {
        this.clientManager = clientManager;
    }
    
    @Override
    public Object query(String request, Socket clientSocket) throws QueryException {
        System.out.println(request);
        User client = clientManager.getOrAddClientForInetAddress(clientSocket.getInetAddress());
        
        if (request.equals("/") || request.equals("/index.html")) {
            if(!clientManager.isSystemClient(client)) {
                return "<html><head><meta http-equiv=\"refresh\" content=\"0; URL=choosePosition.html\"></head><body></body></html>";
            }
            return "Test";

            //return HTMLHelper.HTML;
        } else if (request.equals("/style.css")) {
            return HTMLHelper.CSS;
        } else if (request.startsWith("/getUserColor")) {
            if(client.hasUserCharacter()) {
                String c = client.getUserCharacter().getColorString();
                if(c != null) {
                    return c;
                }
            }
            return NO_COLOR_SET;
        } else if (request.startsWith("/tryToChoosePosition")) {
            boolean gotIt = clientManager.tryToSetSystemClient(request.substring("/tryToChoosePosition?".length()), client);
            System.out.println(gotIt ? "YES" : "NO");
            return gotIt ? "YES" : "NO";
        } else if (request.startsWith("/choosePosition.html")) {
            return AssetsLoader.loadFileIgnoreExceptions("web/choosePosition.html");
        } else if (request.startsWith("/refreshPositions")) {
            return NO_MESSAGE_ID + clientManager.getHTMLSystemClientChoser();
        } else if (request.startsWith("/refresh")) {
            if(!clientManager.isSystemClient(client)) {
                return NO_MESSAGE_ID + clientManager.getHTMLSystemClientChoser();
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
                    return NO_MESSAGE_ID + "connected";
                } else {
                    // Kontrolle nach Updates
                    if(ir.matchMessageId(request.substring("/refresh?".length()))) {
                        return NO_UPDATES;
                    } else {
                        return ir.getMessageIdAs9CharacterString() + HTMLHelper.generateHTMLContent(ir.getTitel(), ir.getChoices(), messageID);
                    }
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
        } else if (request.equals("/jquery.min.js")) {
            try {
                return AssetsLoader.loadFile("web/jquery.min.js");
            } catch (AssetNotExistsException ex) {
                Logger.getLogger(ControlDirector.class.getName()).log(Level.SEVERE, null, ex);
                return "Fataler Fehler!!! jquery.min.js kann nicht geladen werden.";
            }
        } else if (request.equals("/functions.js")) {
            return AssetsLoader.loadFileIgnoreExceptions("web/functions.js");
        } else if(AssetsLoader.fileExists("web/" + request.substring(1))){
            return AssetsLoader.loadFileIgnoreExceptions("web/" + request.substring(1));
        } else {
            return "Fehler :(";
        } 
        
        
    }
    

}
