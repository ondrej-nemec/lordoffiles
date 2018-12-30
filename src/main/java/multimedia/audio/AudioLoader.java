package multimedia.audio;

import java.io.IOException;
import java.util.function.Consumer;

import javax.sound.sampled.AudioInputStream;

public class AudioLoader {

	protected final int BUFFER_SIZE = 128000;

	public void load(AudioInputStream stream, Consumer<byte[]> consumer) throws IOException {
		byte[] data = new byte[BUFFER_SIZE];
		int nBytesRead = 0;
		
		while(nBytesRead != -1){
			nBytesRead = stream.read(data, 0, data.length);
			if (nBytesRead >= 0)
				consumer.accept(data);
		}
	}
}
