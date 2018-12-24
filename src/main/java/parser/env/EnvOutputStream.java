package parser.env;

import java.io.IOException;
import java.io.OutputStream;

import exceptions.ParserSyntaxException;
import parser.ParserOutputStream;

public class EnvOutputStream extends ParserOutputStream{

	public EnvOutputStream(final OutputStream stream) {
		super(stream);
	}
	
	public void writeTwins(String key, String value) throws IOException {
		if (key.indexOf('\n') >= 0)
			throw new ParserSyntaxException(".env", "Key could not contains new line (" + key + ")");
		if (key.indexOf('\\') >= 0)
			throw new ParserSyntaxException(".env", "Key could not contains backslash (" + key + ")");
		if (value.indexOf('\n') >= 0 || value.indexOf('"') >= 0 || value.indexOf('\'') >= 0 || value.indexOf('\\') >= 0) {

			value = value.replaceAll("\\\\", "\\\\\\\\"); // replace \ with \\
			value = value.replaceAll("\"", "\\\\\""); // replace " with \"
			value = value.replaceAll("'", "\\\\\'"); // replace ' with \'
			value = value.replaceAll("\n", "\\\\\\n"); // replace \n (one char) with \n (two chars)
			
			value = "\"" + value + "\"";
		}
		String toWrite = key + "=" + value + NEW_LINE;
		os.write(toWrite.getBytes());
	}
}
