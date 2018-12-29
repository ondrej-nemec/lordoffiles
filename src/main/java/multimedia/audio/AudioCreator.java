package multimedia.audio;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

public class AudioCreator {

	private final OutputStream os;
	
	public AudioCreator(final OutputStream destination) {
		this.os = destination;
	}
	
	public void save(AudioFormat format, AudioFileFormat.Type type, ByteArrayOutputStream data) {
		
	}
	
	public void save(AudioFormat format) {
		
	}

}
