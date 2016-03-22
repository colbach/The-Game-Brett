package thegamebrett.action.request;

import thegamebrett.model.mediaeffect.MediaEffect;

/**
 * @author Christian Colbach
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
