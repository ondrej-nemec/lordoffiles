package parser.csv;

import java.io.IOException;
import java.io.OutputStream;

import parser.ParserOutputStream;

public class CsvOutputStream extends ParserOutputStream{
	
	private final char separator;
	
	private boolean isFirst = true;	
	
	public CsvOutputStream(final OutputStream stream) {
		super(stream);
		this.separator = ',';
	}
	
	public CsvOutputStream(final OutputStream stream, final char separator) {
		super(stream);
		this.separator = separator;
	}

	public void writeValue(String value) throws IOException {
		value = value.replaceAll("\"", "\"\"");
		if (value.indexOf(separator) != -1 || value.indexOf("\n") != -1)
			value = "\"" + value + "\"";
		if (!isFirst)
			value = separator + value;
		os.write(value.getBytes());
		isFirst = false;
	}
	
	public void writeNewLine() throws IOException {
		os.write(NEW_LINE.getBytes());
		isFirst = true;
	}
	
}
