/*$(document).ready(function () {
    //$("#refresh").load("refresh?start");
    update();
    var refreshId = setInterval(function () {
        update();
        //$("#refresh").load('refresh?' + 1 * new Date());
    }, 2000);
});*/

var backgroundColor = '#B70E79'; // Angegebene Farbe ist Default

var lastContentID = "000000000"; // 000000000 bedeutet dass es keine aktuelle gibt

function reply(answer) {
    //$("#refresh").load("reply?" + answer);
    var url = "reply?" + answer;
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();

    document.getElementById("refresh").innerHTML = "";
}
function updatePositionChooser() {
    var url = "getPositionsInfo";
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    response.onreadystatechange = function () {
        if (response.readyState === 4 && response.status === 200) {
            var comps = response.responseText.split(" ");
            for(var i=0; i<comps.length; i++) {
                if(comps[i] === "ON") {
                    document.getElementById("button" + i).disabled = false;
                    document.getElementById("button" + i).innerHTML = "<img src=\"position-enabled.png\" alt=\"Sitzplatz\" class=\"posImage\">";
                    
                } else {
                    document.getElementById("button" + i).disabled = true;
                    document.getElementById("button" + i).innerHTML = "<img src=\"position-disabled.png\" alt=\"Sitzplatz\" class=\"posImage\">";
                }
            }
            
        }
    };
}

function updateCharacterChooser() {
    var url = "getCharacterInfo";
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    response.onreadystatechange = function () {
        if (response.readyState === 4 && response.status === 200) {
            var comps = response.responseText.split(" ");
            for(var i=0; i<comps.length; i++) {
                if(comps[i] === "ON") {
                    document.getElementById("chooseCharacterButton" + i).hidden = false;
                } else {
                    document.getElementById("chooseCharacterButton" + i).hidden = true;
                }
            }
            
        }
    };
}

function checkAndDoRedirect(actualPage) {
    var url = "needsRedirect?" + actualPage;
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    var w = window;
    response.onreadystatechange = function () {
            if (response.readyState === 4 && response.status === 200) {
            console.log("response.responseText=" + response.responseText);

            if (response.responseText === "YES") {
                console.log("Seite muss neu geladen werden");
                direct();
            } else {
                console.log("Seite korrekt");
            }
        }
    };
}

function tryToStartGame() {
    var url = "tryToStartGame";
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    var w = window;
    response.onreadystatechange = function () {
            if (response.readyState === 4 && response.status === 200) {
            console.log("response.responseText=" + response.responseText);

            if (response.responseText === "YES") {
                console.log("Spiel startet");
                direct();
            } else {
                console.log("Spiel kann nicht gestartet werden");
            }
        }
    };
}

function tryToJoinGame() {
    var url = "tryToJoinGame";
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    var w = window;
    response.onreadystatechange = function () {
            if (response.readyState === 4 && response.status === 200) {
            console.log("response.responseText=" + response.responseText);

            if (response.responseText === "YES") {
                console.log("Spiel beigetreten");
                direct();
            } else {
                console.log("Spiel kann nicht beigetreten werden");
            }
        }
    };
}

function tryToCreateGame(index, name) {
    if (confirm("Willst du " + name + " erstellen? Weitere Spieler müssen dem Spiel beitreten damit das Spiel starten kann.")) {
        console.log("ja");
        
        var url = "tryToCreateGame?" + index;
        var response = new XMLHttpRequest();
        response.open("GET", url, true);
        response.send();
        var w = window;
        response.onreadystatechange = function () {
                if (response.readyState === 4 && response.status === 200) {
                console.log("response.responseText=" + response.responseText);

                if (response.responseText === "YES") {
                    console.log("Spiel erstellt");
                    direct();
                } else {
                    console.log("Spiel kann nicht erstellt werden");
                }
            }
        };
    } else {
        console.log("nein");
    }
    /*
    var url = "tryToGetCharacter?" + index;
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    var w = window;
    response.onreadystatechange = function () {
            if (response.readyState === 4 && response.status === 200) {
            console.log("response.responseText=" + response.responseText);

            if (response.responseText === "YES") {
                direct();
            } else {
                updateCharacterChooser();
            }
        }
    };*/

}

function tryToGetCharacter(index) {    
    var url = "tryToGetCharacter?" + index;
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    var w = window;
    response.onreadystatechange = function () {
            if (response.readyState === 4 && response.status === 200) {
            console.log("response.responseText=" + response.responseText);

            if (response.responseText === "YES") {
                direct();
            } else {
                updateCharacterChooser();
            }
        }
    };

}

function refreshStartGameInfoText() {
    var url = "refreshStartGameInfoText";
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    response.onreadystatechange = function () {
        if (response.readyState === 4 && response.status === 200) {
            if (response.responseText !== "null") {
                document.getElementById("refresh").innerHTML = response.responseText;
            }
        }
    };
}

function update() {
    console.log("lastContentID=" + lastContentID);
    var url = "refresh?" + lastContentID;
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    response.onreadystatechange = function () {
        if (response.readyState === 4 && response.status === 200) {

            if (response.responseText === "direct") {
                direct();
            } else if (response.responseText !== "null") {

                var contentID = response.responseText.substr(0, 9);
                lastContentID = contentID;
                var contentText = response.responseText.substr(9);

                document.getElementById("refresh").innerHTML = contentText;

                console.log("update " + contentID);
            } else {
                console.log("no update");
            }
        }
    };
}

function tryToLogIn(position) {    
    var url = "tryToLogIn?" + position;
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    var w = window;
    response.onreadystatechange = function () {
            if (response.readyState === 4 && response.status === 200) {
            console.log("response.responseText=" + response.responseText);

            if (response.responseText === "YES") {
                console.log("Position verfuegbar");
                direct();
            } else {
                updatePositionChooser();
            }
        }
    };

}

function logOut() {
    var url = "logOut";
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    direct();
}

function direct() {
    window.location = "";
}

function startGame() {
    /*hier muss geprueft werden welches Spiel gewaehlt ist und dieses dann mit allen spielern gestartet werden*/
    window.location = "ingame.html";
}
function returnToCharacter() {
    /*hier muss zum choose character zurück gesprungen werden. wichtig ist, dass falls ein neuer character erstellt wurde, der nun auch zur auswahl steht.*/
    //window.location = "back";
    logOut();
}
function setBackgroundColor(backgroundcolor) {
    /*hier die farbe für jeden spieler anpassen. gibt es bei google was zu :D*/
    document.getElementsByTagName("body")[0].style.backgroundColor = backgroundColor;
}
function createCharacter() {
    window.location = "createCharacter.html";
}

function loadUserColor() {
    
    var url = "getUserColor";
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    response.onreadystatechange = function () {
        if (response.readyState === 4 && response.status === 200) {
            
            console.log("userColor=" + response.responseText);
            
            if (response.responseText !== "NONE") {
                console.log("Keine Farbe gesetzt");
                return backgroundColor;
            } else {
                backgroundColor = response.responseText;
                return response.responseText;
            }
        }
    };
}