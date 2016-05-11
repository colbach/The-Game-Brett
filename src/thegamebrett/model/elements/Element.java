package thegamebrett.model.elements;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 *
 * Definiert Elemente welche später auf der Spielfläche angezeigt werden
 */
public abstract class Element {

    private volatile Boolean changeRegistrar = true;

    /**
     * Diese Methode muss bei JEDER AENDERUNG aufgerufen werden damit UI diese
     * Aktualisiert. AUSNAHME: Positionsaenderungen
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
