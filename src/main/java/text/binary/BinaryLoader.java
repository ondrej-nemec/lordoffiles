package text.binary;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class BinaryLoader {
	
	private int defaultBufferSize = 32;
	
	private final InputStream stream;
	
	public BinaryLoader(final InputStream stream) {
		this.stream = stream;
	}

	public void read(final Consumer<byte[]> consumer) throws IOException {
		read(consumer, defaultBufferSize);
	}

	public void read(final Consumer<byte[]> consumer, final int bufferSize) throws IOException {
		byte[] bytes = new byte[bufferSize];
		while(stream.read(bytes) != -1) {
			consumer.accept(bytes);
		}
	}
	
	public byte[] read() throws IOException {
		List<Byte> a = new LinkedList<>();
		
		int readed;
		while ((readed = stream.read()) != -1) {
			a.add((byte)readed);
		}
		
		byte[] result = new byte[a.size()];
		for (int i = 0; i < a.size(); i++) {
			result[i] = a.get(i);
		}
		
		return result;
	}

	public int getDefaultBufferSize() {
		return defaultBufferSize;
	}

	public void setDefaultBufferSize(int defaultBufferSize) {
		this.defaultBufferSize = defaultBufferSize;
	}
}
