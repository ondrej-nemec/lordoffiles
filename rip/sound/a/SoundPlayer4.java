package sound.a;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import exceptions.NoSourceWasGivenException;
import exceptions.SomeSoundActualyRun;

public class SoundPlayer4{
	//TODO volume clip.getLevel();
	//FloatControl v = clip.getControl(FloatControl.Type.Master_gain)
	//v .setValue
	
	
	private AudioInputStream stream = null;
	private SourceDataLine line = null;
	private int loopCount = 0;
	
	public static final int NO_SOURCE = 0;
	public static final int PLAYED = 1;
	public static final int STOPPED = 2;
	public static final int PAUSED = 3;
	public static final int ENDED = 4;
	
	/*********************** STREAM *****************************/

	public AudioInputStream getStream(){
		return stream;
	}
	
	public void setStream(InputStream is, boolean stopPrevius) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		throwIfStreamIsNotNull(stopPrevius);
		this.stream = AudioSystem.getAudioInputStream(is);
		this.line = createLine();
	}
	
	public void setStream(String path, boolean stopPrevius) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		throwIfStreamIsNotNull(stopPrevius);
		this.stream = AudioSystem.getAudioInputStream(new File(path));
		this.line = createLine();
	}
	
	public void setStream(File file, boolean stopPrevius) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		throwIfStreamIsNotNull(stopPrevius);
		this.stream = AudioSystem.getAudioInputStream(file);
		this.line = createLine();
	}
	
	public void setStream(URL url, boolean stopPrevius) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		throwIfStreamIsNotNull(stopPrevius);
		this.stream = AudioSystem.getAudioInputStream(url);
		this.line = createLine();
	}

	/******************** CONTROL 
	 * @throws IOException 
	 * @throws LineUnavailableException ************************/
	
	public void play() throws LineUnavailableException, IOException{
	}
	
	public void stop() throws IOException{
	}
	
	public void pause() throws IOException{
	}
	
	public void foward(long interval){
	}
	
	public void back(long interval){
	}
	
	/********* SUPPORT ************/
	
	public int getStatus(){
		return -1;
	}
		
	public long getPosition(){
		throwIfStreamIsNull();
		return line.getMicrosecondPosition();
	}
	
	public void setPosition(long position){
		
	}
	
	public long getDuration(){
		return 0;
	}
	
	public void setLoop(int count){
		
	}
	
	public int getLoopCount(){
		return loopCount;
	}

	/********************************************************/
	
	protected void setLine(SourceDataLine line){
		this.line = line;
	}
	
	/**
	 * 
	 * @return
	 * @throws LineUnavailableException 
	 * @throws IOException 
	 */
	protected SourceDataLine createLine() throws LineUnavailableException, IOException{
		AudioFormat format = stream.getFormat();		
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		SourceDataLine c = (SourceDataLine) AudioSystem.getLine(info);
		
		c.addLineListener(new LineListener() {
			
			@Override
			public void update(LineEvent e) {
				System.err.println(e);
			}
		});
		return c;
	}
	
	private void running(){
		line.start();
		int bufferSize = 128000;
		
		int nBytedRead = 0;
		byte[] data = new byte[bufferSize];
		while(nBytedRead != -1){
			try {
				nBytedRead = stream.read(data, 0, data.length);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(nBytedRead >= 0){
				int nBytesWritten = line.write(data, 0, nBytedRead);
			}
		}
		//konec
		line.drain();
		line.close();
		
	}
	
	private void throwIfStreamIsNotNull(boolean stopPrevius){
		if(!stopPrevius && stream !=null)
			throw new SomeSoundActualyRun();
	}
		
	private void throwIfStreamIsNull(){
		if(stream == null || line == null)
			throw new NoSourceWasGivenException();
	}
	
	/*********************************/
	
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
