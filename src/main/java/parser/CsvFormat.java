package parser;

public class CsvFormat implements InputFormat {

	private char previousChar = '\u0000';
	private boolean isInQuots = false;
	
	private String value = "";	
	private int row = 0;	
	private int col = 0;
	
	@Override
	public boolean parse(char car) {
		boolean result = true;
	//TODO co treba mit dve eventy - text a new line???s
		switch (car) {
		case '"':
			if (previousChar == '"') {
				value += car;
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
				
		if ( ! (!isInQuots && car == '\r') ) 
			previousChar = car;
		return result;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public int getRowIndex() {
		return row;
	}

	@Override
	public int getColumnIndex() {
		return col;
	}

	
	
}
