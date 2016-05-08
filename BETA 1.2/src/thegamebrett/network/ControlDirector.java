package thegamebrett.network;

import java.io.File;
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
    
    /**  Rueckgabe von Server wenn Client redirecten soll */
    public static final String DIRECT = "direct";
    
    /**  diese Id wird uebergeben um anzugeben dass keine ID verfuegbar ist */
    public static final String NO_MESSAGE_ID = "#########";
    
    /**  diese Id wird uebergeben um anzugeben dass keine Farbe verfuegbar ist */
    public static final String NO_COLOR_SET = "NONE";
        
    public ControlDirector(UserManager clientManager) {
        this.clientManager = clientManager;
    }
    
    public Object direct(User u) {
        final int webPage = u.getWebPage();
            
        if(webPage == User.WEB_PAGE_CHOOSE_POSITION) {
            return AssetsLoader.loadText_localized_SuppressExceptions("web/choosePosition.html");
        } else if(webPage == User.WEB_PAGE_CHOOSE_CHARACTER) {
            return WebGenerator.getChooseCharacterWebPage();
        } else if(webPage == User.WEB_PAGE_CHOOSE_GAME) {
            return WebGenerator.getChooseGameWebPage();
        } else if(webPage == User.WEB_PAGE_PLAY_GAME) {
            return AssetsLoader.loadText_localized_SuppressExceptions("web/inGame.html");
        } else if(webPage == User.WEB_PAGE_GAME_ALREADY_STARTED) {
            return AssetsLoader.loadText_localized_SuppressExceptions("web/gameAlreadyStarted.html");
        } else if(webPage == User.WEB_PAGE_PREFERENCES) {
            return "noch nicht implementiert (Einstellungen)";
        } else if(webPage == User.WEB_PAGE_START_GAME) {
            return AssetsLoader.loadText_localized_SuppressExceptions("web/startGame.html");
        } else if(webPage == User.WEB_PAGE_JOIN_GAME) {
            return AssetsLoader.loadText_localized_SuppressExceptions("web/joinGame.html");
        } else {
            return "Fehler (Ungueltiger Wert)";
        }
    }
    
    @Override
    public Object query(String request, Socket clientSocket) throws QueryException {
        System.out.println(request);
        final NetworkGameSelector ngs = clientManager.getManager().getMobileManager().getNetworkManager().getNetworkGameSelector();
        if(ngs.isGameStarted() && !clientManager.isSystemClient(clientSocket.getInetAddress())) {
            
            if(request.startsWith("/refreshFreePositionList")) {
                return clientManager.generateFreePositionHTML();
            } else if (request.equals("/") || request.equals("/index.html")) {
                return AssetsLoader.loadText_localized_SuppressExceptions("web/gameAlreadyStarted.html");
            } else if (request.startsWith("/tryToLogIn")) {
                try {
                    boolean gotIt = clientManager.tryToReplaceSystemClient(request.substring("/tryToLogIn?".length()), clientSocket.getInetAddress());
                    if(gotIt) {
                        return "YES";
                    } else {
                        return "NO";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "Fehler (Login)";
            }
        } else {
            final User client = clientManager.getOrAddClientForInetAddress(clientSocket.getInetAddress());
            client.signOfLife();
            
            // Kontrolle State...
            final int webPage = client.getWebPage();
            final boolean gameStarted = ngs.isGameStarted();
            final boolean gameSelected = ngs.isGameSelected();
            if(gameStarted && (webPage != User.WEB_PAGE_PREFERENCES && webPage != User.WEB_PAGE_PLAY_GAME)) {
                System.err.println("Client scheint falsche WebPage zu haben. Fallback: WEB_PAGE_PLAY_GAME");
                client.setWebPage(User.WEB_PAGE_PLAY_GAME);
            } else if(gameSelected && (webPage != User.WEB_PAGE_JOIN_GAME && webPage != User.WEB_PAGE_START_GAME)) {
                System.err.println("Client scheint falsche WebPage zu haben. Fallback: WEB_PAGE_JOIN_GAME");
                client.setWebPage(User.WEB_PAGE_JOIN_GAME);
            }
            
            // Analyse Request-String ...
            if (request.equals("/") || request.equals("/index.html")) {
                return direct(client);
            } else if (request.startsWith("/tryToCancelGame")) {
                ngs.tryToCancelGame(client);
                return "OK";
            } else if (request.startsWith("/tryToStartGame")) {
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
                return ngs.getInfo();
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
            } else if (request.startsWith("/refreshGame")) {
                // Kontrolle ob Spieler ueberhaupt noch in Spiel
                if(client.getWebPage() != User.WEB_PAGE_PLAY_GAME) {
                    return DIRECT;
                }
                
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
                    if(ir.matchMessageId(request.substring("/refreshGame?".length()))) {
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
            }
        }
        if (request.equals("/jquery.min.js")) {
            try {
                return AssetsLoader.loadFile("web/jquery.min.js");
            } catch (AssetNotExistsException ex) {
                Logger.getLogger(ControlDirector.class.getName()).log(Level.SEVERE, null, ex);
                return "Fataler Fehler!!! jquery.min.js kann nicht geladen werden.";
            }
        } else if (request.equals("/functions.js")) {
            String f = AssetsLoader.loadText_SuppressExceptions("web/functions.js");
            return f;
        } else if(request.startsWith("/avatars/")){
            return AssetsLoader.loadFile_SuppressExceptions(request.substring(1));
        } else if(AssetsLoader.fileExists("web/" + request.substring(1))){
            return AssetsLoader.loadFile_SuppressExceptions("web/" + request.substring(1));
        }
        System.err.println("Unbekannte Eingabe: " + request);
        return "Fehler :(";
    }
    

}
