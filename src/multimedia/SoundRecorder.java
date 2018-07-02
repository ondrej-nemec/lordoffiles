package multimedia;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class SoundRecorder {

	public int recording(AudioInputStream ais, AudioFileFormat.Type type, File file) throws IOException{
		return AudioSystem.write(ais, type, file);
	}
	
	public int recording(AudioInputStream ais, AudioFileFormat.Type type, OutputStream os) throws IOException{
		return AudioSystem.write(ais, type, os);
	}
	
}
