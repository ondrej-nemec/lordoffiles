package multimedia.sound;

import java.io.IOException;
import java.util.function.Supplier;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Reproductor {
	
	private final SourceDataLine line;
	public boolean play = true;
	
	public Reproductor(SourceDataLine line) {
		this.line = line;
	}

	//TODO
	public void play(AudioFormat format, Supplier<byte[]> dataProvider)
			throws IOException, LineUnavailableException {
		line.open(format);
		line.start();
		int nBytesRead = 0;
		while(nBytesRead != -1 && play){
			byte[] data = dataProvider.get();
			if(nBytesRead >= 0){
				/*int nBytesWritten =*/ line.write(data, 0, nBytesRead);
			}
		}
		line.stop();
		line.close();
	}
}
