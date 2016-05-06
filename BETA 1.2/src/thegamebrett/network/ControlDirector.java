package thegamebrett.network;

import thegamebrett.network.httpserver.Director;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.assets.AssetNotExistsException;
import thegamebrett.assets.AssetsLoader;
import thegamebrett.game.GameCollection;
import thegamebrett.model.GameFactory;
import thegamebrett.usercharacter.UserCharacter;
import thegamebrett.usercharacter.UserCharacterDatabase;

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
    
    public Object direct(User u) {
        int webPage = u.getWebPage();
            
        if(webPage == User.WEB_PAGE_CHOOSE_POSITION) {
            return AssetsLoader.loadFileIgnoreExceptions("web/choosePosition.html");
        } else if(webPage == User.WEB_PAGE_CHOOSE_CHARACTER) {
            return WebGenerator.getChooseCharacterWebPage();
        } else if(webPage == User.WEB_PAGE_CHOOSE_GAME) {
            return WebGenerator.getChooseGameWebPage();
        } else if(webPage == User.WEB_PAGE_WAIT) {
            return "noch nicht implementiert (Warten)";
        } else if(webPage == User.WEB_PAGE_PLAY_GAME) {
            return AssetsLoader.loadFileIgnoreExceptions("web/ingame.html");
        } else if(webPage == User.WEB_PAGE_GAME_ALREADY_STARTED) {
            return AssetsLoader.loadFileIgnoreExceptions("web/gameAlreadyStarted.html");
        } else if(webPage == User.WEB_PAGE_PREFERENCES) {
            return "noch nicht implementiert (Einstellungen)";
        } else if(webPage == User.WEB_PAGE_START_GAME) {
            return AssetsLoader.loadFileIgnoreExceptions("web/startGame.html");
        } else if(webPage == User.WEB_PAGE_JOIN_GAME) {
            return AssetsLoader.loadFileIgnoreExceptions("web/joinGame.html");
        } else {
            return "Fehler (Ungueltiger Wert)";
        }
    }
    
    @Override
    public Object query(String request, Socket clientSocket) throws QueryException {
        System.out.println(request);
        User client = clientManager.getOrAddClientForInetAddress(clientSocket.getInetAddress());
        
        if (request.equals("/") || request.equals("/index.html")) {
            return direct(client);
        } if (request.startsWith("/tryToStartGame")) {
            NetworkGameSelector ngs = clientManager.getManager().getMobileManager().getNetworkManager().getNetworkGameSelector();
            boolean b = ngs.tryToStart();
            if(b) {
                return "YES";
            } else {
                return "NO";
            }
        } else if (request.equals("/logOut")) {
            clientManager.logOutSystemClient(client);
            client.removeUserCharacter();
            return direct(client);
        } else if (request.startsWith("/getUserColor")) {
            if(client.hasUserCharacter()) {
                String c = client.getUserCharacter().getColor();
                if(c != null) {
                    return c;
                }
            }
            return NO_COLOR_SET;
        } else if (request.startsWith("/refreshStartGameInfoText")) {
            return clientManager.getManager().getMobileManager().getNetworkManager().getNetworkGameSelector().getInfo();
        } else if (request.startsWith("/tryToLogIn")) {
            try {
                boolean gotIt = clientManager.tryToSetSystemClient(request.substring("/tryToLogIn?".length()), client);
                if(gotIt) {
                    client.setWebPage(User.WEB_PAGE_CHOOSE_CHARACTER);
                    return "YES";
                } else {
                    return "NO";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Fehler (Login)";
        } else if (request.startsWith("/tryToJoinGame")) {
            
            NetworkGameSelector ngs = clientManager.getManager().getMobileManager().getNetworkManager().getNetworkGameSelector();
            boolean b = ngs.tryToGetReady(client);
            
            if(b) {
                return "YES";
            } else {
                return "NO";
            }
            
        } else if (request.startsWith("/needsRedirect")) {
            try {
                int actualWebpage = Integer.valueOf(request.substring("/needsRedirect?".length()));
                
                if(actualWebpage == client.getWebPage()) {
                    return "NO";
                } else {
                    return "YES";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Fehler (Needs Redirect)";
        } else if (request.startsWith("/tryToCreateGame")) {
            try {
                int gameToSelect = Integer.valueOf(request.substring("/tryToCreateGame?".length()));
                System.out.println(gameToSelect);
                GameFactory game = GameCollection.gameFactorys[gameToSelect];
                NetworkGameSelector ngs = clientManager.getManager().getMobileManager().getNetworkManager().getNetworkGameSelector();
                boolean b = ngs.tryToSelectGame(game, client);
                if(b) {
                    return "YES";
                } else {
                    return "NO";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "ERROR";
            }
        } else if (request.startsWith("/getGameIcon")) {
            int i = Integer.valueOf(request.substring("/getGameIcon?".length()));
            return WebGenerator.getGameImage(i);
        } else if (request.startsWith("/tryToGetCharacter")) {
            try {
                int i = Integer.valueOf(request.substring("/tryToGetCharacter?".length()));
                UserCharacter character = UserCharacterDatabase.getUserCharacter(i);
                System.out.println(i);
                boolean gotIt = client.tryToSetUserCharacter(character);
                if(gotIt) {
                    NetworkGameSelector ngs = clientManager.getManager().getMobileManager().getNetworkManager().getNetworkGameSelector();
                    if(ngs.isGameSelected()) {
                        client.setWebPage(User.WEB_PAGE_JOIN_GAME);
                    } else {
                        client.setWebPage(User.WEB_PAGE_CHOOSE_GAME);
                    }
                    return "YES";
                } else {
                    return "NO";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Fehler (Login)";
            }
        } else if (request.startsWith("/getPositionsInfo")) {
            return clientManager.generateSystemClientChooserAvailabilityInfoForAPI();
        } else if (request.startsWith("/getCharacterInfo")) {
            return WebGenerator.generateUserCharacterChooserAvailabilityInfoForAPI();
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
                return NO_MESSAGE_ID + "connected";
            } else {
                // Kontrolle nach Updates
                if(ir.matchMessageId(request.substring("/refresh?".length()))) {
                    return NO_UPDATES;
                } else {
                    return ir.getMessageIdAs9CharacterString() + WebGenerator.generateHTMLContent(ir.getTitel(), ir.getChoices(), messageID);
                }
            }
        } else if (request.startsWith("/reply")) {
            
            int messageID = Integer.valueOf(request.substring("/reply?".length(), request.lastIndexOf("?")));
            int answerID = Integer.valueOf(request.substring(request.lastIndexOf("?")+1));
            client.replyFromHTTP(messageID, answerID);
            
            return "OK";
        } else if (request.equals("/jquery.min.js")) {
            try {
                return AssetsLoader.loadFile("web/jquery.min.js");
            } catch (AssetNotExistsException ex) {
                Logger.getLogger(ControlDirector.class.getName()).log(Level.SEVERE, null, ex);
                return "Fataler Fehler!!! jquery.min.js kann nicht geladen werden.";
            }
        } else if (request.startsWith("/choosePosition")) {
            return WebGenerator.getChooseCharacterWebPage();
        } else if (request.equals("/functions.js")) {
            return AssetsLoader.loadFileIgnoreExceptions("web/functions.js");
        } else if(request.startsWith("/avatars/")){
            return AssetsLoader.loadFileIgnoreExceptions(request.substring(1));
        } else if(AssetsLoader.fileExists("web/" + request.substring(1))){
            return AssetsLoader.loadFileIgnoreExceptions("web/" + request.substring(1));
        } else {
            System.err.println("Unbekannte Eingabe: " + request);
            return "Fehler :(";
        }
        
        
    }
    

}
