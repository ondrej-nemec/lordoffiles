package sound.a;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.NoSourceWasGivenException;
import exceptions.SomeSoundActualyRun;
import multimedia.sound.support.SourceDataLineT;


public class SoundPlayerTest4 {
	
	private String absolutePath = "/multimedia/sound/sound.wav";
	
	private String path = "res/multimedia/sound/";
	
	private String file = "sound.wav";
	
	private long duration = 185835102;
	
	private int threadsCount;
	
	@Test(expected=SomeSoundActualyRun.class)
	public void testSetStreamStreamThrowsWhenSomeStreamAlreadyIsSet(){
		try {
			SoundPlayer4 s = getSPWithSettedStream();
			s.setStream(getClass().getResourceAsStream(absolutePath), false);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	@Test(expected=SomeSoundActualyRun.class)
	public void testSetStreamStringThrowsWhenSomeStreamAlreadyIsSet(){
		try {
			SoundPlayer4 s = getSPWithSettedStream();
			s.setStream(path + file, false);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	
	@Test(expected=SomeSoundActualyRun.class)
	public void testSetStreamFileThrowsWhenSomeStreamAlreadyIsSet(){
		try {
			SoundPlayer4 s = getSPWithSettedStream();
			s.setStream(new File(absolutePath), false);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	@Test(expected=SomeSoundActualyRun.class)
	public void testSetStreamUrlThrowsWhenSomeStreamAlreadyIsSet(){
		try {
			SoundPlayer4 s = getSPWithSettedStream();
			s.setStream(getClass().getResource(absolutePath), false);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	@Test()
	public void testSetStreamStreamWorks(){
		try {
			SoundPlayer4 s = getSPWithSettedStream();
			AudioInputStream old = s.getStream();
			
			s.setStream(getClass().getResourceAsStream(absolutePath), true);
			assertNotNull(s.getStream());
			assertNotEquals(old, s.getStream());
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	@Test()
	public void testSetStreamUrlWorks(){
		try {
			SoundPlayer4 s = getSPWithSettedStream();
			AudioInputStream old = s.getStream();
			
			s.setStream(getClass().getResource(absolutePath), true);
			assertNotNull(s.getStream());
			assertNotEquals(old, s.getStream());
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	@Test()
	public void testSetStreamFileWorks(){
		try {
			SoundPlayer4 s = getSPWithSettedStream();
			AudioInputStream old = s.getStream();
			
			s.setStream(new File(path + file), true);
			assertNotNull(s.getStream());
			assertNotEquals(old, s.getStream());
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	@Test()
	public void testSetStreamStringWorks(){
		try {
			SoundPlayer4 s = getSPWithSettedStream();
			AudioInputStream old = s.getStream();
			
			s.setStream(path + file, true);
			assertNotNull(s.getStream());
			assertNotEquals(old, s.getStream());
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	/*****************************************************************************************/
	
	
	
	/***********************************************************************************/
	private SoundPlayer4 getSPWithSettedStream(){
		SoundPlayer4 s = new SoundPlayer4();
		try {
			s.setStream(getClass().getResourceAsStream(absolutePath), false);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
			fail();
		}
		return s;
	}
	
	private SourceDataLine getMockedSourceDataLine(SoundPlayer4 s){
		try {
			return spy(new SourceDataLineT(s.createLine()));
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problem with clip");
		}
	}
}
