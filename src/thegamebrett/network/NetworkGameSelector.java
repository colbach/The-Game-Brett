package thegamebrett.network;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import thegamebrett.Manager;
import thegamebrett.gui.MenueView;
import thegamebrett.gui.ScreenResolution;
import thegamebrett.model.GameFactory;
import thegamebrett.model.Model;
import thegamebrett.model.exceptions.TooFewPlayers;
import thegamebrett.model.exceptions.TooMuchPlayers;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class NetworkGameSelector {

    private final Manager manager;
    private GameFactory selectedGame;
    private boolean gameStarted;
    private final ArrayList<User> players;

    private User firstCanceler = null;
    private long cancelTime = 0;

    public NetworkGameSelector(Manager manager) {
        this.manager = manager;
        this.players = new ArrayList<>();
    }

    public synchronized boolean tryToCancelGame(User canceler) {
        System.out.println(players.size());
        if ((firstCanceler != canceler && System.currentTimeMillis() - cancelTime < 10000) || players.size() <= 1) {
            endGame();
            Platform.runLater(() -> {
                manager.getGui().showMenuScene();
            });
            return true;
        } else {
            firstCanceler = canceler;
            cancelTime = System.currentTimeMillis();
            return false;
        }
    }

    public synchronized boolean tryToSelectGame(GameFactory game, User creator) {
        if (selectedGame == null) {
            selectedGame = game;
            UserManager um = manager.getMobileManager().getUserManager();
            User[] users = um.getSystemClients();
            players.clear();
            if (creator != null) {
                players.add(creator);
            }
            for (User user : users) {
                if (user != null) {
                    user.setActualInteractionRequest(null);
                    if (user.hasUserCharacter()) {
                    }
                }
            }
            if (creator != null) {
                if (manager.getGui() != null && manager.getGui().getMenuView() != null) {
                    manager.getGui().getMenuView().refreshGameSelectedScreen();
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void endGame() {
        selectedGame = null;
        gameStarted = false;
        User[] users = manager.getMobileManager().getUserManager().getSystemClients();
        resetPlayers();
    }

    private synchronized void resetPlayers() {
        players.clear();
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
        } else if (selectedGame.getMaximumPlayers() >= players.size() + 1) {
            players.add(user);
            if (manager.getGui() != null && manager.getGui().getMenuView() != null) {
                manager.getGui().getMenuView().refreshGameSelectedScreen();
            }
            return true;
        } else {
            return false;
        }
    }

    public synchronized void notReady(User user) {
        players.remove(user);
    }

    public boolean canStart() {
        if (selectedGame == null) {
            System.err.println("Game is not selected.");
            return false;
        } else {
            return selectedGame.getMaximumPlayers() >= players.size() && selectedGame.getMinimumPlayers() <= players.size();
        }
    }

    public synchronized boolean tryToStart() {
        if (canStart()) {

            System.out.println("Starte Spiel...");

            // Nichtteilnehmende Clients aussortieren
            User[] systemClients = manager.getMobileManager().getUserManager().getSystemClients();
            for (int i = 0; i < systemClients.length; i++) {
                boolean plays = false;
                for (User player : players) {
                    if (systemClients[i] == player) {
                        plays = true;
                    }
                }
                if (!plays) {
                    if (systemClients[i] != null) {
                        systemClients[i].removeUserCharacter();
                        systemClients[i] = null;
                    }
                }
            }

            // Spielerliste erstellen
            ArrayList<User> players = new ArrayList<>();
            for (User user : systemClients) {
                if (user != null) {
                    players.add(user);
                }
            }
            if (players.size() != this.players.size()) {
                System.err.println("Achtung: readyList.size()->" + this.players.size() + " aber players.size()->" + players.size());
            }

            // Spiel starten
            Platform.runLater(() -> {
                try {
                    Model gameModel = selectedGame.createGame(players);
                    ScreenResolution.setBoardRatios(gameModel.getBoard().getRatioX(), gameModel.getBoard().getRatioY());

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
            return Manager.rb.getString("NoGameChoosen");
        } else {
            return String.format(Manager.rb.getString("JoinedUserInfo"), players.size(), selectedGame.getMinimumPlayers(), selectedGame.getMaximumPlayers());
        }
    }

    public ArrayList<User> getPlayers() {
        return players;
    }
}