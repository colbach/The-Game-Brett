package thegamebrett.network.httpserver;

import java.io.File;
import java.util.HashMap;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class Mimes {

    private static final MimeMap MIMEMAP = new MimeMap();

    public static String getMime(String name) {
        if (name.equals("/")) {
            return (String) MIMEMAP.get(".html"); // fuer den Fall dass index.html automatisch zurückgegeben wird
        } else {
            return (String) MIMEMAP.get(getExtension(name));
        }
    }

    public static String getMime(File file) {
        return getMime(file.getName());
    }

    private static String getExtension(String name) {
        String extension = "";
        int pos = name.lastIndexOf(".");
        if (pos != -1) {
            extension = name.substring(pos);
        }
        return extension.toLowerCase();
    }

    private static class MimeMap extends HashMap {

        public MimeMap() {
            super();

            // web
            put(".htm", "text/html");
            put(".html", "text/html");
            put(".css", "text/css");
            put(".js", "text/javascript");

            // text
            put(".text", "text/plain");
            put(".txt", "text/plain");
            put(".java", "text/plain");

            // executable
            put(".exe", "application/octet-stream");

            // archives
            put(".zip", "application/zip");
            put(".tar", "application/x-tar");

            // image
            put(".gif", "image/gif");
            put(".jpg", "image/jpeg");
            put(".jpeg", "image/jpeg");
            put(".png", "image/png");

            // audio
            put(".wav", "audio/x-wav");
            put(".mp3", "audio/mpeg");

            // video
            put(".avi", "video/x-msvideo");
            put(".wmv", "video/x-msvideo");
            put(".mpeg", "video/mpeg");
            put(".mpg", "video/mpeg");
            put(".mpe", "video/mpeg");

            // document
            put(".doc", "application/msword");
            put(".dot", "application/msword");
            put(".pdf", "application/pdf");
            put(".ppt", "mspowerpoint");
            put(".ppz", "mspowerpoint");
            put(".pps", "mspowerpoint");
            put(".pot", "mspowerpoint");
            put(".rtf", "application/rtf");

        }

        @Override
        public Object get(Object o) {
            if (containsKey(o)) {
                return super.get(o);
            } else {
                return "content/unknown"; // Sollte eigentlich "application/octet-stream" sein
            }
        }
    }
}
