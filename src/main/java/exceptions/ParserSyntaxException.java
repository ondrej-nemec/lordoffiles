package exceptions;

public class ParserSyntaxException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ParserSyntaxException() {}
	
	public ParserSyntaxException(String message) {
		super(message);
	}
	
	
	public ParserSyntaxException(String format, String message) {
		super("Parser: " + format + ", " + message);
	}

}
