package multimedia.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

public class DataLineFactory {

	public static Clip getClip(AudioInputStream stream) throws LineUnavailableException {
 		AudioFormat format = stream.getFormat();		
 		DataLine.Info info = new DataLine.Info(Clip.class, format);
 		return (Clip) AudioSystem.getLine(info);
 	}
}
