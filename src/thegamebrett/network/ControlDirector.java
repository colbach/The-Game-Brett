package thegamebrett.network;

import java.net.InetAddress;
import thegamebrett.network.httpserver.Director;
import java.net.Socket;
import thegamebrett.Manager;
import thegamebrett.action.request.InteractionRequest;
import thegamebrett.assets.AssetsLoader;
import thegamebrett.game.GameCollection;
import thegamebrett.model.GameFactory;
import thegamebrett.usercharacter.UserCharacter;
import thegamebrett.usercharacter.UserCharacterDatabase;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class ControlDirector implements Director {

    UserManager userManager;

    /**
     * Rueckgabe von Server wenn kein Update noetig ist
     */
    public static final String NO_UPDATES = "null";

    /**
     * Rueckgabe von Server wenn Client redirecten soll
     */
    public static final String DIRECT = "direct";

    /**
     * diese Id wird uebergeben um anzugeben dass keine ID verfuegbar ist
     */
    public static final String NO_MESSAGE_ID = "#########";

    /**
     * diese Id wird uebergeben um anzugeben dass keine Farbe verfuegbar ist
     */
    public static final String NONE = "#ECDACE";

    public static final String YES = "YES";
    public static final String NO = "NO";
    public static final String OK = "OK";

    public ControlDirector(UserManager clientManager) {
        this.userManager = clientManager;
    }

    public Object direct(int webPage) {

        if (webPage == User.WEB_PAGE_CHOOSE_POSITION) {
            return AssetsLoader.loadText_localized_SuppressExceptions("web/choosePosition.html");
        } else if (webPage == User.WEB_PAGE_CHOOSE_CHARACTER) {
            return WebGenerator.getChooseCharacterWebPage();
        } else if (webPage == User.WEB_PAGE_CHOOSE_GAME) {
            return WebGenerator.getChooseGameWebPage();
        } else if (webPage == User.WEB_PAGE_PLAY_GAME) {
            return AssetsLoader.loadText_localized_SuppressExceptions("web/inGame.html");
        } else if (webPage == User.WEB_PAGE_GAME_ALREADY_STARTED) {
            return AssetsLoader.loadText_localized_SuppressExceptions("web/gameAlreadyStarted.html");
        } else if (webPage == User.WEB_PAGE_START_GAME) {
            return AssetsLoader.loadText_localized_SuppressExceptions("web/startGame.html");
        } else if (webPage == User.WEB_PAGE_JOIN_GAME) {
            return AssetsLoader.loadText_localized_SuppressExceptions("web/joinGame.html");
        } else {
            return "Fehler (Ungueltiger Wert)";
        }
    }

    @Override
    public Object query(String request, Socket clientSocket) throws QueryException {
        System.out.println(request);
        final InetAddress inetAddress = clientSocket.getInetAddress();
        final NetworkGameSelector ngs = userManager.getManager().getMobileManager().getNetworkManager().getNetworkGameSelector();
        User userOrNull = userManager.getClientForInetAddress(inetAddress);

        // --- User noch nicht registriert ---
        if (userOrNull == null) {
            if (request.startsWith("/needsRedirect")) {
                try {
                    int actualWebpage = Integer.valueOf(request.substring("/needsRedirect?".length()));
                    return actualWebpage == User.WEB_PAGE_CHOOSE_POSITION || actualWebpage == User.WEB_PAGE_GAME_ALREADY_STARTED ? NO : YES;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.err.println("Fehler (needsRedirect/nicht angemeldet)");
                return "Fehler (needsRedirect/nicht angemeldet)";
            }

            if (ngs.isGameStarted()) { // Spiel hat bereits begonnen

                if (request.equals("/") || request.equals("/index.html")) {
                    return AssetsLoader.loadText_localized_SuppressExceptions("web/gameAlreadyStarted.html");

                } else if (request.startsWith("/tryToLogIn")) {
                    try {
                        boolean gotIt = userManager.tryToReplaceSystemClient(request.substring("/tryToLogIn?".length()), inetAddress);
                        return gotIt ? YES : NO;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.err.println("Fehler (tryToLogIn)");
                    return "Fehler (tryToLogIn)";

                } else if (request.startsWith("/refreshFreePositionList")) {
                    return userManager.generateFreePositionHTML();
                }

            } else // Spiel hat noch nicht begonnen
            if (request.equals("/") || request.equals("/index.html")) {
                return AssetsLoader.loadText_localized_SuppressExceptions("web/choosePosition.html");

            } else if (request.startsWith("/tryToLogIn")) {
                try {
                    boolean gotIt = userManager.tryToSetSystemClient(request.substring("/tryToLogIn?".length()), inetAddress);
                    return gotIt ? YES : NO;
                } catch (Exception e) {
                    System.err.println("User kann nicht gesetzt werden!");
                    e.printStackTrace();
                }
                System.err.println("Fehler (tryToLogIn)");
                return "Fehler (tryToLogIn)";

            } else if (request.startsWith("/getPositionsInfo")) {
                return userManager.generateSystemClientChooserAvailabilityInfoForAPI();
            }

            // --- User bereits registiert ---
        } else {
            final User user = userOrNull;
            user.signOfLife();
            final int webPage = user.getWebPage();

            if (request.equals("/") || request.equals("/index.html")) {
                return direct(webPage);

            } else if (request.startsWith("/tryToCancelGame")) {
                ngs.tryToCancelGame(user);
                return OK;

            } else if (request.startsWith("/tryToStartGame")) {
                boolean b = ngs.tryToStart();
                return b ? YES : NO;

            } else if (request.equals("/logOut")) {
                if (ngs.isGameStarted()) {
                    userManager.logOutSystemClientWhilePlaying(user);
                } else {
                    userManager.logOutSystemClient(user);
                }
                return OK;

            } else if (request.startsWith("/getUserColor")) {
                if (user.hasUserCharacter()) {
                    String c = user.getUserCharacter().getColor();
                    return c != null ? c : NONE;
                }
                return NONE;

            } else if (request.startsWith("/refreshStartGameInfoText")) {
                return ngs.getInfo();

            } else if (request.startsWith("/tryToJoinGame")) {
                boolean b = ngs.tryToGetReady(user);
                return b ? YES : NO;

            } else if (request.startsWith("/needsRedirect")) {
                try {
                    int actualWebpage = Integer.valueOf(request.substring("/needsRedirect?".length()));
                    return actualWebpage == user.getWebPage() ? NO : YES;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.err.println("Fehler (needsRedirect/angemeldet)");
                return "Fehler (needsRedirect/angemeldet)";

            } else if (request.startsWith("/tryToCreateGame")) {
                try {
                    int gameToSelect = Integer.valueOf(request.substring("/tryToCreateGame?".length()));
                    System.out.println(gameToSelect);
                    GameFactory game = GameCollection.gameFactorys[gameToSelect];
                    boolean b = ngs.tryToSelectGame(game, user);
                    if (b) {
                        return "YES";
                    } else {
                        return "NO";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.err.println("Fehler (tryToCreateGame)");
                return "Fehler (tryToCreateGame)";

            } else if (request.startsWith("/tryToGetCharacter")) {
                try {
                    int i = Integer.valueOf(request.substring("/tryToGetCharacter?".length()));
                    UserCharacter character = UserCharacterDatabase.getUserCharacter(i);
                    boolean gotIt = user.tryToSetUserCharacter(character);
                    return gotIt ? YES : NO;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.err.println("Fehler (Login)");
                return "Fehler (Login)";

            } else if (request.startsWith("/getCharacterInfo")) {
                return WebGenerator.generateUserCharacterChooserAvailabilityInfoForAPI();

            } else if (request.startsWith("/refreshGame")) {
                // Kontrolle ob Spieler ueberhaupt noch in Spiel
                if (user.getWebPage() != User.WEB_PAGE_PLAY_GAME) {
                    return DIRECT;
                }

                // messageID und InteractionRequest sicher aus user laden
                long messageID;
                InteractionRequest ir;
                do {
                    messageID = user.getMessageId();
                    ir = user.getActualInteractionRequest();
                } while (messageID != user.getMessageId());

                // ausgabe
                if (ir == null) {
                    return NO_MESSAGE_ID + Manager.rb.getString("PleaseWait");
                } else // Kontrolle nach Updates
                if (ir.matchMessageId(request.substring("/refreshGame?".length()))) {
                    return NO_UPDATES;
                } else {
                    return ir.getMessageIdAs9CharacterString() + WebGenerator.generateHTMLContent(ir.getTitel(), ir.getChoices(), messageID);
                }

            } else if (request.startsWith("/reply")) {
                int messageID = Integer.valueOf(request.substring("/reply?".length(), request.lastIndexOf("?")));
                int answerID = Integer.valueOf(request.substring(request.lastIndexOf("?") + 1));
                user.replyFromHTTP(messageID, answerID);
                return OK;
            }
        }

        // --- Generelle Abfragen ---
        if (request.startsWith("/avatars/")) {
            return AssetsLoader.loadFile_SuppressExceptions(request.substring(1));
        } else if (request.startsWith("/gameicons/")) {
            return AssetsLoader.loadFile_SuppressExceptions(request.substring(1));
        } else if (request.equals("/functions.js")) {
            return AssetsLoader.loadText_localized_SuppressExceptions("web/functions.js");
        } else if (AssetsLoader.fileExists("web/" + request.substring(1))) {
            return AssetsLoader.loadFile_SuppressExceptions("web/" + request.substring(1));
        } else if (request.startsWith("/refreshFreePositionList")) {
            return DIRECT;
        } else if (request.startsWith("/getUserColor")) {
            return NONE;
        }
        System.err.println("Unbekannte Eingabe: " + request);
        return "Fehler :(";
    }

}
