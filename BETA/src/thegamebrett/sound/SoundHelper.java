package thegamebrett.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.ArrayList;

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
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource(resource);
        AudioClip sound;
        sound = Applet.newAudioClip(url);
        synchronized(sounds) {
            sounds.add(sound);
        }
        sound.play();
    }
    
    public static void stopSounds() {
        synchronized(sounds) {
            for(AudioClip s : sounds)
                s.stop();
            sounds.clear();
        }
    }
    
}
