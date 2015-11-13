package thegamebrett.network.httpserver;

/**
 * @author Christian Colbach
 */
public class SingleStringBasedDirector implements Director {

    private String html;
    
    public SingleStringBasedDirector(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
    
    
    public Object query(String request) {
            return html;
    }
	
}
