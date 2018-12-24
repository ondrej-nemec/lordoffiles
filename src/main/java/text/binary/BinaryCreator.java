package text.binary;

import java.io.IOException;
import java.io.OutputStream;

public class BinaryCreator{

	public boolean write(final OutputStream stream, final byte[] data) throws IOException {
		stream.write(data);
		return true;
	}
	
}
