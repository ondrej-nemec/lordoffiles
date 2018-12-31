package multimedia.sound;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PipedInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Reproductor {
	
	protected final int BUFFER_SIZE = 128000;
	
	private final SourceDataLine line;
	public boolean play = true;
	
	public Reproductor(SourceDataLine line) {
		this.line = line;
	}

	public void play(AudioFormat format, ByteArrayInputStream data) throws IOException, LineUnavailableException {
		line.open(format);
		line.start();
		int nBytesRead = 0;
		byte[] readed = new byte[BUFFER_SIZE];
		while(nBytesRead != -1 && play){
			nBytesRead = data.read(readed);
			if(nBytesRead >= 0){
				/*int nBytesWritten =*/ line.write(readed, 0, nBytesRead);
			}
		}
		line.stop();
		line.close();
	}
	
	public void play(AudioFormat format, PipedInputStream pipe) throws IOException, LineUnavailableException {
		line.open(format);
		line.start();
		int nBytesRead = 0;
		byte[] readed = new byte[BUFFER_SIZE];
		while(nBytesRead != -1 && play){
			nBytesRead = pipe.read(readed);
			if(nBytesRead >= 0){
				/*int nBytesWritten =*/ line.write(readed, 0, nBytesRead);
			}
		}
		line.stop();
		line.close();
	}
}
