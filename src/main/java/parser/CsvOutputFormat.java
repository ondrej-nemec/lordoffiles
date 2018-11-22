package parser;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class CsvOutputFormat {
	
	private OutputStream os;
	
	private boolean isFirst = true;
	private String newLine = File.separator == "/" ? "\n" : "\r\n";
	
	public CsvOutputFormat(final OutputStream stream) {
		this.os = stream;
	}

	public void writeValue(String value) throws IOException {
		value = value.replaceAll("\"", "\"\"");
		if (value.indexOf(",") != -1 || value.indexOf("\n") != -1)
			value = "\"" + value + "\"";
		if (!isFirst)
			value = "," + value;
		os.write(value.getBytes());
		isFirst = false;
	}
	
	public void writeNewLine() throws IOException {
		os.write(newLine.getBytes());
		isFirst = true;
	}
	
}
