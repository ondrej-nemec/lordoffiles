package multimedia.sound;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import exceptions.FileCouldNotBeClosedException;
import exceptions.NoSourceWasGivenException;
import exceptions.SomeSoundActualyRun;

public class SoundPlayerTest {
	
	private String path = "/multimedia/sound/res/sound.wav";
	
	@Test
	public void testToMicrosecond(){
		assertEquals(1000000, SoundPlayer.toMicrosecond(1));
	}
	
	@Test
	public void testToSecond(){
		assertEquals(1, SoundPlayer.toSecond(1000000));
	}
	
	@Test
	public void testSoundDuration(){
		SoundPlayer s = new SoundPlayer();
		try {
			s.setStream(getClass().getResourceAsStream(path));
			s.soundPlay();
		} catch (UnsupportedAudioFileException e2) {e2.printStackTrace();
		} catch (IOException e2) {e2.printStackTrace();
		} catch (LineUnavailableException e) {e.printStackTrace();}
		assertEquals(185, SoundPlayer.toSecond(s.soundDuration()));
	}
	
	@Test
	public void testSoundPlayerFowardRewievPosition() {
		System.err.println("This test require your full attention, duration: 32s");
		int threads = Thread.activeCount();
		SoundPlayer s = new SoundPlayer();
		for(int i = 0; i  < 1000; i++){
			switch (i) {
			case 0:
				System.out.println("main " + i + " sound start");
				try {
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
				} catch (LineUnavailableException e1) {e1.printStackTrace();
				} catch (IOException e1) {e1.printStackTrace();
				} catch (UnsupportedAudioFileException e) {e.printStackTrace();}
				break;
			case 6:
				System.out.println("main " + i + " fowart for 60s");
				s.soundFoward(SoundPlayer.toMicrosecond(15));
				break;
			case 15:
				System.out.println("main " + i + " rewiev for 30s ");
				s.soundBack(SoundPlayer.toMicrosecond(10));
				break;
			case 20:
				System.out.println("main " + i + " rewiev for 100s - play from start");
				s.soundBack(SoundPlayer.toMicrosecond(100));
				break;
			case 25:
				System.out.println("main " + i + " foward for 300s - sound ends");
				s.soundFoward(SoundPlayer.toMicrosecond(300));
				break;
			case 32:
				System.err.println("End");
				assertEquals(threads, Thread.activeCount());
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
	
	//@Test
	public void testSoundPlayerLoop() {
		System.err.println("This test require your full attention, duration: 32s");
		
		SoundPlayer s = new SoundPlayer();
				
		for(int i = 0; i  < 1000; i++){
			switch (i) {
			case 0:
				System.out.println("main " + i + " sound start");
				try {
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
				} catch (LineUnavailableException e1) {e1.printStackTrace();
				} catch (IOException e1) {e1.printStackTrace();
				} catch (UnsupportedAudioFileException e) {e.printStackTrace();}
				break;
			case 1:	
				System.out.println("main " + i + " loop2 added");
				s.soundLoop(2);
				break;
			case 50:	
				System.out.println("main " + i + " loop2 added");
				s.soundLoop(2);
				break;
			case 75:	
				System.out.println("main " + i + " loop stoped");
				s.soundLoop(0);
				break;
			default:
				System.out.println("main: " + i);
				break;
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	
	@Test
	public void testSoundPlayerStartStopPause() {
		System.err.println("This test require your full attention, duration: 32s");
		int threads = Thread.activeCount();
		SoundPlayer s = new SoundPlayer();
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
					assertEquals(NoSourceWasGivenException.class, e.getClass());
					if(e instanceof NoSourceWasGivenException)
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
					assertEquals(NoSourceWasGivenException.class, e.getClass());
					if(e instanceof NoSourceWasGivenException)
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
				assertEquals(threads, Thread.activeCount());
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
