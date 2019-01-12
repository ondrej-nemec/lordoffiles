package text.binary;

import java.io.IOException;
import java.io.OutputStream;

public class BinaryCreator{
	
	private final OutputStream stream;
	
	public BinaryCreator(final OutputStream stream) {
		this.stream = stream;
	}

	public boolean write(final byte[] data) throws IOException {
		stream.write(data);
		return true;
	}
	
}
