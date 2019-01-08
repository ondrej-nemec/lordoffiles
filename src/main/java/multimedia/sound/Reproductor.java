package multimedia.sound;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PipedInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Reproductor {
	protected interface Reader {
		int read(byte[] readed) throws IOException, LineUnavailableException;
	}
	
	protected final int BUFFER_SIZE = 128000;
	
	private final SourceDataLine line;
	public boolean play = true;
	
	public Reproductor(SourceDataLine line) {
		this.line = line;
	}

	public void play(AudioFormat format, ByteArrayInputStream data) throws IOException, LineUnavailableException {
		play(format, (readed)->{return data.read(readed);});
	}
	
	public void play(AudioFormat format, PipedInputStream pipe) throws IOException, LineUnavailableException {
		play(format, (readed)->{return pipe.read(readed);});
	}
	
	protected void play (AudioFormat format, Reader reader) throws IOException, LineUnavailableException {
		line.open(format);
		line.start();
		int nBytesRead = 0;
		byte[] readed = new byte[BUFFER_SIZE];
		while(nBytesRead != -1 && play){
			nBytesRead = reader.read(readed);
			if(nBytesRead >= 0){
				/*int nBytesWritten =*/ line.write(readed, 0, nBytesRead);
			}
		}
		line.stop();
		line.close();
	}
}
