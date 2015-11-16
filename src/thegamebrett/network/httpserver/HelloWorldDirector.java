package thegamebrett.network.httpserver;

/**
 * @author Christian Colbach
 */
public class HelloWorldDirector implements Director {

	public Object query(String request) {
		return "Hello World!";
	}
	
}
