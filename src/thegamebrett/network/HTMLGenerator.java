package thegamebrett.network;

/**
 *
 * @author christiancolbach
 */
public class HTMLGenerator {
    
    public final static String HTML_TOP = "<!DOCTYPE html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"><link href=\"style.css\" type=\"text/css\" rel=\"stylesheet\"><title>The-Game-Brett</title>"
                  + "<script type=\"text/javascript\">\n" +
                    "     $(document).ready(function() {\n" +
                    "       $(\"#refresh\").load(\"refresh.php\");\n" +
                    "       var refreshId = setInterval(function() {\n" +
                    "          $(\"#refresh\").load('refresh.php?' + 1*new Date());\n" +
                    "       }, 1000);\n" +
                    "    });\n" +
                    "</script>"
            + "</head>";
    public final static String HTML_START = "<body><div align=\"center\" id=\"content\">";
    public final static String HTML_END = "</div></body></html>";
    
    /** generieren von index.html */
    public static String generateHTML(String titel, String[] choices) {
        // hier muss etwas generiert werden
        
        return HTML_TOP + HTML_START +
                "Hallo Welt\n"
                + "<div id=\"refresh\" style=\"text-align:center;\"></div>" +
                "<div align=\"center\"><button class=\"buttons\">^</button></div>\n" +
                    "<div align=\"center\">\n" +
                    "<button class=\"buttons\"><</button>\n" +
                    "<button class=\"buttons\">+</button>\n" +
                "<button class=\"buttons\">></button></div>\n" +
                "<div align=\"center\"><button class=\"buttons\">v</button></div>" + HTML_END;
        
    }
    
    /** style.css */
    public final static String CSS = "*{ padding:0; margin:0; }\n" +
        "body{ font-family: 'Open Sans', Arial, sans-serif; width:100%; }\n" +
        "main{ margin: 0 auto; }\n" +
        "#content{ padding:5px; margin-left:5px; }\n" +
        ".buttons{  padding:5px; margin:5px; width:35px; height:35px; }";
}
