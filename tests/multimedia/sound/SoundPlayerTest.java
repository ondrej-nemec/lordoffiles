package multimedia.sound;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

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
			assertEquals(185, SoundPlayer.toSecond(s.soundDuration()));
			s.soundStop();
		} catch (UnsupportedAudioFileException e2) {e2.printStackTrace(); fail("UnsupportedAudioFileException");
		} catch (IOException e2) {e2.printStackTrace(); fail("IOException");
		} catch (LineUnavailableException e) {e.printStackTrace(); fail("LineUnavailableException");}
	}
	
	@Test
	public void testSoundStatus(){
		System.err.println("This test not require your attention - status - but duration: <2, 4>s");
		SoundPlayer s = new SoundPlayer();
		try {
			assertEquals(SoundPlayer.NO_SOURCE, s.status());
			
			s.setStream(getClass().getResourceAsStream(path));
			assertEquals(SoundPlayer.NOT_STARTED_YET, s.status());
			
			s.soundPlay();
			Thread.sleep(500);
			assertEquals(SoundPlayer.PLAYING, s.status());
			
			s.soundPause();
			Thread.sleep(500);
			assertEquals(SoundPlayer.PAUSE, s.status());
			
			s.soundSetPosition(SoundPlayer.toMicrosecond(200));
			assertEquals(SoundPlayer.ENDED, s.status());
		} catch (Exception e) {e.printStackTrace();}
	}
	
	@Test
	public void testSoundPlayerFowardRewievPosition() {
		System.err.println("This test require your full attention - foward, rewiev, position, duration: 32s");
		SoundPlayer s = new SoundPlayer();
		for(int i = 0; i  < 1000; i++){
			try {
				switch (i) {
				case 0:
					System.out.println("time " + i + " sound start");
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
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
					s.soundStop();
					System.err.println("End");
					return;
				default:
					System.out.println("time: " + i);
					break;
				}
				Thread.sleep(1000);
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	@Test
	public void testSoundPlayerLoop() {
		System.err.println("This test require your full attention - loop, duration: 50s");
		
		SoundPlayer s = new SoundPlayer();
		for(int i = 0; i  < 1000; i++){
			try {
				switch (i) {
				case 0:
					System.out.println("time " + i + " sound start");
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
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
					s.soundPlay();
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
					s.soundStop();
					break;	
				default:
					System.out.println("time: " + i);
					break;
				}
				Thread.sleep(1000);
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	
	@Test
	public void testSoundPlayerStartStopPause() {
		System.err.println("This test require your full attention - start, stop, pause, duration: 32s");
		SoundPlayer s = new SoundPlayer();
		for(int i = 0; i  < 1000; i++){
			try {
				switch (i) {
				case 0:
					System.err.println("Start");
					break;
				case 1:
					System.out.println("time " + i + ": stopMusic - nothing happend" );
					s.soundStop();
					break;
				case 2:
					System.out.println("time " + i + ": pause - nothing happend" );
					s.soundPause();
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
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
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
					s.soundStop();
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
					s.setStream(getClass().getResourceAsStream(path));
					s.soundPlay();
					break;	
				case 20:
					System.out.println("time " + i + ": pause - music stop" );
					s.soundPause();
					break;	
				case 25:
					System.out.println("time " + i + ": play - music start, where was stopped" );
					s.soundPlay();
					break;
				case 30:
					System.out.println("time " + i + ": stopMusic - music stop" );
					s.soundStop();
					break;
				case 32:
					System.err.println("End");
					return;
				default:
					System.out.println("time: " + i);
					break;
				}
				Thread.sleep(1000);
			} catch (Exception e) {e.printStackTrace();}
		}
	}

}
