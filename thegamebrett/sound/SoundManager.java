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

    public static void main(String[] args) {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource("thegamebrett/sound/assets/glitch.wav");
        AudioClip sound;
        //try {
        sound = Applet.newAudioClip(url);
        sound.play();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SoundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void playSoundEffect(SoundEffect se) {

    }

    protected static void playSound(String resource) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource(resource);
        AudioClip sound;
        sound = Applet.newAudioClip(url);
        sound.play();
    }

}
