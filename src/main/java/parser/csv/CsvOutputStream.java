package parser.csv;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class CsvOutputStream {
	
	private final OutputStream os;
	private final char separator;
	
	private boolean isFirst = true;
	private String newLine = File.separator == "/" ? "\n" : "\r\n";
	
	public CsvOutputStream(final OutputStream stream) {
		this.os = stream;
		this.separator = ',';
	}
	
	public CsvOutputStream(final OutputStream stream, final char separator) {
		this.os = stream;
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
		os.write(newLine.getBytes());
		isFirst = true;
	}
	
}
