
$(document).ready(function() {
	$("#refresh").load("refresh?start");
	var refreshId = setInterval(function() {
		$("#refresh").load('refresh?' + 1 * new Date());
	}, 2000);
});

function reply(answer) {
	$("#refresh").load("reply?" + answer);
}
function startGame(){
	/*hier muss geprueft werden welches Spiel gewaehlt ist und dieses dann mit allen spielern gestartet werden*/
	window.location = "ingame.html";
}
function returnToCharacter(){
	/*hier muss zum choose character zurück gesprungen werden. wichtig ist, dass falls ein neuer character erstellt wurde, der nun auch zur auswahl steht.*/
	window.location = "chooseCharacter.html";
}
