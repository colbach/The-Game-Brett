package thegamebrett.sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import thegamebrett.model.mediaeffect.SoundEffect;

/**
 * Kuemmert sich um Soundwiedergabe.
 *
 * @author christiancolbach
 */
public class SoundManager {

    public static void playSoundEffect(SoundEffect se) {
        playSound(se.getResource());
    }

    public static void playSound(String resource) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource(resource);
        AudioClip sound;
        sound = Applet.newAudioClip(url);
        sound.play();
    }

}
