package multimedia.sound;

import java.util.function.Consumer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.TargetDataLine;

public class SoundRecorder {

	public PlayingAction action = PlayingAction.PLAY; // TODO refactor or new enum
	
	private int bufferSize;
	
	public void capture(AudioFormat format, TargetDataLine line, Consumer<byte[]> consumer) {
		
	}
	
	public void saveSound() {
		
	}
	
	public void capcureAndSaveSound(AudioFormat format, TargetDataLine line) {
		
	}
	
	public int getBufferSize() {
		return bufferSize;
	}
}
