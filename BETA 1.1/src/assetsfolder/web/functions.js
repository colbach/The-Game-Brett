/*$(document).ready(function () {
    //$("#refresh").load("refresh?start");
    update();
    var refreshId = setInterval(function () {
        update();
        //$("#refresh").load('refresh?' + 1 * new Date());
    }, 2000);
});*/

var backgroundColor = '#B70E79';        
var lastContentID = "#########";

function reply(answer) {
    //$("#refresh").load("reply?" + answer);
    var url = "reply?" + answer;
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();

    document.getElementById("refresh").innerHTML = "";
}

function update() {
    //var url = 'refresh?' + 1 * new Date();
    var url = 'refresh?' + lastContentID;
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    response.onreadystatechange = function () {
        if (response.readyState === 4 && response.status === 200) {

            if (response.responseText !== "null") {

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

function choosePosition(answer) {    
    var url = "tryToChoosePosition?" + answer;
    var response = new XMLHttpRequest();
    response.open("GET", url, true);
    response.send();
    var w = window;
    response.onreadystatechange = function () {
            if (response.readyState === 4 && response.status === 200) {
            console.log("response.responseText=" + response.responseText);

            if (response.responseText === "YES") {
                console.log("Position verfuegbar");
                window.location = "createCharacter.html";
            } else {
                $("#refresh").load("refreshPositions");
            }
        }
    };

}

function startGame() {
    /*hier muss geprueft werden welches Spiel gewaehlt ist und dieses dann mit allen spielern gestartet werden*/
    window.location = "ingame.html";
}
function returnToCharacter() {
    /*hier muss zum choose character zurück gesprungen werden. wichtig ist, dass falls ein neuer character erstellt wurde, der nun auch zur auswahl steht.*/
    window.location = "chooseCharacter.html";
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
                return "#FFFFFF";
            } else {
                return response.responseText;
            }
        }
    };
    
}
