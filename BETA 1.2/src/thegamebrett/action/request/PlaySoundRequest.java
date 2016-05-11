package thegamebrett.action.request;

import thegamebrett.model.mediaeffect.SoundEffect;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
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
