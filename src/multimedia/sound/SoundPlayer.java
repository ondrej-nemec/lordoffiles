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
	
	public static final int NO_SOURCE = 0;
	public static final int NOT_STARTED_YET = 1;
	public static final int PLAYING = 2;
	public static final int PAUSE = 3;
	public static final int ENDED = 4;
	
	
	AudioInputStream stream = null;
	Clip clip = null;
	
	
	public AudioInputStream getStream(){
		return stream;
	}
	
	public void setStream(InputStream is) throws UnsupportedAudioFileException, IOException{
		canRunNew();
		this.stream = AudioSystem.getAudioInputStream(is);
	}
	
	public void setStream(String path) throws UnsupportedAudioFileException, IOException{
		canRunNew();
		this.stream = AudioSystem.getAudioInputStream(new File(path));
	}
	
	public void setStream(File file) throws UnsupportedAudioFileException, IOException{
		canRunNew();
		this.stream = AudioSystem.getAudioInputStream(file);
	}
	
	public void setStream(URL url) throws UnsupportedAudioFileException, IOException{
		canRunNew();
		this.stream = AudioSystem.getAudioInputStream(url);
	}
	
	private void canRunNew() throws SomeSoundActualyRun{
		if(stream != null)
			throw new SomeSoundActualyRun();
	}
	/**************/
	public int status(){
		if(stream == null)
			return NO_SOURCE;
		if(clip == null)
			return NOT_STARTED_YET;
		if(clip.isRunning() || clip.isActive())
			return PLAYING;
		if(soundDuration() <= soundGetPosition())
			return ENDED;
		if(!clip.isActive() && !clip.isRunning())
			return PAUSE;
		return -1;
	}
	
	/******************/
	public void soundStop() throws FileCouldNotBeClosedException{
		soundPause();
		if(clip != null){
			clip.close();
			clip.flush();
		}
		clip = null;
		stream = null;
	}
	
	public void soundPause() throws FileCouldNotBeClosedException{
		if(clip != null)
			clip.stop();
		if(stream != null)
			try {
				stream.close();
			}catch(IOException e){
				throw new FileCouldNotBeClosedException();
			}
	}
	
	public void soundPlay() throws LineUnavailableException, IOException{
		if(stream == null)
			throw new NoSourceWasGivenException();
		if(clip != null)
			clip.start();
		else if(stream != null)
			playing();
	}
	
	/**
	 * 
	 * @param count ouf loop, 0 - stop looping, -x, infinity
	 */
	public void soundLoop(int count){
		if(clip != null){
			if(count < 0)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.loop(count);
		}
	}
	
	
	/*******  *******/
	public void soundBack(long positionFromStart){
		soundSetPosition(soundGetPosition() - positionFromStart);
	}
	
	public void soundFoward(long positionFromStart){
		soundSetPosition(soundGetPosition() + positionFromStart);
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
	
	/**
	 * @throws LineUnavailableException 
	 * @throws IOException
	*/
	private void playing() throws LineUnavailableException, IOException{
		AudioFormat format = stream.getFormat();		
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		clip = (Clip)AudioSystem.getLine(info);
		clip.open(stream);
		clip.start();
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		soundStop();
		super.finalize();
	}
	
}
