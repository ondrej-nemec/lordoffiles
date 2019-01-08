package multimedia.audio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.function.Consumer;

import javax.sound.sampled.AudioInputStream;

public class AudioLoader {
	
	protected interface Writer {
		
		void write(byte[] data, int off, int len) throws IOException;
		
	}

	protected final int BUFFER_SIZE = 128000;

	public void load(AudioInputStream stream, Consumer<byte[]> consumer) throws IOException {
		load(stream, (data, off, len)->{consumer.accept(data);});
	}
	
	public void load(AudioInputStream stream, ByteArrayOutputStream out) throws IOException {
		load(stream, (data, off, len)->{out.write(data, off, len);});
	}
	
	public void load(AudioInputStream stream, PipedOutputStream pipe) throws IOException {
		load(stream, (data, off, len)->{pipe.write(data, off, len);});
	}
	
	protected void load(AudioInputStream stream, Writer writer) throws IOException {
		byte[] data = new byte[BUFFER_SIZE];
		int nBytesRead = 0;
		
		while(nBytesRead != -1){
			nBytesRead = stream.read(data, 0, data.length);
			if (nBytesRead >= 0)
				writer.write(data, 0, nBytesRead);
		}
	}
}
