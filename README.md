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
### Model View Controller
### Grafik
### Webinterface
#### Serverseitig
#### Clientseitig
### Assets
### Lokalisierung
Für die Lokalisierung unserer Anwendung verwenden wir das Java-eigene Lokalisierungsframework <code>java.util.ResourceBundle</code>. Dieses erlaubt es Sprachunabhängige Strings zu definieren und diese dann in verschiedene Sprachen zu übersetzen.
#### Verwendung
Bei der Verwendung unterscheidet unterscheiden wir im volgenden zwischen der Lokalisierung des Java-codes und der Lokalisierung der HTML-Seiten. 
Die Einstellung der Sprache kann in der UI im Menu vorgenommen werden. Hierzu klickt man auf den Button [Optionen]
##### Java

##### HTML
<p>
    Zur Lokalisierung der HTML-Seiten verwenden wir ein eigenes System welches durch unseren AssetsLoader realisiert wird. Dieser durchsucht die Dateien On-the-fly nach dem Vorkommen dieser Symbole <code>##</code> und ersetzt dann den zwischen diesen Symbolen vorkommenden Text durch den passenden Eintrag im <code>ResourceBundle</code>.
</p>
<p>
    Ein Beispiel wie dies aussehen könnte:

</p>
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
