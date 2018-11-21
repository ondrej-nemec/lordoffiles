package parser;

public interface InputFormat {

	boolean parse(char car);
	
	// csv
	String getValue();
	
	// csv
	int getLine();
	
}
