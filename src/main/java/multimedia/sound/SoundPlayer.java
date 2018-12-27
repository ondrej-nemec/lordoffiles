package multimedia.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundPlayer {

	public PlayingAction ACTION = PlayingAction.PLAY;
	protected final int BUFFER_SIZE = 128000;
	
	private final AudioInputStream stream;
	private final SourceDataLine line;
	
	public SoundPlayer(AudioInputStream stream, SourceDataLine line) {
		this.stream = stream;
		this.line = line;
	}
	
	public void play() throws IOException, LineUnavailableException {
		byte[] data = new byte[BUFFER_SIZE];
		int nBytesRead = 0;
		
		line.open(stream.getFormat());
		line.start();
		while(nBytesRead != -1 && ACTION != PlayingAction.STOP){
			if (ACTION == PlayingAction.PLAY) {
				nBytesRead = stream.read(data, 0, data.length);
				
				if(nBytesRead >= 0){
					/*int nBytesWritten =*/ line.write(data, 0, nBytesRead);
				}
			}
		}
	}
	
	public long getMicroSecondPosition() {
		return 0;
	}
	
}
