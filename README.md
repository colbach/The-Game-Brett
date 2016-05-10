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
$ java thegamebrett.gui.GUIApplication /Users/christiancolbach/Documents/gamebrett/classes/assetsfolder/
</pre>

## Aufbau des Frameworks
![Grundsätzlicher Aufbau](images/Grundsaetzlicher Aufbau Projekt.png)
<em>Prinzipieller Aufbau des Projektes</em>

### Model View Controller
Mit einer Trennung zwischen Spielelogik und IO versuchen wir in unser Framework stark MVC-orientiert umzusetzen. Dies hat den Vorteil dass die Spiele komplett austauschbar sind ohne dass dafür eine Zeile Code im Rest des Systems geändert werden muss.

#### Der Manager (Controller)
<p>
    Der Manager stellt sozusagen den "Dreh und Angelpunkt" der Anwendung dar. Über ihn laufen alle Komunikationen und er verbindet alles mit allem (direkt oder indirekt).
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
    Das <em>Webinterface</em> stellt in unserem Aufbau die Grundlage aller Komunikationen mit dem Spieler. Jeder Spieler muss sich über sein Handy (/anderes internetfähiges Gerät) an unserem Server anmelden. Von diesem bleibt er dann mit dem System verbunden und interagiert mit dem System und den Spielen.
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
    Die Komunikation zwischen den einzelnen Modulen (Model, Grafik, Webinterface, Spielelogik) findet über einen selbst implementiertes System aus <em>Actions</em> stadt. Grundlage hierfür stellen die Interfaces <code>ActionRequest</code> und <code>ActionResponse</code> von welchen es eine Reihe von Ableitungen gibt und welche durch das gesammte System durchgereicht werden. Hierbei läuft das immer so ab dass der Manager <em>Requests</em> bei der Spielelogik abholt und diese dann an das gewünschte System weitergeibt (z.B. Netzwerk oder UI). Diese Systeme generieren dann zu diesen passende <em>Responses</em> welche dann wieder an den Manager abgegeben werden. Hierzu zu beachten ist dass die Spielelogik immer nur beim Anstoss durch den Manager aktiv wird und ihre nächste <em>Requests</em> generieren. Ein solcher Anstoss geschieht nur wenn eine <em>Resonse</em> an die Spielelogik zurück geht. Um diesen Aufbau zu verstehen sind folgende Methoden relevant:
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
    Unsere Brett ist so konzipiert dass eine Steuerung mittels Maus oder Touchscreen nicht nötig ist. Es reicht wenn das Gamebrett mit dem WLAN verbunden ist beziehungsweise einen eingebauten Access Point besitzt. Das Menue und die Spiele werden über die Handhelds welche mit dem Brett verbunden sind gesteuert.
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
    In Java können Strings durch die direkte Verwendung des <code>ResourceBundle</code>-Ojektes lokalisiert werden. Dieses ist statisch und befindet sich im Manager und kann durch <code>Manager.rb</code> angesprochen werden. <code>ResourceBundle</code> besitzt eine Funktion <code>getString(String)</code>, diese Methode gibt bei Angabe eines Keys einen entsprechend lokalisierten String zurück.
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
(<code>thegamebrett.model.Model</code>)
<p>
    Die Klasse Model
</p>

#### Player
#### Elemente
##### Koordinatensystem
##### Interface Element
##### Board
##### Field
##### Figure
#### Layout
#### GameFactory
#### GameLogic
#### Requests und Resposes
##### GUI
##### Interaction
##### Messages
##### Sound
##### Timer
##### Spielende und Spielstart
### Gamecollection
### Beispiele
## Anlegen eigener Character
