package text.binary;

import java.io.IOException;
import java.io.OutputStream;

public class BinaryCreator{
	
	private final OutputStream stream;
	
	public BinaryCreator(final OutputStream stream) {
		this.stream = stream;
	}

	public void write(final byte[] data) throws IOException {
		stream.write(data);
	}
	
}
