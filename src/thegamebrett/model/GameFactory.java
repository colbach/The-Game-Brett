package thegamebrett.model;

/**
 * Dieses Interface dient der erzeugung des Models der einzelnen spezifischen Spiele
 * 
 * @author Christian Colbach
 */
public interface GameFactory {
    
    /** generiert Model mit allem drum und dran */
    public Model createGame();
}
