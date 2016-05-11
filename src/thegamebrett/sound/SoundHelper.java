package thegamebrett.sound;

import java.applet.AudioClip;
import java.util.ArrayList;
import thegamebrett.assets.AssetNotExistsException;
import thegamebrett.assets.AssetsLoader;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 * 
 * Stellt Hilfsmethoden fuer die Sound-API zur Verfuegung.
 */
public class SoundHelper {
    
    private static ArrayList<AudioClip> sounds = new ArrayList<>();
    
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
