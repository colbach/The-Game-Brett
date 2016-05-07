package thegamebrett.network;

import com.sun.security.ntlm.Client;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import thegamebrett.Manager;
import thegamebrett.game.dummy.D_GameLogic;
import thegamebrett.gui.MenueView;
import thegamebrett.model.GameFactory;
import thegamebrett.model.Model;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;

public class NetworkGameSelector {

    private final Manager manager;
    private GameFactory selectedGame;
    private boolean gameStarted;
    private final ArrayList<User> readyList;

    public NetworkGameSelector(Manager manager) {
        this.manager = manager;
        this.readyList = new ArrayList<>();
    }

    public synchronized boolean tryToSelectGame(GameFactory game, User creator) {
        if (selectedGame == null) {
            selectedGame = game;
            UserManager um = manager.getMobileManager().getUserManager();
            User[] users = um.getSystemClients();
            readyList.clear();
            readyList.add(creator);
            for (User user : users) {
                if (user != null) {
                    user.setActualInteractionRequest(null);
                    if (user.hasUserCharacter()) {
                        user.setWebPage(User.WEB_PAGE_JOIN_GAME);
                    }
                }
            }
            creator.setWebPage(User.WEB_PAGE_START_GAME);
            return true;
        } else {
            return false;
        }
    }
    
    public void endGame() {
        
        selectedGame = null;
        gameStarted = false;
        
        User[] users = manager.getMobileManager().getUserManager().getSystemClients();
        for(User user : users) {
            if(user!=null && user.hasUserCharacter())
                user.setWebPage(User.WEB_PAGE_CHOOSE_GAME);
        }
        
        resetReadyList();
        
    }

    private synchronized void resetReadyList() {
        readyList.clear();
    }

    public boolean isGameSelected() {
        return selectedGame != null && !isGameStarted();
    }
    
    public boolean isGameStarted() {
        return gameStarted;
    }

    public synchronized boolean tryToGetReady(User user) {
        if (!isGameSelected()) {
            return false;
        } else if (selectedGame.getMaximumPlayers() >= readyList.size() + 1 && selectedGame.getMinimumPlayers() <= readyList.size() + 1) {
            readyList.add(user);
            user.setWebPage(User.WEB_PAGE_START_GAME);
            return true;
        } else {
            return false;
        }
    }

    public synchronized void notReady(User user) {
        readyList.remove(user);
    }

    public boolean canStart() {
        return selectedGame.getMaximumPlayers() >= readyList.size() && selectedGame.getMinimumPlayers() <= readyList.size();
    }

    public synchronized boolean tryToStart() {
        if (canStart()) {

            System.out.println("Starte Spiel...");

            // Webview von Spielern setzen
            for (User user : readyList) {
                user.setWebPage(User.WEB_PAGE_PLAY_GAME);
            }

            // Nichtteilnehmende Clients aussortieren
            User[] systemClients = manager.getMobileManager().getUserManager().getSystemClients();
            for(int i=0; i<systemClients.length; i++) {
                boolean plays = false;
                for(User player : readyList) {
                    if(systemClients[i] == player) {
                        plays = true;
                    }
                }
                if(!plays) {
                    systemClients[i] = null;
                }
            }
            
            // Spielerliste erstellen
            ArrayList<User> players = new ArrayList<>();
            for(User user : systemClients) {
                if(user != null) {
                    players.add(user);
                }
            }
            if(players.size() != readyList.size()) {
                System.err.println("Achtung: readyList.size()->" + readyList.size() + " aber players.size()->" + players.size());
            }
            
            // Spiel starten
            Platform.runLater(() -> {
                try {
                    Model gameModel = selectedGame.createGame(players);
                    manager.getGui().getGameView().setGameModel(gameModel);
                    manager.startGame(gameModel);
                    manager.getGui().showGameScene();
                } catch (TooMuchPlayers ex) {
                    Logger.getLogger(MenueView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TooFewPlayers ex) {
                    Logger.getLogger(MenueView.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            gameStarted = true;
            return true;
        } else {
            System.err.println("Spiel kann nicht gestartet werden");
            return false;
        }
    }

    public synchronized String getInfo() {
        if (!isGameSelected()) {
            return "Es ist noch kein Spiel ausgewählt";
        } else {
            return selectedGame.getGameName() + " wurde angelegt.<br>"
                    + "Es sind bereits " + readyList.size() + " Spieler eingetreten.<br>"
                    + "(Minimum: " + selectedGame.getMinimumPlayers() + " Maximum: " + selectedGame.getMaximumPlayers() + ")";
        }
    }
}
