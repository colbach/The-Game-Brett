package thegamebrett.model.elements;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Definiert Elemente welche später auf der Spielfläche angezeigt werden
 *
 * @author Christian Colbach
 */
public abstract class Element {

    private volatile Boolean changeRegistrar = true;

    /**
     * Diese Methode muss bei JEDER AENDERUNG aufgerufen werden damit UI diese
     * Aktualisiert.
     */
    public void registerChange() {
        changeRegistrar = true;
    }

    public boolean isChangedSinceLastCall() {
        synchronized (changeRegistrar) {
            if (changeRegistrar) {
                changeRegistrar = false;
                return true;
            } else {
                return false;
            }
        }
    }

}
