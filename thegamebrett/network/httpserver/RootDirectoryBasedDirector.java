package thegamebrett.network.httpserver;

import java.io.File;
import java.net.URLDecoder;


/**
 * @author Christian Colbach
 */
public class RootDirectoryBasedDirector implements Director {

	public class NotFoundException extends QueryException {
		@Override
		public int getStatusCode() {
			return HttpResponseHeader.STATUSCODE_NOT_FOUND;
		}
		@Override
		public Object getErrorDocument() {
			return "<b> Error 404 (Not Found) </b>";
		}
	}

	private String rootDirectory;
	
	public RootDirectoryBasedDirector(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	public Object query(String request) throws NotFoundException {
		
		if(request.equals("/")) {

			if(new File(rootDirectory + "/index.html").exists()) {
				
				return new File(rootDirectory + "index.html");
				
			} else {

				return "<center> <h1> Hello here is Game Brett ! </h1> </center>";
			}

		} else {
			
			File file = new File(rootDirectory + URLDecoder.decode(request));
			
			if(new File(rootDirectory + request).isFile()) {
				
				return file;
				
			} else {
				
				throw new NotFoundException();
			}
		}
	}
}
