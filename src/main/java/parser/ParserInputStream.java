package parser;

import java.io.IOException;
import java.io.InputStream;

public class ParserInputStream<T extends InputFormat> {
	
	private final InputStream is;
	
	private final T format;
		
	public ParserInputStream(final InputStream stream, T format) {
		this.is = stream;
		this.format = format;
	}
	
	public boolean next() throws IOException {
		int b = 0;
		boolean isContinue = true;
		while(isContinue && ((b = is.read()) != -1)) {
			isContinue = format.parse((char)b);
		}
		if (b != -1)
			return true;
		return false;
	}
	
	public T getFormat() {
		return format;
	}
}
