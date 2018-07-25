package multimedia.sound;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.NoSourceWasGivenException;
import exceptions.SomeSoundActualyRun;
import multimedia.sound.support.ClipT;


public class SoundPlayerTest {
	
	private String absolutePath = "/multimedia/sound/sound.wav";
	
	private String path = "res/multimedia/sound/";
	
	private String file = "sound.wav";
	
	private long duration = 185835102;
	
	private int threadsCount;
	
	@Test(expected=SomeSoundActualyRun.class)
	public void testSetStreamStreamThrowsWhenSomeStreamAlreadyIsSet(){
		try {
			SoundPlayer s = getSPWithSettedStream();
			s.setStream(getClass().getResourceAsStream(absolutePath), false);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	@Test(expected=SomeSoundActualyRun.class)
	public void testSetStreamStringThrowsWhenSomeStreamAlreadyIsSet(){
		try {
			SoundPlayer s = getSPWithSettedStream();
			s.setStream(path + file, false);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	
	@Test(expected=SomeSoundActualyRun.class)
	public void testSetStreamFileThrowsWhenSomeStreamAlreadyIsSet(){
		try {
			SoundPlayer s = getSPWithSettedStream();
			s.setStream(new File(absolutePath), false);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	@Test(expected=SomeSoundActualyRun.class)
	public void testSetStreamUrlThrowsWhenSomeStreamAlreadyIsSet(){
		try {
			SoundPlayer s = getSPWithSettedStream();
			s.setStream(getClass().getResource(absolutePath), false);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	@Test()
	public void testSetStreamStreamWorks(){
		try {
			SoundPlayer s = getSPWithSettedStream();
			AudioInputStream old = s.getStream();
			
			s.setStream(getClass().getResourceAsStream(absolutePath), true);
			assertNotNull(s.getStream());
			assertNotEquals(old, s.getStream());
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	@Test()
	public void testSetStreamUrlWorks(){
		try {
			SoundPlayer s = getSPWithSettedStream();
			AudioInputStream old = s.getStream();
			
			s.setStream(getClass().getResource(absolutePath), true);
			assertNotNull(s.getStream());
			assertNotEquals(old, s.getStream());
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	@Test()
	public void testSetStreamFileWorks(){
		try {
			SoundPlayer s = getSPWithSettedStream();
			AudioInputStream old = s.getStream();
			
			s.setStream(new File(path + file), true);
			assertNotNull(s.getStream());
			assertNotEquals(old, s.getStream());
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	@Test()
	public void testSetStreamStringWorks(){
		try {
			SoundPlayer s = getSPWithSettedStream();
			AudioInputStream old = s.getStream();
			
			s.setStream(path + file, true);
			assertNotNull(s.getStream());
			assertNotEquals(old, s.getStream());
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();fail();}
	}
	
	/*****************************************************************************************/
	
	/**
	 * 
	 * @throws LineUnavailableException
	 * @throws IOException
	 * @throws UnsupportedAudioFileException 
	 */
	@Test
	public void testGetStatusWorks() throws LineUnavailableException, IOException, UnsupportedAudioFileException{
		SoundPlayer sp = new SoundPlayer();
		assertEquals(SoundPlayer.NO_SOURCE, sp.getStatus());
		
		SoundPlayer s = getSPWithSettedStream();
		Clip sound = getMockedClip(s);
		s.setClip(sound);
		
		assertEquals(SoundPlayer.STOPPED, s.getStatus());
		
		sound.open(s.getStream());
		assertEquals(SoundPlayer.PAUSED, s.getStatus());
		
		sound.start();
		assertEquals(SoundPlayer.PLAYED, s.getStatus());
		
		sound.setMicrosecondPosition(duration + 100);
		assertEquals(SoundPlayer.ENDED, s.getStatus());
		
		sound.setMicrosecondPosition(duration/2);
		assertEquals(SoundPlayer.PAUSED, s.getStatus());
		try {Thread.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
		//assertEquals(SoundPlayer.PLAYED, s.getStatus());
		
		sound.start();
		try {Thread.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
		assertEquals(SoundPlayer.PLAYED, s.getStatus());

		sound.stop();
		sound.close();
		sound.flush();
		assertEquals(SoundPlayer.STOPPED, s.getStatus());
		
		sound.open(s.getStream());
		sound.start();
		try {Thread.sleep(20);} catch (InterruptedException e) {e.printStackTrace();}
		assertEquals(SoundPlayer.PLAYED, s.getStatus());

	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testGetDurationThrowsWhenNoStreamGiven(){
		SoundPlayer s = new SoundPlayer();
		s.getDuration();
	}
	
	@Test
	public void testGetDurationWorks() throws LineUnavailableException, IOException{
		SoundPlayer s = getSPWithSettedStream();
		Clip clip = getMockedClip(s);
		s.setClip(clip);
		clip.open(s.getStream());
		
		assertEquals(duration, s.getDuration());
		
		verify(clip, times(1)).open(s.getStream());
		verify(clip, times(1)).getMicrosecondLength();
		verifyNoMoreInteractions(clip);
	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testGetPositionThrowsWhenNoStreamGiven(){
		SoundPlayer s = new SoundPlayer();
		s.getPosition();
	}
	
	@Test
	public void testGetPositionWorks() throws LineUnavailableException, IOException{
		SoundPlayer s = getSPWithSettedStream();
		Clip clip = getMockedClip(s);
		s.setClip(clip);
		clip.open(s.getStream());
		
		long pos = 100000;
		clip.setMicrosecondPosition(pos);
		
		assertEquals(pos, s.getPosition(), 10000);
		
		verify(clip, times(1)).open(s.getStream());
		verify(clip, times(1)).setMicrosecondPosition(pos);
		verify(clip, times(1)).getMicrosecondPosition();
		verifyNoMoreInteractions(clip);
	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testSetPositionThrowsWhenNoStreamGiven(){
		SoundPlayer s = new SoundPlayer();
		s.setPosition(10);
	}
	
	@Test
	public void testSetPositionWorks() throws LineUnavailableException, IOException{
		//TODO data provider only for this method - some extrems
		SoundPlayer s = getSPWithSettedStream();
		Clip clip = getMockedClip(s);
		s.setClip(clip);
		clip.open(s.getStream());
		
		long position = 10000000;
		s.setPosition(position);
		
		assertEquals(position, clip.getMicrosecondPosition());
		verify(clip, times(1)).open(s.getStream());
		verify(clip, times(1)).setMicrosecondPosition(position);
		verify(clip, times(1)).getMicrosecondPosition();
		verifyNoMoreInteractions(clip);
	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testSetLoopThrowsWhenNoStreamGiven(){
		SoundPlayer s = new SoundPlayer();
		s.setLoop(0);
	}
	
	@Test
	public void testSetLoopWorks() throws LineUnavailableException, IOException{
		//TODO data provider only for this method - some extrems
		SoundPlayer s = getSPWithSettedStream();
		Clip clip = getMockedClip(s);
		s.setClip(clip);
		clip.open(s.getStream());
		
		assertEquals(0, s.getLoopCount());
		
		int loop = -1;
		s.setLoop(loop);
		
		assertEquals(Clip.LOOP_CONTINUOUSLY, s.getLoopCount());
		verify(clip, times(1)).loop(Clip.LOOP_CONTINUOUSLY);
		verify(clip, times(1)).open(s.getStream());
		verifyNoMoreInteractions(clip);
	}
	
	/**
	 * @throws IOException 
	 * @throws LineUnavailableException ********************************************/
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testPlayThrowsWhenNoSource() throws LineUnavailableException, IOException{
		SoundPlayer s = new SoundPlayer();
		s.play();
	}
	
	@Test
	public void testPlayWorks() throws LineUnavailableException, IOException{
		SoundPlayer s = getSPWithSettedStream();
		Clip clip = getMockedClip(s);
		s.setClip(clip);
		
		s.play();
		
		assertEquals(SoundPlayer.PLAYED, s.getStatus());
		verify(clip, times(1)).open(s.getStream());
		verify(clip, times(1)).start();
		verify(clip, never()).flush();
		verify(clip, never()).close();
		verify(clip, never()).stop();
	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testStopThrowsWhenNoSource() throws IOException{
		SoundPlayer s = new SoundPlayer();
		s.stop();
	}
	
	@Test
	public void testStopWorks() throws LineUnavailableException, IOException{
		SoundPlayer s = getSPWithSettedStream();
		Clip clip = getMockedClip(s);
		s.setClip(clip);
		
		s.stop();
		assertEquals(SoundPlayer.STOPPED, s.getStatus());
		clip.open(s.getStream());
		s.stop();
		assertEquals(SoundPlayer.STOPPED, s.getStatus());
		
		verify(clip, times(2)).close();
		verify(clip, times(2)).stop();
		verify(clip, times(2)).flush();
		verify(clip, times(2)).isOpen();
		verify(clip, times(1)).open(s.getStream());
		verifyNoMoreInteractions(clip);
	}	

	
	@Test(expected=NoSourceWasGivenException.class)
	public void testPauseThrowsWhenNoSource() throws IOException{
		SoundPlayer s = new SoundPlayer();
		s.pause();
	}
	
	@Test
	public void testPauseWorks() throws LineUnavailableException, IOException{
		SoundPlayer s = getSPWithSettedStream();
		Clip clip = getMockedClip(s);
		s.setClip(clip);

		s.pause();
		assertEquals(SoundPlayer.STOPPED, s.getStatus());
		clip.open(s.getStream());
		s.pause();
		assertEquals(SoundPlayer.PAUSED, s.getStatus());
		
		verify(clip, times(2)).stop();
		verify(clip, times(1)).open(s.getStream());
		verify(clip, never()).flush();
		verify(clip, never()).close();
	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testFowardThrowsWhenNoSource(){
		SoundPlayer s = new SoundPlayer();
		s.foward(1);
	}
	
	@Test
	public void tesFowardWorks() throws LineUnavailableException, IOException{
		//TODO data provider only for this method - some extrems
		SoundPlayer s = getSPWithSettedStream();
		Clip clip = getMockedClip(s);
		s.setClip(clip);
		clip.open(s.getStream());
		
		long interval = 100000000;
		s.foward(interval);
		
		assertEquals(interval, s.getPosition(), 10000);
		verify(clip, times(1)).open(s.getStream());
		verify(clip, times(2)).getMicrosecondPosition();
		verify(clip, times(1)).setMicrosecondPosition(interval);
		verifyNoMoreInteractions(clip);
	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testBackThrowsWhenNoSource(){
		SoundPlayer s = new SoundPlayer();
		s.back(1);
	}
	
	@Test
	public void tesBackWorks() throws LineUnavailableException, IOException{
		//TODO data provider only for this method - some extrems
		SoundPlayer s = getSPWithSettedStream();
		Clip clip = getMockedClip(s);
		s.setClip(clip);
		clip.open(s.getStream());
		
		long interval = 10000000;
		
		s.setPosition(interval);
		
		s.back(interval/2);
		
		assertEquals(interval/2, clip.getMicrosecondPosition(), 10000);
		verify(clip, times(2)).getMicrosecondPosition();
		verify(clip, times(1)).setMicrosecondPosition(interval);
		verify(clip, times(1)).setMicrosecondPosition(interval/2);
		verify(clip, times(1)).open(s.getStream());
		verifyNoMoreInteractions(clip);
	}
	
	/***********************************************************************************/
	private SoundPlayer getSPWithSettedStream(){
		SoundPlayer s = new SoundPlayer();
		try {
			s.setStream(getClass().getResourceAsStream(absolutePath), false);
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
			fail();
		}
		return s;
	}
	
	private Clip getMockedClip(SoundPlayer s){
		try {
			return spy(new ClipT(s.createClip()));
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problem with clip");
		}
	}
	
	@Before
	public void before(){
		this.threadsCount = Thread.activeCount();
	}
	
	@After
	public void after(){
		int actual = Thread.activeCount();
		/*
		if(threadsCount == 2)
			assertTrue(actual - threadsCount < 3);
		else
			assertEquals(0, actual - threadsCount);
		//*/
	}
}
