# The-Game-Brett
Teamprojekt Hochschule Trier 2015-2016
Entwicklung und Umsetzung eines digitalen Spielbretts für Gemeinschaftsspiele
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
![Grundsätzlicher Aufbau](/Grundsaetzlicher Aufbau Projekt.png)
<em>Grundsätzlicher Aufbau des Projektes</em>

### Model View Controller

#### Der Manager (Controller)

#### Grafik (View #1)

#### Webinterface (View #2)

#### Spielelogik (Model #1)

#### Userstate (Model #2)

#### Kommunikation
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
#### Ausreisser

##### Serverseitig
##### Clientseitig
### Assets

### Lokalisierung
Für die Lokalisierung unserer Anwendung verwenden wir das Java-eigene Lokalisierungsframework <code>java.util.ResourceBundle</code>. Dieses erlaubt es Sprachunabhängige Strings zu definieren und diese dann in verschiedene Sprachen zu übersetzen.

#### Verwendung durch den Endbenutzer
Die Einstellung der Sprache kann in der UI im Menu vorgenommen werden. Hierzu klickt man auf den Button [Optionen] uns stell diese dann unter den Spracheinstellungen ein.

#### Verwendung durch den Programmierer
Bei der Verwendung unterscheidet unterscheiden wir im folgenden zwischen der Lokalisierung des Java-codes und der Lokalisierung der HTML-Seiten. Hierzu zu erwähnen ist ausserdem dass alle Strings welche verwendet werden sollen in der entsprechenden <code>properties</code>-Datei angegeben sein müssen. Für English in der Datei <code>languages_en.properties</code> und für Deutsch <code>languages_de.properties</code>.

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
##### Einbinden neuer Sprachen

## Erstellung eigener Spiele

### Aufbau
#### Model
#### Player
#### Elemente
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
### Beispiele
## Anlegen eigener Character