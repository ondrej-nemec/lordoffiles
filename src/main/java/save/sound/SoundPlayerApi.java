package multimedia.sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Function;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import exceptions.NoSourceWasGivenException;


public abstract class SoundPlayerApi<T extends DataLine> {
//TODO rework
	/***** status constants *****/
	public static final int NO_SOURCE = 0;
	public static final int STOPPED = 1;
	public static final int PLAYED = 2;
	public static final int PAUSED = 3;
	
	/***** ****** *****/
	
	protected AudioInputStream stream;
	
	protected T line;
	
	protected AudioFormat format;
	
	protected int loopCount = 0;
	
	/***** streams *****/

	public void setResource(InputStream is) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.stream = AudioSystem.getAudioInputStream(is);
		this.line = createLine();
	}
	
	public void setResource(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.stream = AudioSystem.getAudioInputStream(new File(path));
		this.line = createLine();
	}
	
	public void setResource(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.stream = AudioSystem.getAudioInputStream(file);
		this.line = createLine();
	}
	
	public void setResource(URL url) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.stream = AudioSystem.getAudioInputStream(url);
		this.line = createLine();
	}
	
	public void closeResources() throws IOException{
		if(stream != null){
			stream.close();
		}
		if(line != null){
			line.stop();
			line.flush();
			line.close();
		}
	}
	
	/**************/
	
	public void setResource(AudioInputStream stream, T line) throws LineUnavailableException, IOException{
		this.stream = stream;
		this.line = line;
	}	
	
	public AudioInputStream getStream(){
		return stream;
	}
		
	public T getDataLine(){
		return line;
	}
		
	protected T createLine() throws LineUnavailableException, IOException{
		stream.mark(Integer.MAX_VALUE);
		format = stream.getFormat();
		DataLine.Info info = new DataLine.Info(clazz(), format);
		DataLine line = (DataLine)AudioSystem.getLine(info);
		
		line.addLineListener(new LineListener() {
			
			@Override
			public void update(LineEvent e) {
				if(e.getType().equals(LineEvent.Type.STOP)){
					if(getPosition() >= getDuration()){
						stop();
					}
				}
			}
		});
		
		return fromLineToT().apply(line);
	}
	
	protected abstract Function<DataLine, T> fromLineToT();
	
	protected abstract Class<T> clazz();
		
	/***** volume *****/
	//TODO volume

	/***********  API  ***************/
	
	public abstract void play() throws LineUnavailableException, IOException ;
	
	public abstract void pause();
	
	public abstract void stop();
	
	public abstract void foward(long msInterval);
	
	public abstract void back(long msInterval) throws LineUnavailableException, IOException ;
	/**/
	public abstract int getStatus();
	
	public abstract long getDuration();
	
	public abstract void setPosition(long microSeconds);
	
	public abstract void setLoop(int count);
	/**/
	public long getPosition(){
		throwIfNoResource();
		return line.getMicrosecondPosition();
	}
		
	public int getLoopCount(){
		return loopCount;
	}
	
	/***/
	
	protected void throwIfNoResource(){
		if(stream == null)
			throw new NoSourceWasGivenException();
	}
	
	/***** ** *****/
	
	@Override
	protected void finalize() throws Throwable {
		closeResources();
		super.finalize();
	}	
}
