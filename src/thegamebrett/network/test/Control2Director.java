package thegamebrett.network.test;

import thegamebrett.network.httpserver.RootDirectoryBasedDirector;

/**
 *
 * @author christiancolbach
 */
public class Control2Director extends RootDirectoryBasedDirector {

    public Control2Director(String rootDirectory) {
        super(rootDirectory);
    }
    
    @Override
    public Object query(String request) throws NotFoundException {
        System.out.println(request);
        return super.query(request);
    }
    
}
