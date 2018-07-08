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
	public void testSoundStatus(){
		System.err.println("This test not require your attention - status - but duration: <3, 4>s");
		SoundPlayer s = new SoundPlayer();
		assertEquals(SoundPlayer.NO_SOURCE, s.status());
		try {
			s.setStream(getClass().getResourceAsStream(path));
			assertEquals(SoundPlayer.NOT_STARTED_YET, s.status());
		} catch (UnsupportedAudioFileException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();}
		try {
			s.soundPlay();
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			assertEquals(SoundPlayer.PLAYING, s.status());
		} catch (LineUnavailableException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();}
		try {
			s.soundPause();
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			assertEquals(SoundPlayer.PAUSE, s.status());
		} catch (FileCouldNotBeClosedException e) {e.printStackTrace();}
		s.soundSetPosition(SoundPlayer.toMicrosecond(200));
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		assertEquals(SoundPlayer.ENDED, s.status());
		
	}
	
	@Test
	public void testSoundPlayerFowardRewievPosition() {
		System.err.println("This test require your full attention - foward, rewiev, position, duration: 32s");
		int threads = Thread.activeCount();
		SoundPlayer s = new SoundPlayer();
		for(int i = 0; i  < 1000; i++){
			switch (i) {
			case 0:
				System.out.println("time " + i + " sound start");
				try {
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
				} catch (LineUnavailableException e1) {e1.printStackTrace();
				} catch (IOException e1) {e1.printStackTrace();
				} catch (UnsupportedAudioFileException e) {e.printStackTrace();}
				break;
			case 6:
				System.out.println("time " + i + " fowart for 60s");
				s.soundFoward(SoundPlayer.toMicrosecond(15));
				break;
			case 15:
				System.out.println("time " + i + " rewiev for 30s ");
				s.soundBack(SoundPlayer.toMicrosecond(10));
				break;
			case 20:
				System.out.println("time " + i + " rewiev for 100s - play from start");
				s.soundBack(SoundPlayer.toMicrosecond(100));
				break;
			case 25:
				System.out.println("time " + i + " foward for 300s - sound ends");
				s.soundFoward(SoundPlayer.toMicrosecond(300));
				break;
			case 32:
				try {
					s.soundStop();
				} catch (FileCouldNotBeClosedException e1) {e1.printStackTrace();}
				System.err.println("End");
				assertEquals(threads, Thread.activeCount());
				return;
			default:
				System.out.println("time: " + i);
				break;
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	@Test
	public void testSoundPlayerLoop() {
		System.err.println("This test require your full attention - loop, duration: 50s");
		int threads = Thread.activeCount();
		
		SoundPlayer s = new SoundPlayer();
		for(int i = 0; i  < 1000; i++){
			switch (i) {
			case 0:
				System.out.println("time " + i + " sound start");
				try {
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
				} catch (LineUnavailableException e1) {e1.printStackTrace();
				} catch (IOException e1) {e1.printStackTrace();
				} catch (UnsupportedAudioFileException e) {e.printStackTrace();}
				break;
			case 1:	
				System.out.println("time " + i + " loop1 added");
				s.soundLoop(1);
				break;
			case 5:
				System.out.println("time " + i + " skipped nearly to end");
				s.soundSetPosition(SoundPlayer.toMicrosecond(180));
				break;
			case 15:
				System.out.println("time " + i + " now the second is playing");
				break;
			case 17:
				System.out.println("time " + i + " skipped nearly to end");
				s.soundSetPosition(SoundPlayer.toMicrosecond(180));
				break;
			case 25:
				System.out.println("time " + i + " now the second is stopped");
				break;
			case 30:
				System.out.println("time " + i + " sound start");
				try {
					s.soundPlay();
				} catch (LineUnavailableException e1) {e1.printStackTrace();
				} catch (IOException e1) {e1.printStackTrace();}
				break;
			case 31:	
				System.out.println("time " + i + " loop8 added");
				s.soundLoop(8);
				break;
			case 36:	
				System.out.println("time " + i + " loop stopped");
				s.soundLoop(0);
				break;
			case 38:
				System.out.println("time " + i + " skipped nearly to end");
				s.soundSetPosition(SoundPlayer.toMicrosecond(180));
				break;
			case 50:	
				System.err.println("end");
				try {
					s.soundStop();
				} catch (FileCouldNotBeClosedException e1) {e1.printStackTrace();}
				assertEquals(threads, Thread.activeCount());
				break;	
			default:
				System.out.println("time: " + i);
				break;
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	
	@Test
	public void testSoundPlayerStartStopPause() {
		System.err.println("This test require your full attention - start, stop, pause, duration: 32s");
		int threads = Thread.activeCount();
		SoundPlayer s = new SoundPlayer();
		for(int i = 0; i  < 1000; i++){
			switch (i) {
			case 0:
				System.err.println("Start");
				break;
			case 1:
				System.out.println("time " + i + ": stopMusic - nothing happend" );
				try {
					s.soundStop();
				} catch (FileCouldNotBeClosedException e1) {e1.printStackTrace();}
				
				break;
			case 2:
				System.out.println("time " + i + ": pause - nothing happend" );
				try {
					s.soundPause();
				} catch (FileCouldNotBeClosedException e1) {e1.printStackTrace();}
				break;
			case 3:
				System.out.println("time " + i + ": play - nothing happend" );
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
				System.out.println("time " + i + ": setStream, play - music start" );
				try {
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
				} catch (LineUnavailableException e1) {e1.printStackTrace();
				} catch (IOException e1) {e1.printStackTrace();
				} catch (UnsupportedAudioFileException e) {e.printStackTrace();}
				break;	
			case 6:
				System.out.println("time " + i + ": setStream, play - nothing happend" );
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
				System.out.println("time " + i + ": stopMusic - music stop" );
				try {
					s.soundStop();
				} catch (FileCouldNotBeClosedException e1) {e1.printStackTrace();}
				break;	
			case 10:
				System.out.println("time " + i + ": play - nothing happend" );
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
				System.out.println("time " + i + ": setStream, play - music start" );
				try {
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
				} catch (LineUnavailableException e1) {e1.printStackTrace();
				}catch(Exception e){e.printStackTrace();}
				break;	
			case 20:
				System.out.println("time " + i + ": pause - music stop" );
				try {
					s.soundPause();
				} catch (FileCouldNotBeClosedException e1) {e1.printStackTrace();}
				break;	
			case 25:
				System.out.println("time " + i + ": play - music start, where was stopped" );
				try {
					s.soundPlay();
				} catch (LineUnavailableException e1) {e1.printStackTrace();
				} catch (IOException e1) {e1.printStackTrace();}
				break;
			case 30:
				System.out.println("time " + i + ": stopMusic - music stop" );
				try {
					s.soundStop();
				} catch (FileCouldNotBeClosedException e1) {e1.printStackTrace();}
				break;
				
			case 32:
				System.err.println("End");
				assertEquals(threads+1, Thread.activeCount());
				return;
			default:
				System.out.println("time: " + i);
				break;
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

}
