package thegamebrett.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import thegamebrett.assets.AssetNotExistsException;
import thegamebrett.assets.AssetsLoader;

/**
 * Stellt Hilfsmethoden zur Verfuegung. 
 * 
 * @author Christian Colbach
 */
public class SoundHelper {
    
    private static ArrayList<AudioClip> sounds = new ArrayList<>();
    
    /*
    BUGS:
    - Audiodatei wird stoppt wenn Thread beendet wird.
    - Loop_Little_Big_Adventure_04.wav kann aus unbekannten Gruenden nicht abgespielt werden.
    */
    
    /**
     * Spielt eine Audiodatei ab.
     * ACHTUNG: Thread muss lange genug laufen damit Datei bis zum Ende abgespielt wird.
     * 
     * @param resource Resource welche abgespielt werden soll.
     *                 (Pfad zu Datei, ausgehend von src, muss wav sein)
     * BEISPIEL: playSound("thegamebrett/sound/assets/glitch.wav");
     */
    public static void playSound(String resource) {
        AudioClip sound;
        try {
            sound = AssetsLoader.loadSound(resource);
            synchronized(sounds) {
                sounds.add(sound);
            }
            sound.play();
            
        } catch (AssetNotExistsException ex) {
            System.out.println("Sound kann nicht abgespielt werden");
        }
    }
    
    public static void stopSounds() {
        synchronized(sounds) {
            for(AudioClip s : sounds)
                s.stop();
            sounds.clear();
        }
    }
    
}
