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
    
    private long loopInterval;
    
    private int loopCount;
    
    /** gibt an ob Clip geloopt werden darf.
     * Wenn false kann clip kürzer als duration sein, wenn true wird clip so lange wiederholt bis duration fertig
     */
    private boolean loop;

    public SoundEffect(String resource, boolean loop, long loopInterval, int loopCount) {
        this.resource = resource;
        this.loopInterval = loopInterval;
        this.loop = loop;
        this.loopCount = loopCount;
    }
    
    public SoundEffect(String resource) {
        this.resource = resource;
        this.loopInterval = 0;
        this.loopCount = 1;
        this.loop = false;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public long getLoopInterval() {
        return loopInterval;
    }

    public void setLoopInterval(long loopInterval) {
        this.loopInterval = loopInterval;
    }

    public int getLoopCount() {
        return loopCount;
    }

    public void setLoopCount(int loopCount) {
        this.loopCount = loopCount;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    
    
}
