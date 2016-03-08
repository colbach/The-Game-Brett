/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.model.mediaeffect;

/**
 *
 * @author christiancolbach
 */
public class SoundEffect {
    
    /** Referenz auf Sounddatei. Format: .wav */
    private String resource;
    
    /** laenge des SoundClips */
    private double duration;
    
    /** gibt an ob Clip geloopt werden darf.
     * Wenn false kann clip kürzer als duration sein, wenn true wird clip so lange wiederholt bis duration fertig
     */
    private boolean loop;

    public SoundEffect(String resource, double duration, boolean loop) {
        this.resource = resource;
        this.duration = duration;
        this.loop = loop;
    }
    
    public SoundEffect(String resource) {
        this.resource = resource;
        this.duration = Double.NaN;
        this.loop = false;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
    
    
}
