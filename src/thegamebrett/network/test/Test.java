package thegamebrett.network.test;

import thegamebrett.network.httpserver.HttpServer;
import thegamebrett.network.httpserver.RootDirectoryBasedDirector;

import java.util.Scanner;
import thegamebrett.network.Client;
import thegamebrett.network.ClientManager;
import thegamebrett.network.ControlDirector;

/**
 *
 * @author christiancolbach
 */
public class Test {
    
    //public static String usertext = "";
    public static void main(String[] args) {
        HttpServer server = new HttpServer(8116, new ControlDirector(new ClientManager()));
        server.enableServer();
        
        /*Scanner sc = new Scanner(System.in);
        System.out.println("Eingeben :D");
        while(sc.hasNextLine()) {
            usertext = sc.nextLine();
        }*/
    }
}
