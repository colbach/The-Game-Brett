/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.model.mediaeffect;

import java.io.File;

/**
 *
 * @author christiancolbach
 */
public class SoundEffect {
    
    /** Referenz auf Sounddatei. Format: .wav */
    private File clip;
    
    /** laenge des SoundClips */
    private double duration;
    
    /** gibt an ob Clip geloopt werden darf.
     * Wenn false kann clip kürzer als duration sein, wenn true wird clip so lange wiederholt bis duration fertig
     */
    private boolean loop;

    public SoundEffect(File clip, double duration, boolean loop) {
        this.clip = clip;
        this.duration = duration;
        this.loop = loop;
    }
}
