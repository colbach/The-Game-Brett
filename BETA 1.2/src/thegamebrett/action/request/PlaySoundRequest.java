package thegamebrett.action.request;

import thegamebrett.model.mediaeffect.SoundEffect;

/**
 * @author Christian Colbach
 */
public class PlaySoundRequest implements SoundRequest {
    
    private final SoundEffect sound;

    public PlaySoundRequest(SoundEffect sound) {
        this.sound = sound;
    }

    public SoundEffect getSound() {
        return sound;
    }
}
