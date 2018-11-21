package parser;


public class CsvFormat implements InputFormat {

	private char previousChar = '\u0000';
	private boolean isInQuots = false;
	
	private int line = 0;
	private String value = "";
//	private boolean twoQuots; //TODO this is not optimal solution
	
	@Override
	public boolean parse(char car) {
		//TODO if string start with " and not stopped before file end, this return true
		boolean result = true;
		
		if (previousChar == '\n' && !isInQuots) {
			line++;
			value = "";
		}		
		if (previousChar == ',' && !isInQuots)
			 value = "";
		
	//	if (previousChar != '"')
	//		twoQuots = false;
				
		switch (car) {
		case '"':
		//	if (twoQuots)
		//		throw new ParserSyntaxException("CSV, could not be three double quots");
			if (previousChar == '"') {
				value += car;
		//		twoQuots = true;
			} else if (isInQuots) {
				isInQuots = false;
			} else {
				isInQuots = true;
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
		
		/*		
		if (car == '"' && previousChar == '"')
			previousChar = '\u0000';
		else 
		*/	
		if ( ! (!isInQuots && car == '\r') ) 
			previousChar = car;
		
		return result;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public int getLine() {
		return line;
	}	
	
}
