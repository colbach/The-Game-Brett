package thegamebrett.action.request;

import thegamebrett.model.mediaeffect.MediaEffect;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
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
