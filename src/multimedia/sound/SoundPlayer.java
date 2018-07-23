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
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import exceptions.FileCouldNotBeClosedException;
import exceptions.NoSourceWasGivenException;
import exceptions.SomeSoundActualyRun;

public class SoundPlayer{
	
	public static final int NOT_PREPARED = 0;
	public static final int NOT_PLAYED_YET = 1;
	public static final int PLAYING = 2;
	public static final int PAUSE = 3;
	public static final int ENDED = 4;
	public static final int STOPPED = 5;
	
	
	private AudioInputStream stream = null;
	private Clip clip = null;

	public AudioInputStream getStream(){
		return stream;
	}
	
	public void setStream(InputStream is) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		canRunNew();
		this.stream = AudioSystem.getAudioInputStream(is);
		prepare();
	}
	
	public void setStream(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		canRunNew();
		this.stream = AudioSystem.getAudioInputStream(new File(path));
		prepare();
	}
	
	public void setStream(File file) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		canRunNew();
		this.stream = AudioSystem.getAudioInputStream(file);
		prepare();
	}
	
	public void setStream(URL url) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		canRunNew();
		this.stream = AudioSystem.getAudioInputStream(url);
		prepare();
	}
	
	public void clear() throws IOException{
		soundStop();
		stream = null;
		clip = null;
	}
	
	private void canRunNew() throws SomeSoundActualyRun{
		if(stream != null)
			throw new SomeSoundActualyRun();
	}
	
	/**
	 * @throws LineUnavailableException 
	 * @throws IOException
	*/
	private void prepare() throws LineUnavailableException, IOException{
		AudioFormat format = stream.getFormat();		
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		clip = (Clip)AudioSystem.getLine(info);
	}
	
	/**************/
	public int status(){
		
		try {Thread.sleep(90);} catch (InterruptedException e) {e.printStackTrace();}
		
		if(stream == null || clip == null)
			return NOT_PREPARED;
/*		System.out.println();
		System.out.println(clip.isActive());
		System.out.println(clip.isOpen());
		System.out.println(clip.isRunning());*/
		if(!clip.isOpen()) // && !clip.isRunning() && !clip.isActive()
			return STOPPED;
		if(!clip.isActive() && !clip.isRunning()) // && clip.isOpen()
			return PAUSE;
		if(soundDuration() <= soundGetPosition())
			return ENDED;
		if(clip.isRunning() || clip.isOpen())
			return PLAYING;
		return -1;
	}
	
	public void clip(String name){
		try {Thread.sleep(90);} catch (InterruptedException e) {e.printStackTrace();}
		
		System.out.println("---- " + name);
		if(clip != null){
			System.out.println("active " + clip.isActive());
			System.out.println("running " + clip.isRunning());
			System.out.println("open " + clip.isOpen());
		}
		System.out.println();
	}
	
	/******************/	
	public void soundPause() throws FileCouldNotBeClosedException{
		if(clip != null){
			clip.stop();
		}
		if(stream != null)
			try {
				stream.close();
			}catch(IOException e){
				throw new FileCouldNotBeClosedException();
			}
	}
	
	public void soundStop() throws FileCouldNotBeClosedException{
		soundPause();
		if(clip != null){
			clip.close();
			clip.flush();
		}
	//	clip = null;
	//	stream = null;
	}
	
	public void soundPlay() throws LineUnavailableException, IOException{
		if(stream == null)
			throw new NoSourceWasGivenException();
		if(!clip.isOpen()){
			System.out.println("----------------openning");
			prepare();
			clip.open(stream);
		}
		if(!clip.isActive() || !clip.isRunning()){
			System.out.println("---------------starting");
			clip.start();
		}
	}

	public void soundBack(long positionFromStart){
		soundSetPosition(soundGetPosition() - positionFromStart);
	}
	
	public void soundFoward(long positionFromStart){
		soundSetPosition(soundGetPosition() + positionFromStart);
	}

	/**************/
	
	/**
	 * 
	 * @param count of loop, 0 - stop looping, -x infinity
	 */
	public void soundLoop(int count){
		if(clip != null){
			if(count < 0)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.loop(count);
		}
	}

	public long soundGetPosition(){
		if(clip == null)
			throw new NoSourceWasGivenException();
		return clip.getMicrosecondPosition();
	}
	
	public void soundSetPosition(long microSecond){
		if(clip == null)
			throw new NoSourceWasGivenException();
		clip.setMicrosecondPosition(microSecond);
	}
	
	public long soundDuration(){
		if(clip == null)
			throw new NoSourceWasGivenException();
		return clip.getMicrosecondLength();
	}
	
	/****** *******/
	public static int toSecond(long microsecond){
		return (int)(microsecond/1000000.0);
	}
	
	public static long toMicrosecond(int second){
		return (long)(second*1000000);
	}
	
	/**********************************************************/
	
	
	@Override
	protected void finalize() throws Throwable {
		clear();
		super.finalize();
	}
	
}
