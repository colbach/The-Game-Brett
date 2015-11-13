

import java.io.File;
import thegamebrett.network.httpserver.*;


/**
 * @author Christian Colbach
 */
public class TestClass {

	//public static HttpServer manager = new HttpServer(8124, new RootDirectoryBasedDirector("/Users/christiancolbach/Desktop/root"));
	
	
        
        
        public static void main(String[] args) {
		
            
            
            //HttpServer server = new HttpServer(8031, new TestDirector());
            
            //HttpServer server = new HttpServer(8126, new TestDirector());
            HttpServer server = new HttpServer(8112, new RootDirectoryBasedDirector("/Users/christiancolbach/Desktop/root"));
            
            server.enableServer();
            // christian@colba.ch
		
	}
	
}
