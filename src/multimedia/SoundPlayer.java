package multimedia;

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
import exceptions.NoSourceWasGiven;
import exceptions.SomeSoundActualyRun;

public class SoundPlayer extends Thread {

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
	
	/******************/
	public void soundStop() throws FileCouldNotBeClosedException{
		soundPause();
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
			throw new NoSourceWasGiven();
		if(clip != null)
			clip.start();
		else if(stream != null)
			playing();
		
	}
	//TODO pretaceni
	// mark, skip, reset,
	
	
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
