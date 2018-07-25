package multimedia.sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import exceptions.NoSourceWasGivenException;
import exceptions.SomeSoundActualyRun;

public class SoundPlayer{
	//TODO volume clip.getLevel();
	//FloatControl v = clip.getControl(FloatControl.Type.Master_gain)
	//v .setValue
	
	
	private AudioInputStream stream = null;
	private Clip sound = null;
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
		this.sound = createClip();
	}
	
	public void setStream(String path, boolean stopPrevius) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		throwIfStreamIsNotNull(stopPrevius);
		this.stream = AudioSystem.getAudioInputStream(new File(path));
		this.sound = createClip();
	}
	
	public void setStream(File file, boolean stopPrevius) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		throwIfStreamIsNotNull(stopPrevius);
		this.stream = AudioSystem.getAudioInputStream(file);
		this.sound = createClip();
	}
	
	public void setStream(URL url, boolean stopPrevius) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		throwIfStreamIsNotNull(stopPrevius);
		this.stream = AudioSystem.getAudioInputStream(url);
		this.sound = createClip();
	}

	/******************** CONTROL 
	 * @throws IOException 
	 * @throws LineUnavailableException ************************/
	
	public void play() throws LineUnavailableException, IOException{
		throwIfStreamIsNull();
		if(!sound.isOpen()){
			sound.open(stream);
		}
	
		int i = 0;
		while(!sound.isRunning() || !sound.isActive()){
			try {Thread.sleep(30);} catch (InterruptedException e) {e.printStackTrace();}
			sound.start();
			i++;
			if(i==100)
				break;
		}
		System.out.println(i);
		
		
		System.out.println("---------");
		System.out.println("running " + sound.isRunning());
		System.out.println("active " + sound.isActive());
		System.out.println("open " + sound.isOpen());
		System.out.println("position " + sound.getMicrosecondPosition());
		System.out.println("-------");
	}
	
	public void stop() throws IOException{
		throwIfStreamIsNull();
		pause();
		sound.close();
		sound.flush();		
	}
	
	public void pause() throws IOException{
		throwIfStreamIsNull();
		sound.stop();
	}
	
	public void foward(long interval){
		throwIfStreamIsNull();
		sound.setMicrosecondPosition(sound.getMicrosecondPosition() + interval);
	}
	
	public void back(long interval){
		throwIfStreamIsNull();
		sound.setMicrosecondPosition(sound.getMicrosecondPosition() - interval);
	}
	
	/********* SUPPORT ************/
	
	public int getStatus(){
		if(stream == null || sound == null)
			return NO_SOURCE; //0
		if(!sound.isOpen())
			return STOPPED; //2
		if(getDuration() <= getPosition())
			return ENDED; //4
		if(sound.isRunning() && sound.isActive())
			return PLAYED; //1
		if(!sound.isActive() && !sound.isRunning() && sound.isOpen())
			return PAUSED; //3
		return -1;
	}
		
	public long getPosition(){
		throwIfStreamIsNull();
		return sound.getMicrosecondPosition();
	}
	
	public void setPosition(long position){
		throwIfStreamIsNull();
		sound.setMicrosecondPosition(position);
	}
	
	public long getDuration(){
		throwIfStreamIsNull();
		return sound.getMicrosecondLength();
	}
	
	public void setLoop(int count){
		throwIfStreamIsNull();
		if(count < 0){
			sound.loop(Clip.LOOP_CONTINUOUSLY);
			this.loopCount = Clip.LOOP_CONTINUOUSLY;
		}else{
			sound.loop(count);
			this.loopCount = count;
		}
	}
	
	public int getLoopCount(){
		return loopCount;
	}

	/********************************************************/
	/**
	 * for test only
	 * override existing clip instance
	 * to future - some annotation
	 * @param clip
	 */
	protected void setClip(Clip clip){
		this.sound = clip;
	}
	
	/**
	 * 
	 * @return
	 * @throws LineUnavailableException 
	 * @throws IOException 
	 */
	protected Clip createClip() throws LineUnavailableException, IOException{
		//TODO this or other implementatios
		AudioFormat format = stream.getFormat();		
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		Clip c = (Clip) AudioSystem.getLine(info);
		c.addLineListener(new LineListener() {
			
			@Override
			public void update(LineEvent e) {
				System.err.println(e);
			}
		});
		return c;
	}
	
	private void throwIfStreamIsNotNull(boolean stopPrevius){
		if(!stopPrevius && stream !=null)
			throw new SomeSoundActualyRun();
	}
		
	private void throwIfStreamIsNull(){
		if(stream == null || sound == null)
			throw new NoSourceWasGivenException();
	}
	
	/*********************************/
	
	@Override
	protected void finalize() throws Throwable {
		if(stream != null)
			stream.close();
		if(sound != null){
			sound.stop();
			sound.close();
			sound.flush();
			
		}
		super.finalize();
	}
}
