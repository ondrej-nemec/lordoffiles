package text.binary;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BinaryCreator{

	public OutputStream stream(String name) throws FileNotFoundException {
		return new FileOutputStream(name);
	}
	
	public boolean write(final OutputStream stream, final byte[] data) throws IOException {
		stream.write(data);
		return true;
	}
	
}
