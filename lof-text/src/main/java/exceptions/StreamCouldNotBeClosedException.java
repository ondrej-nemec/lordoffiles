package exceptions;

import java.io.IOException;

public class StreamCouldNotBeClosedException extends IOException{

	private static final long serialVersionUID = 1L;
	
	public StreamCouldNotBeClosedException() {}
	
	public StreamCouldNotBeClosedException(final String path) {
		super("File: " + path);
	}

}
