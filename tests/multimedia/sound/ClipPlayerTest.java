package multimedia.sound;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import exceptions.NoSourceWasGivenException;
import multimedia.sound.support.ClipT;


public class ClipPlayerTest {

	private String absolutePath = "/multimedia/sound/sound.wav";

	private long duration = 185835102; // 185, 835 102 s
	
	private long accept = 50000;
	
	/*** play ***/
		
	@Test(expected = NoSourceWasGivenException.class)
	public void testPlayThrowsWhenNoSourceGiven() throws Throwable {
		ClipPlayer p = new ClipPlayer();
		
		p.play();
		p.finalize();
	}
	
	
	@Test
	public void testPlayWorks() throws Throwable{
		ClipPlayer p = getPlayerWithStream();
		Clip clip = getMockedClip(p);
		p.setLine(clip);
		
		p.play();
		
		verify(clip).start();
		p.finalize();
	}

	/*** pause 
	 * @throws Throwable ***/
	
	@Test(expected = NoSourceWasGivenException.class)
	public void testPauseThrowsWhenNoSourceGiven() throws Throwable {
		ClipPlayer p = new ClipPlayer();
		
		p.pause();
		p.finalize();
	}
	
	
	@Test
	public void testPauseWorks() throws Throwable{
		ClipPlayer p = getPlayerWithStream();
		Clip clip = getMockedClip(p);
		p.setLine(clip);
		
		p.pause();
		
		verify(clip).stop();
		p.finalize();
	}

	/*** stop 
	 * @throws Throwable ***/
	
	@Test(expected = NoSourceWasGivenException.class)
	public void testStopThrowsWhenNoSourceGiven() throws Throwable {
		ClipPlayer p = new ClipPlayer();
		
		p.stop();
		p.finalize();
	}
		
	@Test
	public void testStopWorks() throws Throwable{
		ClipPlayer p = getPlayerWithStream();
		AudioInputStream is = spy(p.getStream());
		Clip clip = getMockedClip(p);
		p.setStream(is);
		p.setLine(clip);
		
		p.stop();
		
		verify(clip).stop();
		verify(clip).close();
		verify(clip).flush();
		verify(is).close();
		
		p.finalize();
	}

	/*** foward 
	 * @throws Throwable ***/
	
	@Test(expected = NoSourceWasGivenException.class)
	public void testFowardThrowsWhenNoSourceGiven() throws Throwable {
		ClipPlayer p = new ClipPlayer();
		
		long interval = 1000000;
		p.foward(interval);
		p.finalize();
	}
	
	@Test
	public void testFowardWorks() throws Throwable{
		ClipPlayer p = getPlayerWithStream();
		Clip clip = getMockedClip(p);
		p.setLine(clip);
		
		long interval = 1000000;
		long pos = clip.getMicrosecondPosition();
		
		p.foward(interval);
		assertEquals(interval + pos, clip.getMicrosecondPosition(), accept);
		
		p.foward(-1);
		assertEquals(interval + pos, clip.getMicrosecondPosition(), accept);
		
		p.foward(duration + interval);
		assertEquals(duration, clip.getMicrosecondPosition(), accept);
		
		verify(clip, times(6)).getMicrosecondPosition();
		verify(clip, times(1)).setMicrosecondPosition(interval + pos);
		verify(clip, times(2)).setMicrosecondPosition(Mockito.anyLong());

		p.finalize();
	}

	/*** back 
	 * @throws Throwable ***/

	@Test(expected = NoSourceWasGivenException.class)
	public void testBackThrowsWhenNoSourceGiven() throws Throwable {
		ClipPlayer p = new ClipPlayer();
		
		long interval = 1000000;
		p.back(interval);

		p.finalize();
	}
	
	@Test
	public void testBackWorks() throws Throwable{
		ClipPlayer p = getPlayerWithStream();
		Clip clip = getMockedClip(p);
		p.setLine(clip);

		long interval = 1000000;
		long pos = clip.getMicrosecondPosition();
		
		p.back(-1);
		assertEquals(pos, clip.getMicrosecondPosition(), accept);
		
		p.back(pos + interval);
		assertEquals(0, clip.getMicrosecondPosition(), accept);
		
		clip.setMicrosecondPosition(duration);
		p.back(interval);
		assertEquals(duration - interval, clip.getMicrosecondPosition(), accept);
		
		verify(clip, times(6)).getMicrosecondPosition();
		verify(clip, times(1)).setMicrosecondPosition(duration);
		verify(clip, times(1)).setMicrosecondPosition(duration-interval);

		p.finalize();
	}

	/*** getStatus 
	 * @throws Throwable ***/
	
	@Test
	public void testGetStatusWorks() throws Throwable{
		ClipPlayer p = new ClipPlayer();
		
		assertEquals(ClipPlayer.NO_SOURCE, p.getStatus());
		
		p = getPlayerWithStream();
		Clip clip = getMockedClip(p);
		p.setLine(clip);
		
		clip.start();
		Thread.sleep(100);
		assertEquals(ClipPlayer.PLAYED, p.getStatus());
		
		clip.stop();
		assertEquals(ClipPlayer.PAUSED, p.getStatus());
		
		clip.start();
		assertEquals(ClipPlayer.PLAYED, p.getStatus());
		
		clip.stop();
		clip.close();
		assertEquals(ClipPlayer.STOPPED, p.getStatus());
		/*
		clip = getMockedClip(p);
		p.setLine(clip);
		clip.start();
		Thread.sleep(100);
		assertEquals(ClipPlayer.PLAYED, p.getStatus());
		 */
		p.finalize();
	}
	
	/*** duration 
	 * @throws Throwable ***/
	
	@Test(expected = NoSourceWasGivenException.class)
	public void testGetDurationThrowsWhenNoSourceGiven() throws Throwable {
		ClipPlayer p = new ClipPlayer();
		
		p.getDuration();

		p.finalize();
	}
	
	@Test
	public void testGetDurationWorks() throws Throwable {
		ClipPlayer p = getPlayerWithStream();
		Clip clip = getMockedClip(p);
		p.setLine(clip);
		
		assertEquals(duration, p.getDuration());
		
		verify(clip, times(1)).getMicrosecondLength();
		p.finalize();
	}
	
	/*** setLoop 
	 * @throws Throwable ***/
	
	@Test(expected = NoSourceWasGivenException.class)
	public void testSetLoopThrowsWhenNoSourceGiven() throws Throwable {
		ClipPlayer p = new ClipPlayer();
		
		p.setLoop(0);

		p.finalize();
	}
	
	@Test
	public void testSetLoopWorks() throws Throwable{
		ClipPlayer p = getPlayerWithStream();
		Clip clip = getMockedClip(p);
		p.setLine(clip);
		
		p.setLoop(-1);
		verify(clip).loop(Clip.LOOP_CONTINUOUSLY);
		
		p.setLoop(4);
		verify(clip).loop(4);

		p.finalize();
	}
	
	/*** getPosition 
	 * @throws Throwable ***/
	
	@Test(expected = NoSourceWasGivenException.class)
	public void testGetPositionThrowsWhenNoSourceGiven() throws Throwable{
		ClipPlayer p = new ClipPlayer();
		
		p.getPosition();

		p.finalize();
	}
	
	@Test
	public void testGetPositionWorks() throws Throwable{
		ClipPlayer p = getPlayerWithStream();
		Clip clip = getMockedClip(p);
		p.setLine(clip);
		
		long interval = 1000000;
		clip.setMicrosecondPosition(interval);
		
		assertEquals(interval, p.getPosition(), accept);
		
		verify(clip, times(1)).setMicrosecondPosition(interval);
		verify(clip, times(1)).getMicrosecondPosition();

		p.finalize();
	}

	/** setPosition 
	 * @throws Throwable **/
	
	@Test(expected = NoSourceWasGivenException.class)
	public void testSetPositionThrowsWhenNoSourceGiven() throws Throwable{
		ClipPlayer p = new ClipPlayer();
		
		long interval = 10000;
		p.setPosition(interval);
		
		p.finalize();
	}
	
	@Test
	public void testSetPositionWorks() throws Throwable{
		ClipPlayer p = getPlayerWithStream();
		Clip clip = getMockedClip(p);
		p.setLine(clip);
		
		long interval = 10000000;
		
		p.setPosition(interval);
		assertEquals(interval, clip.getMicrosecondPosition(), accept);
		
		p.setPosition(duration + interval);
		assertEquals(duration, clip.getMicrosecondPosition(), accept);
		
		p.setPosition(- duration - 1);
		assertEquals(0, clip.getMicrosecondPosition(), accept);
		
		verify(clip, times(1)).setMicrosecondPosition(interval);
		verify(clip, times(3)).setMicrosecondPosition(Mockito.anyLong());
		verify(clip, times(3)).getMicrosecondPosition();

		p.finalize();
	}
	
	/*** actionAfterStreamSettting ****/
	//@Test
	public void testActionAfterStremSettingWorks(){
		fail();
	}
	
	/*****************************/
	
	@After
	public void after(){
	//	assertEquals(3, Thread.activeCount());
		assertTrue(Thread.activeCount() > 1 && Thread.activeCount() < 5);
	}
	
	/***************************************************/
	
	private Clip getMockedClip(ClipPlayer p){
		return spy(new ClipT(p.getDataLine()));
	}
	
	private ClipPlayer getPlayerWithStream(){
		ClipPlayer p = new ClipPlayer();
		try {
			p.setStream(getClass().getResourceAsStream(absolutePath));
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
			fail();
		}
		return p;
	}	
}
