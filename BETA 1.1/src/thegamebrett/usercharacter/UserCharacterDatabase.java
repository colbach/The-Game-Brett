package thegamebrett.usercharacter;

import java.util.ArrayList;
import thegamebrett.assets.AssetsLoader;

/**
 * @author Christian Colbach
 */
public class UserCharacterDatabase {
    private static ArrayList<UserCharacter> userCharacters;
    
    static {
        String csvs = AssetsLoader.loadText_SuppressExceptions("userdata/characters.csv");
        for(String csv : csvs.split("\n")) {
            userCharacters.add(UserCharacter.fromCSV(csv));
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
}
