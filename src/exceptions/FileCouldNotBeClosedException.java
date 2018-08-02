package exceptions;

public class FileCouldNotBeClosedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public FileCouldNotBeClosedException() {
		
	}
	
	public FileCouldNotBeClosedException(String path) {
		super(path);
	}

}
