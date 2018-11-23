package parser;

import java.io.IOException;
import java.io.InputStream;

import exceptions.ParserSyntaxException;

public class CsvInputStream {
	
	private final InputStream is;

	private char previousChar = '\u0000';
	private boolean isInQuots = false;
	
	private int line = 0;
	private String value = "";
	private int countOfQuots = 0;
		
	public CsvInputStream(final InputStream stream) {
		this.is = stream;
	}	

	public String getValue() {
		return value;
	}

	public int getLine() {
		return line;
	}	
	
	public boolean next() throws IOException {
		int b = 0;
		boolean isContinue = true;
		while(isContinue && ((b = is.read()) != -1)) {
			isContinue = parse((char)b);
		}
		if (b != -1)
			return true;
		return false;
	}
	
	protected boolean parse(char car) {
		//TODO if string start with " and not stopped before file end, this return true
		boolean result = true;
		
		if (previousChar == '\n' && !isInQuots) {
			line++;
			value = "";
		}		
		if (previousChar == ',' && !isInQuots)
			 value = "";
		
		if (car != '"' && countOfQuots > 0) {
			if (!isInQuots && countOfQuots == 1 && value.isEmpty()) // start quot
				isInQuots = true;
			else if (isInQuots && countOfQuots %2 == 1) // end quot
				isInQuots = false;
			else if (countOfQuots % 2 == 0) {}// ignoring				
			else
				throw new ParserSyntaxException(
						"CSV",
						"You have syntax problem with double quots on line " 
						+ line + " near " + previousChar + car
				);
			countOfQuots = 0;
		}
				
		switch (car) {
		case '"':
			countOfQuots++;
			if (previousChar == '"') {
				value += car;
			}
			break;
		case ',':
			if (!isInQuots) {
				result = false;
			} else {
				value += car;
			}					
			break;
		case '\n':
			if (isInQuots) {
				value += car;
			} else {
				result = false;
			}
			break;
		case '\r':
			if (isInQuots)
				value += car;
			break;
		default:
			value += car;
		}
		
		if (car == '"' && previousChar == '"')
			previousChar = '\u0000';
		else if ( ! (!isInQuots && car == '\r') ) 
			previousChar = car;
		
		return result;
	}

}
