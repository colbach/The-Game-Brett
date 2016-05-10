# The-Game-Brett
Teamprojekt Hochschule Trier 2015-2016
Entwicklung und Umsetzung eines digitalen Spielbrettframeworks für Gemeinschaftsspiele
von Kore Kaluzynski, Cenk Saatci, Christian Colbach

## Installation und Ausführung der Anwendung
<p>
Zur Erstellung einer ausführbaren Datei muss das <em>src</em> Verzeichnis kompiliert werden.
Die Hauptklasse der Anwendung ist die Klasse <em>thegamebrett.gui.GUIApplication</em>. Dieser muss zur Ausführung ein Parameter mitgegeben werden welcher den absoluten Pfad auf den Assetsordner angibt mit folgendem <code>/</code>. Dieser Ordner ist in <em>scr/assetsfolder</em> zu finden und kann auf einen beliebigen Ort im Dateisystem gelegt werden:
</p>
##### Beispiel wie die Anwendung gestartet werden kann:
<pre>
$ java thegamebrett.gui.GUIApplication /Users/grompa/Documents/gamebrett/classes/assetsfolder/
</pre>

## Aufbau des Frameworks
![Grundsätzlicher Aufbau](images/Grundsaetzlicher Aufbau Projekt.png)
<em>Prinzipieller Aufbau des Projektes</em>

### Model View Controller
Mit einer Trennung zwischen Spielelogik und IO versuchen wir in unser Framework stark MVC-orientiert umzusetzen. Dies hat den Vorteil dass die Spiele komplett austauschbar sind ohne dass dafür eine Zeile Code im Rest des Systems geändert werden muss.

#### Der Manager (Controller)
<p>
    Der Manager stellt sozusagen den "Dreh und Angelpunkt" der Anwendung dar. Über ihn laufen alle Kommunikationen und er verbindet alles mit allem (direkt oder indirekt).
</p>

#### Grafik (View #1)
<p>
    Die Grafik welche auf dem Gerät selbst angezeigt wird setzt auf JavaFX. In unserem Aufbau wechselt man immer nur zwischen zwei <em>Scenes</em>. Der <code>MenueView</code> und der <code>GameView</code>.
</p>
<p>
    Die <code>MenueView</code> stellt die erste <em>Scene</em>. Diese wird beim Start der Anwendung sofort angezeigt und zeigt die Möglichkeiten an welche bestehen. Zur Funktion wird sie jedoch nicht benötigt (auch wenn sie diese ergänzen kann) da die Funktion nicht auf die Eingabe direkt am Gerät angewiesen ist (Mehr dazu unter <em>Steuerungskonzept</em>).
</p>
<p>
    Die <code>GameView</code> stellt die zweite <em>Scene</em>. Diese ist generisch und stellt immer das aktuelle Spiel dar. Spiele werden in ihr geladen und auch hier animiert (Bewegung von Figuren und Feldern).
</p>

#### Webinterface (View #2)
<p>
    Das <em>Webinterface</em> stellt in unserem Aufbau die Grundlage aller Kommunikationen mit dem Spieler. Jeder Spieler muss sich über sein Handy (/anderes internetfähiges Gerät) an unserem Server anmelden. Von diesem bleibt er dann mit dem System verbunden und interagiert mit dem System und den Spielen.
</p>

#### Spielelogik (Model #1)
<p>
    Die <em>Spielelogik</em> und die dazugehörigen Klassen des <em>Models</em> stellt das Spiel im eigentlichen dar. Sie ist komplett austauschbar und kann auch zur Laufzeit beliebig oft ausgetauscht werden. Dies ermöglicht es sich gemeinsam für ein Spiel zu entscheiden und dieses dann zu spielen.
</p>

#### Userstate (Model #2)
<p>
    Die am System angemeldeten User werden vom Usermanager organisiert. Jeder Spieler hat einen Sitzplatz und einen dazugehörigen Character.
</p>

#### Kommunikation zwischen einzelnen Modulen
<p>
    Die Komunikation zwischen den einzelnen Modulen (Model, Grafik, Webinterface, Spielelogik) findet über einen selbst implementiertes System aus <em>Actions</em> statt. Grundlage hierfür stellen die Interfaces <code>ActionRequest</code> und <code>ActionResponse</code> von welchen es eine Reihe von Ableitungen gibt und welche durch das gesamte System durchgereicht werden. Hierbei läuft das immer so ab dass der Manager <em>Requests</em> bei der Spielelogik abholt und diese dann an das gewünschte System weitergibt (z.B. Netzwerk oder UI). Diese Systeme generieren dann zu diesen passende <em>Responses</em> welche dann wieder an den Manager abgegeben werden. Hierzu zu beachten ist dass die Spielelogik immer nur beim Anstoss durch den Manager aktiv wird und ihre nächste <em>Requests</em> generieren. Ein solcher Anstoss geschieht nur wenn eine <em>Response</em> an die Spielelogik zurück geht. Um diesen Aufbau zu verstehen sind folgende Methoden relevant:
</p>
In der Spielelogik (<code>Gamelogik</code>):
```java
public ActionRequest[] next(ActionResponse as);
````
In dem Manager (<code>Manager</code>, <code>MobileManager</code>, <code>TimeManager</code>, <code>NetworkManager</code>):
```java
public void react(ActionResponse response);
````

### Steuerungskonzept
<p>
    Unsere Brett ist so konzipiert dass eine Steuerung mittels Maus oder Touchscreen nicht nötig ist. Es reicht wenn das Gamebrett mit dem WLAN verbunden ist beziehungsweise einen eingebauten Access Point besitzt. Das Menü und die Spiele werden über die Handhelds welche mit dem Brett verbunden sind gesteuert.
</p>

### Assets
<p>
    Zum laden von Assets wird die Klasse <code>AssetLoader</code> verwendet. Diese vereinfacht im System das laden von Inhalten und lokalisiert Textdokumente On-the-fly (Mehr dazu im nächsten Abschnitt Lokalisierung)
</p>

### Lokalisierung
<p>
    Für die Lokalisierung unserer Anwendung verwenden wir das Java-eigene Lokalisierungsframework <code>java.util.ResourceBundle</code>. Dieses erlaubt es Sprachunabhängige Strings zu definieren und diese dann in verschiedene Sprachen zu übersetzen.
</p>

#### Verwendung durch den Endbenutzer
<p>
    Die Einstellung der Sprache kann in der UI im Menu vorgenommen werden. Hierzu klickt man auf den Button [Optionen] uns stell diese dann unter den Spracheinstellungen ein.
</p>

#### Verwendung durch den Programmierer
<p>
    Bei der Verwendung unterscheidet unterscheiden wir im folgenden zwischen der Lokalisierung des Java-codes und der Lokalisierung der HTML-Seiten. Hierzu zu erwähnen ist ausserdem dass alle Strings welche verwendet werden sollen in der entsprechenden <code>properties</code>-Datei angegeben sein müssen. Für English in der Datei <code>languages_en.properties</code> und für Deutsch <code>languages_de.properties</code>.
</p>

##### Java
<p>
    In Java können Strings durch die direkte Verwendung des <code>ResourceBundle</code>-Objektes lokalisiert werden. Dieses ist statisch und befindet sich im Manager und kann durch <code>Manager.rb</code> angesprochen werden. <code>ResourceBundle</code> besitzt eine Funktion <code>getString(String)</code>, diese Methode gibt bei Angabe eines Keys einen entsprechend lokalisierten String zurück.
</p>
<em>Beispiel:</em> <br>
```java
System.out.println(Manager.rb.getString("StartGame"));
```
Gibt als Ausgabe <code>Spiel beitreten</code> wenn die Sprache auf Deutsch gestellt ist und <code>Join game</code> wenn die Sprache auf English gestellt ist.

##### HTML
<p>
    Zur Lokalisierung der HTML-Seiten verwenden wir ein eigenes System welches durch unseren AssetsLoader realisiert wird. Dieser durchsucht die Dateien On-the-fly nach dem Vorkommen dieser Symbole <code>##</code> und ersetzt dann den zwischen diesen Symbolen vorkommenden Text durch den passenden Eintrag im <code>ResourceBundle</code>.
</p>
<em>Beispiel:</em>

```html
##JoinGame## 
```
Wird beim laden automatisch ersetzt durch: <code>Spiel beitreten</code> wenn die Sprache auf Deutsch gestellt ist und durch <code>Join game</code> wenn die Sprache auf English gestellt ist.

<p>
    Bei der Verwendung dieser Art der Lokalisierung ist darauf zu achten dass auf diese Weise __nur__ die HTML-Seiten welche durch unseren AssetsLoader geladen werden (also als Datei von der Festplatte geladen werden) lokalisiert werden! Sämtliche andere Strings welche dem Server per Java-code übergeben werden __müssen__ wie im vorherigen Punkt beschrieben lokalisiert werden.
</p>

## Erstellung eigener Spiele
<p>
    Da unser Framework sehr generisch gehalten ist können neue Spiele mit wenig 
</p>
### Aufbau
![Aufbau Model](images/Model.png)
<em>Prinzipieller Aufbau des Models</em>
<p>
    Im Paket <code>thegamebrett.model</code> sind die die Klassen zusammengefasst auf welchen die Spiele basieren müssen damit das Framework mit ihnen arbeiten kann.
</p>

#### Model
Klasse: <code>thegamebrett.model.Model</code>
<p>
    Die Klasse Model ist die Klasse über die der <em>Manager</em> auf das Spiel zugreift und unter welcher die weiteren Klassen des Spieles organisiert sind. Es ist nicht nötig (aber erlaubt) von ihr abzuleiten. Objekte dieser Klasse werden innerhalb der <code>GameFactory</code>-Klasse erzeugt.
</p>

#### Player
Abstrakte Klasse: <code>thegamebrett.model.Player</code>
<p>
    Die Klasse Player ist die Klasse welche einen Spieler im Spiel repräsentiert. Die Klasse ist abstrakt obwohl sie keine abstrakten Methoden enthält und muss somit vererbt werden. Der Grund hierfür ist dass die Logik welche dem Spieler die Figuren zuordnet in dieser Klasse bestimmt werden soll. Die Zuordnung von Figuren kann entweder berechnet werden indem die Methode <code>public Figure[] getFigures();</code> überschrieben wird oder indem die ArrayList <code>ArrayList<Figure> figures</code> befüllt wird.
</p>

#### Elemente
##### Element
Abstrakte Klasse: <code>thegamebrett.model.elements.Element</code>
<p>
    Diese Klasse dient als Vater aller Komponenten welche die UI zeichnen kann. <code>Element</code> erweitert alle Klassen um eine Funktion <code>public void registerChange()</code> diese Funktion muss zwingend aufgerufen werden wenn eine Eigenschaft der Klasse verändert wird welche für die UI interessant sein könnte. Wird diese Funktion __nicht__ aufgerufen werden die vom Model abhängigen UI-Komponenten auch __nicht__ aktualisiert.
</p>

##### Koordinatensystem
<p>
    Bevor wir auf den Aufbau der einzelnen <em>Elemente</em> eingehen gibt es noch ein paar Dinge zu beachten welches das ihn ihnen verwendete Koordinatensystem angeht. Der Bereich der Koordinaten geht von 0 bis 1. Der Punkt der sich unten rechts befindet ist also <em>(1, 1)</em> und der Punkt welcher sich oben links befindet ist also <em>(0, 0)</em>. Hierbei ist zu beachten ist dass dies nicht dem Punkt <em>(0, 0)</em> des Displays entsprechen muss. Mehr dazu im folgendem Abschnitt über die Klasse <code>Board</code>.
</p>

##### Board
Abstrakte Klasse: <code>thegamebrett.model.elements.Board</code>
<p>
    Diese Klasse repräsentiert im Model das Spielbrett im eigentlichen Sinne, also die Unterlage auf der sich die Felder und die Figuren befinden. Die Klasse ist abstrakt und muss somit zwingend vererbt werden. Zusätzlich müssen folgende Methoden implementiert werden:
</p>

Soll die Anzahl der Felder zurück welche sich auf dem Brett befinden:
```java
public abstract int getFieldLength();
```

Soll das Feld mit dem index i zurückgeben
```java
public abstract Field getField(int i);
```

Soll das Layout des Bretts zurückgeben (später mehr dazu):
```java
public abstract Layout getLayout();
```

Soll Ratio des Brettes zurückgeben. das Verhältnis zwischen X und Y gibt das Verhältnis zwischen Breite und Höhe an. Ein Ratio 1 zu 1 entspricht einem Quadratischen Spielfeldes. Das Koordinatensystem ist von dieser Angabe abhängig:
```java
public abstract float getRatioX();
public abstract float getRatioY();
```

##### Field
Abstrakte Klasse: <code>thegamebrett.model.elements.Field</code>
<p>
    Diese Klasse repräsentiert im Model ein Spielfeld. Die Klasse ist abstrakt und muss somit zwingend vererbt werden. Zusätzlich müssen folgende Methoden implementiert werden:
</p>

Soll relative horizontale und vertikale Position zurück liefern:
```java
public abstract RelativePoint getRelativePosition();
```

Soll relative Breite zurückgeben (Wert ∈ [0d, 1d]):
```java
public abstract double getWidthRelative();
```

Soll relative Höhe zurückgeben (Wert ∈ [0d, 1d]):
```java
public abstract double getHeightRelative();
```

Soll nächstes Feld zurückgeben:
```java
public abstract Field[] getNext();
```

Soll vorhergehendes Feld zurückgeben:
```java
public abstract Field[] getPrevious();
```

Soll das Layout des Feldes zurückgeben (später mehr dazu):
```java
public abstract Layout getLayout();
```

##### Figure
Klasse Figure: <code>thegamebrett.model.elements.Figure</code>
<p>
    Die Klasse <code>Figure</code> ist die Klasse welche im Model eine Spielfigur darstellt. Sie hat einen Besitzer (<code>Player</code>), ein <em>Layout</em> sowie eine relative Breite und Höhe (∈ [0d, 1d]). Die Klasse ist nicht abstrakt und somit muss man sie nicht vererben. Jedoch ist dies für viele Spiele anzuraten, da dies der <em>Spielelogik</em> helfen kann diesen zusätzliche Informationen zu geben.
</p>

#### Layout
Klasse Layout: <code>thegamebrett.model.elements.Figure</code>
<p>
    Diese Klasse definiert das Aussehen von Elementen. Sie hat viele optionale Werte die gesetzt werden können um einem Element ein bestimmtes Aussehen zu verpassen. Hierfür kann Form, Bild, Text, Schriftgrösse, Textur, Hintergrundfarbe, Rand, Randgrösse dieser festgelegt werden. Die Grafikmodul (<code>GUILoader</code>) arbeitet generisch und kann somit diese Attribute allen Elementen gleichermassen verleihen.
</p>

#### GameFactory
Interface: <code>thegamebrett.model.GameFactory</code>
<p>
    Die Klasse die <code>Gamefactory</code> implementiert ist die Klasse welche Spiele erstellen kann. Hierfür muss diese Klasse eine Reihe von Funktionen bereit stellen welche von aussen Informationen über das Spiel bereit stellen und die Möglichkeit bieten ein Model des Spieles zu erzeugen. Folgende Methoden müssen implementiert werden:
</p>

Soll das Icon des Spieles zurückgeben:
```java
public Image getGameIcon();
```

Soll den Namen des Spieles zurückgeben:
```java
public String getGameName();
```

Soll die maximale bzw. minimale Anzahl an Spielern zurück geben:
```java
public int getMaximumPlayers();
public int getMinimumPlayers();
```

Soll eine Instanz des Spieles erzeugen (<code>Model</code>-Objekt):
```java
public Model createGame(ArrayList<User> users) throws TooMuchPlayers, TooFewPlayers;
```

#### GameLogic
Abstrakte Klasse: <code>thegamebrett.model.GameLogic</code>
<p>
    Diese Klasse ist die Klasse welche für die Logik im Spiel zuständig ist. In ihr wird der Ablauf des Spieles gesteuert. Sie ist abstrakt und muss somit zwingend vererbt werden. Ausserdem müssen zwei Methoden implementiert werden:
</p>
Diese Methode soll die Startpositionen der Spieler bestimmen
```java
public abstract Field getNextStartPositionForPlayer(Player player);
```
Diese Methode ist die wichtigste Methode im Model. Sie entscheidet den Verlauf des Spieles. Sie bekommt als Eingabe immer eine <em>Response</em> und gibt als Rückgabewert eine Liste von <em>Requests</em> zurück:
```java
public abstract ActionRequest[] next(ActionResponse as);
```

#### Requests und Responses
<p>
    Requests und Responses sind das was das Spiel am laufen behält. Es geht nie eine Interaktion vom Spiel aus. Die Kommunikation erfolgt immer nach dem Ping-Pong-Model. Das Model ist nur aktiv wenn es angestossen wird.
</p>
##### GUI
Stösst die UI an sich zu aktualisieren:
```java
thegamebrett.action.request.GUIUpdateRequest(int value, boolean animated, int delay)
```
Eine Aufruf kann so aussehen:
```java
new GUIUpdateRequest(GUIUpdateRequest.GUIUPDATE_FIELDS+GUIUpdateRequest.GUIUPDATE_FIGURES, true, 0)
```

##### Interaction
Verlangt eine Interaction von einem bestimmten Spieler:
```java
thegamebrett.action.request.InteractionRequest(String titel, Object[] choices, Player player, boolean hidden, String acknowledgment, int delay, Object userData)
```
Wenn dieser Antwortet kommt ein Objekt vom Typ <code>InteractionResponse</code> zurück

##### Messages
Zeigt Nachricht für alle sichtbar auf dem Spielbrett an. Nachricht ist in Richtung des angegebenen Spielers gedreht:
```java
thegamebrett.action.request.ScreenMessageRequest(String label, Player player)
```
Nachricht verschwindet erst wenn eine neue Nachricht geschickt wird oder wenn eine <code>RemoveScreenMessageRequest</code> zurück gegeben wird.

##### Sound
Spielt Sound ab. Sound muss im <code>wav</code>-Format vorliegen. Als Parameter muss ein Objekt vom Typ thegamebrett.model.mediaeffect.SoundEffect übergeben werden:
```java
thegamebrett.action.request.PlaySoundRequest(SoundEffect sound)
```
Sound stoppt erst wenn Sound zu Ende oder wenn <code>StopSoundsRequest</code> zurück gegeben wird. Falls im SoundEffect angegeben ist dass sich dieser für immer wiederholt stoppt dieser erst bei <code>StopSoundsRequest</code> oder wenn Spiel beendet wird.

##### Timer
Da das gesamte Framework darauf aufgebaut ist dass die Spielelogik immer erst anläuft wenn eine Response für diese vorliegt ist es in manchen Situationen sinnvoll einen Timer zu starten:
```java
thegamebrett.action.request.TimerRequest(int millis)
```
Dieser Timer hat die Eigenschaft nach einer bestimmten Anzahl von Sekunden eine <code>TimerResponse</code> zurück zu geben. Auf diese Art kann das Framework sich nach Ablauf einer bestimmten Zeit wieder selbst anstossen. (Auf diese Art und Weise sind somit auch Schleifen möglich wenn man immer ein <code>TimerRequest</code> zurück gibt)

##### Spielstart und Spielende 
<p>
    Spielstart und Spielende  werden auch über <em>Requests</em> und <em>Responses</em> geregelt. Beim Starten des Spieles erhält die <em>GameLogik</em> eine StartPseudoResponse. Diese enthält keine Daten und dient nur dazu das Spiel anzustossen.
</p>
<p>
    Das Spiel ist dann beendet wenn die <em>GameLogik</em> eine <code>GameEndRequest</code> zurückgibt.
</p>
```java
thegamebrett.action.request.GameEndRequest(Player[] winner, String acknowledgment, int delay, Image backgroundImage)
```

### Gamecollection
<p>
    Die GameCollection ist die Klasse in der alle Spiele eingetragen werden müssen damit sie vom Framework erkannt werden. Von jedem existierenden Spiel muss sich eine Instanz der entsprechenden GameLogik befinden.
</p>
```java
public static GameFactory[] gameFactorys = {
        new D_GameFactory(), new PSS_GameFactory()
    };
```

### Beispiele
Als Beispiel-Implementierung haben wir 3 Spiele realisiert. Diese sind unter <code>thegamebrett.game.MADN.*</code>, <code>thegamebrett.game.KFSS.*</code>, <code>thegamebrett.game.PSS.*</code> sowie <code>thegamebrett.game.dummy.*</code> zu finden. Der <em>Dummy</em> dient hierbei einfach als Beispiel einer minimalen Version eines Spieles und lässt sich gut als Grundlage für neue Spiele verwenden.

## Anlegen eigener Character
<p>
    Eigene Character können ganz leicht angelegt werden. Hierzu muss die Datei <code>assetsfolder/characters.csv</code> um eine weitere Zeile erweitert werden.
</p>
<em>Beispiel:</em>
<pre>
Rot;#FF3333;tod-red.png
Blau;#0080FF;tod-blue.png
Green;#00CC00;tod-green.png
</pre>
