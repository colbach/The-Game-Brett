package thegamebrett.network.httpserver;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author christiancolbach
 */
public class InetAddressFormatter {

	public static String formatAddress(InetAddress ip) {
		try {
			return (
				InetAddress.getLocalHost().equals(ip)
					|| ip.toString().startsWith("/0:0:0:0:0:0:0")
				? "localhost" : ip.toString().substring(1)
			);
		} catch(UnknownHostException uhe) {
			return "Unknown";
		}
	}
	
}
