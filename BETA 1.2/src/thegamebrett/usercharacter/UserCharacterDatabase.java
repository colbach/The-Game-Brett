package thegamebrett.usercharacter;

import java.util.ArrayList;
import thegamebrett.assets.AssetsLoader;

/**
 * @author Christian Colbach
 */
public class UserCharacterDatabase {
    
    private final static ArrayList<UserCharacter> userCharacters = new ArrayList<>();
    static {
        String csvs = AssetsLoader.loadText_SuppressExceptions("characters.csv");
        int i=0;
        for(String csv : csvs.split("\n")) {
            i++;
            if(!csv.startsWith("//")) {
                try {
                    userCharacters.add(UserCharacter.fromCSV(csv));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Fehler beim parsen von characters.csv: Zeile " + i + " ist ungueltig");
                }
            }
        }
    }
    
    public static String toCSV() {
        StringBuilder sb = new StringBuilder();
        userCharacters.stream().forEach((uc) -> {
            sb.append("\n").append(uc.toCSV());
        });
        return sb.toString();
    }
    
    public void addUserCharacter(UserCharacter uc) {
        synchronized(userCharacters) {
            userCharacters.add(uc);
            AssetsLoader.saveText("userdata/characters.csv", toCSV());
        }
    }

    public static ArrayList<UserCharacter> getUserCharacters() {
        return userCharacters;
    }
    
    public static UserCharacter getUserCharacter(int i) {
        return userCharacters.get(i);
    }
}
