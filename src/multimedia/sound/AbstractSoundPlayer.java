package multimedia.sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public abstract class AbstractSoundPlayer<T extends DataLine> {

	/***** status constants *****/
	public static final int NO_SOURCE = 0;
	public static final int STOPPED = 1;
	public static final int PLAYED = 2;
	public static final int PAUSED = 3;
	public static final int ENDED = 4;
	
	/***** ****** *****/
	
	protected AudioInputStream stream;
	
	protected T line;
	
	protected AudioFormat format;
	
	/***** streams *****/

	public AudioInputStream getStream(){
		return stream;
	}
	
	public void setStream(InputStream is) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.stream = AudioSystem.getAudioInputStream(is);
		actionAfterStreamSetting();
	}
	
	public void setStream(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.stream = AudioSystem.getAudioInputStream(new File(path));
		actionAfterStreamSetting();
	}
	
	public void setStream(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.stream = AudioSystem.getAudioInputStream(file);
		actionAfterStreamSetting();
	}
	
	public void setStream(URL url) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.stream = AudioSystem.getAudioInputStream(url);
		actionAfterStreamSetting();
	}
	
	protected void setStream(AudioInputStream stream) throws LineUnavailableException, IOException{
		this.stream = stream;
		actionAfterStreamSetting();
	}
	
	protected abstract void actionAfterStreamSetting() throws LineUnavailableException, IOException;
	
	/***** volume *****/
	//TODO volume
	
	
	/***** test setters *****/
	
	protected void setLine(T line){
		this.line = line;
	}
	
	protected T getDataLine(){
		return line;
	}
	
	/***** ** *****/
	@Override
	protected void finalize() throws Throwable {
		if(stream != null)
			stream.close();
		if(line != null){
			line.stop();
			line.close();
			line.flush();
		}
		super.finalize();
	}
	
}
