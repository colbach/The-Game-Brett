package thegamebrett.network;

/**
 *
 * @author christiancolbach
 */
public class HTMLGenerator {

    public final static String HTML_TOP
            = "<!DOCTYPE html><html>"
            + "<head>"
            + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
            + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">"
            + "<link href=\"style.css\" type=\"text/css\" rel=\"stylesheet\">"
            + "<title>The-Game-Brett</title>"
            + "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js\" type=\"text/javascript\"></script>"
            + "<script type=\"text/javascript\">\n"
            + "     $(document).ready("
            + "        function() {\n"
            + "           $(\"#refresh\").load(\"refresh\");\n"
            + "           var refreshId = setInterval("
            + "              function() {\n"
            + "                 $(\"#refresh\").load('refresh?' + 1*new Date());\n"
            + "              }"
            + "           , 1000);\n"
            + "        }"
            + "     );\n"
            + "     function reply(answer) {"
            + "        $(\"#refresh\").load(\"reply?\" + answer);\n"
            + "     }"
            + "</script>"
            + "</head>";
    
    public final static String HTML_START = "<body><div align=\"center\" id=\"content\">";
    public final static String HTML_END = "</div></body></html>";

    /**
     * generieren von index.html
     */
    public static String generateHTML(String titel, String[] choices, int messageID) {
        // hier muss etwas generiert werden
        
        StringBuilder sb = new StringBuilder(HTML_TOP + HTML_START);
        sb.append("<div id=\"refresh\" style=\"text-align:center;\"></div>");
        //sb.append(titel);
        /*for(int i=0; i<choices.length; i++) {
            sb.append(generateHTMLButton(choices[i], messageID, i));
        }*/
        sb.append(HTML_END);
        return sb.toString();

                 //"<div align=\"center\"><button class=\"buttons\">^</button></div>\n"
                 //"<div align=\"center\">\n"
                 //"<button class=\"buttons\" onclick=\"reply('left')\"><</button>\n"
                 //"<button class=\"buttons\">+</button>\n"
                 //"<button class=\"buttons\">></button>"
                 //"</div>\n"
                 //"<div align=\"center\"><button class=\"buttons\">v</button></div>"
                 //"<div align=\"center\"><button class=\"choices\" onclick=\"reply('ok')\">Alle ok?</button></div>" + HTML_END;
    }
    
    public static String generateHTMLContent(String titel, String[] choices, int messageID) {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>" + titel + "</h1>");
        for(int i=0; i<choices.length; i++) {
            sb.append(generateHTMLButton(choices[i], messageID, i));
        }
        return sb.toString();
    }
    
    private static String generateHTMLButton(String choice, int messageID, int anwerID) {
        return "<div align=\"center\"><button class=\"choices\" onclick=\"reply('" + messageID + "&" + anwerID+ "')\">" + choice + "</button></div>";
    }

    /**
     * style.css
     */
    public final static String CSS = "*{ padding:0; margin:0; }\n"
            + "body{ font-family: 'Open Sans', Arial, sans-serif; width:100%; }\n"
            + "main{ margin: 0 auto; }\n"
            + "#content{ padding:5px; margin-left:5px; }\n"
            + ".buttons{ padding:5px; margin:5px; width:35px; height:35px; }"
            + ".choices{ padding:5px; margin:5px; width:80%; height:50px; }";
}
