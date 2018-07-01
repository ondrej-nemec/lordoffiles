package exceptions;

import java.io.IOException;

public class FileCouldNotBeClosedException extends IOException{

	private static final long serialVersionUID = 1L;
	
	public FileCouldNotBeClosedException() {
		
	}
	
	public FileCouldNotBeClosedException(String path) {
		super(path);
	}

}
