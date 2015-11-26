package thegamebrett.network.test;

import thegamebrett.network.httpserver.HttpServer;
import thegamebrett.network.httpserver.RootDirectoryBasedDirector;

import java.util.Scanner;

/**
 *
 * @author christiancolbach
 */
public class Test {
    
    public static String usertext = "";
    public static void main(String[] args) {
        HttpServer server = new HttpServer(8116, new ControlDirector());
        server.enableServer();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Eingeben :D");
        while(sc.hasNextLine()) {
            usertext = sc.nextLine();
        }
    }
}
