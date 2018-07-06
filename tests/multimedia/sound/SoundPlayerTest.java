package multimedia.sound;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import exceptions.FileCouldNotBeClosedException;
import exceptions.NoSourceWasGiven;
import exceptions.SomeSoundActualyRun;

public class SoundPlayerTest {

	@Test
	public void testSoundPlayer() {
		System.err.println("This test require your full attention, duration: 32s");
		String path = "/multimedia/sound/res/sound.wav";
		SoundPlayer s = new SoundPlayer();
		
		try {
			s.setStream(getClass().getResourceAsStream(path));
		} catch (UnsupportedAudioFileException e2) {e2.printStackTrace();
		} catch (IOException e2) {e2.printStackTrace();}
		
		for(int i = 0; i  < 1000; i++){
			switch (i) {
			case 0:
				System.err.println("Start");
				break;
			case 1:
				System.out.println("sound " + i + ": stopMusic - nothing happend" );
				try {
					s.soundStop();
				} catch (FileCouldNotBeClosedException e1) {e1.printStackTrace();}
				
				break;
			case 2:
				System.out.println("sound " + i + ": pause - nothing happend" );
				try {
					s.soundPause();
				} catch (FileCouldNotBeClosedException e1) {e1.printStackTrace();}
				break;
			case 3:
				System.out.println("sound " + i + ": play - nothing happend" );
				try{
					s.soundPlay();
				}catch(Exception e){
					assertEquals(NoSourceWasGiven.class, e.getClass());
					if(e instanceof NoSourceWasGiven)
						System.err.println("NoSourceWasGiven");
					else
						e.printStackTrace();
				}
				break;	
			case 5:
				System.out.println("sound " + i + ": setStream, play - music start" );
				try {
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
				} catch (LineUnavailableException e1) {e1.printStackTrace();
				} catch (IOException e1) {e1.printStackTrace();
				} catch (UnsupportedAudioFileException e) {e.printStackTrace();}
				break;	
			case 6:
				System.out.println("sound " + i + ": setStream, play - nothing happend" );
				try{
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
				}catch(Exception e){
					assertEquals(SomeSoundActualyRun.class, e.getClass());
					if(e instanceof SomeSoundActualyRun)
						System.err.println("SomeSoundActualyRun");
					else
						e.printStackTrace();
				}
				break;	
			case 8:
				System.out.println("sound " + i + ": stopMusic - music stop" );
				try {
					s.soundStop();
				} catch (FileCouldNotBeClosedException e1) {e1.printStackTrace();}
				break;	
			case 10:
				System.out.println("sound " + i + ": play - nothing happend" );
				try{
					s.soundPlay();
				}catch(Exception e){
					assertEquals(NoSourceWasGiven.class, e.getClass());
					if(e instanceof NoSourceWasGiven)
						System.err.println("NoSourceWasGiven");
					else
						e.printStackTrace();
				}
				break;
			case 13:
				System.out.println("sound " + i + ": setStream, play - music start" );
				try {
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
				} catch (LineUnavailableException e1) {e1.printStackTrace();
				}catch(Exception e){e.printStackTrace();}
				break;	
			case 20:
				System.out.println("sound " + i + ": pause - music stop" );
				try {
					s.soundPause();
				} catch (FileCouldNotBeClosedException e1) {e1.printStackTrace();}
				break;	
			case 25:
				System.out.println("sound " + i + ": play - music start, where was stopped" );
				try {
					s.soundPlay();
				} catch (LineUnavailableException e1) {e1.printStackTrace();
				} catch (IOException e1) {e1.printStackTrace();}
				break;
			case 30:
				System.out.println("sound " + i + ": stopMusic - music stop" );
				try {
					s.soundStop();
				} catch (FileCouldNotBeClosedException e1) {e1.printStackTrace();}
				break;
				
			case 32:
				System.err.println("End");
				return;
			default:
				System.out.println("main: " + i);
				break;
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

}
