package thegamebrett.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/**
 *
 * @author Christian Colbach
 */
public class SoundHelper {
    
    /**
     * Spielt eine Audiodatei ab.
     * ACHTUNG: Thread muss lange genug laufen damit Datei bis zum Ende abgespielt wird
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
        sound.play();
    }
    
}
