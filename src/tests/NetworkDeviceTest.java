package tests;

import thegamebrett.network.httpserver.HttpServer;
import thegamebrett.network.httpserver.RootDirectoryBasedDirector;

import java.util.Scanner;
import thegamebrett.network.User;
import thegamebrett.network.UserManager;
import thegamebrett.network.ControlDirector;

/**
 *
 * @author christiancolbach
 */
public class NetworkDeviceTest {
    
    //public static String usertext = "";
    public static void main(String[] args) {
        HttpServer server = new HttpServer(8116, new ControlDirector(new UserManager()));
        server.enableServer();
        
        /*Scanner sc = new Scanner(System.in);
        System.out.println("Eingeben :D");
        while(sc.hasNextLine()) {
            usertext = sc.nextLine();
        }*/
    }
}
