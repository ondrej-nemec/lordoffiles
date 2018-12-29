package multimedia;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioInputStreamFactory {

	public static AudioInputStream getStream(InputStream is) throws UnsupportedAudioFileException, IOException{
 		return AudioSystem.getAudioInputStream(is);
 	}
 	
 	public static AudioInputStream getStream(String path) throws UnsupportedAudioFileException, IOException{
 		return AudioSystem.getAudioInputStream(new File(path));
 	}
 	
 	public static AudioInputStream getStream(File file) throws UnsupportedAudioFileException, IOException{
 		return AudioSystem.getAudioInputStream(file);
 	}
 	
 	public static AudioInputStream getStream(URL url) throws UnsupportedAudioFileException, IOException{
 		return AudioSystem.getAudioInputStream(url);
 	}
}
