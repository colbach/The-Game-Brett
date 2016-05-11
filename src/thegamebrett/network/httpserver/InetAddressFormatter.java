package thegamebrett.network.httpserver;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class InetAddressFormatter {

    /** Wandelt InetAddress in Menschenlesbare IP-Adresse um */
    public static String formatAddress(InetAddress ip) {
        try {
            return (InetAddress.getLocalHost().equals(ip)
                    || ip.toString().startsWith("/0:0:0:0:0:0:0")
                            ? "localhost" : ip.toString().substring(1));
        } catch (UnknownHostException uhe) {
            return "Unknown";
        }
    }

}
