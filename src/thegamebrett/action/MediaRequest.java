package thegamebrett.action;

import thegamebrett.model.mediaeffect.MediaEffect;

/**
 *
 * @author christiancolbach
 */
public class MediaRequest implements SoundRequest, GUIRequest {
    
    private final MediaEffect media;

    public MediaRequest(MediaEffect media) {
        this.media = media;
    }

    public MediaEffect getMedia() {
        return media;
    }
}
