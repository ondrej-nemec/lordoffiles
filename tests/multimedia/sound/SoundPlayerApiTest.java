package multimedia.sound;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exceptions.NoSourceWasGivenException;
import multimedia.sound.SoundPlayerApi;


public abstract class SoundPlayerApiTest<T extends DataLine> {
	
	protected String path = "/multimedia/sound/sound.wav";

	protected long duration = 185835102; // 185, 835 102 s
	
	private int beforeThreads;
	
	protected T mocktedLine;
	
	protected AudioInputStream mockedStream;
	
	/*********************************************/
	
	@Before
	public void before() {
		beforeThreads = Thread.activeCount();
	}
	
	@After
	public void after() {
		int afterThreads = Thread.activeCount();
		//TODO before and after MUST be same
		assertEquals(beforeThreads, afterThreads);
	}
	
	/*************************************************/
	
	@Test
	public void testGetDurationWorks() throws Throwable{
		SoundPlayerApi<T> p = getMockedApi();  //*/ getSettedT();
		p.play();// Thread.sleep(500);
		
		assertEquals(duration, p.getDuration(), 500);
		
		p.finalize();
	}
	
	@Test
	public void testCloseResourceWorks() throws Throwable{
		SoundPlayerApi<T> p = getMockedApi();
		
		p.closeResources();
		
		verify(mockedStream).close();
		verifyNoMoreInteractions(mockedStream);
		
		verify(mocktedLine).stop();
		verify(mocktedLine).flush();
		verify(mocktedLine).close();
		verifyNoMoreInteractions(mocktedLine);
		
		p.finalize();
	}
	
	//@Test
	public void testCreateLineWorks() {
		//TODO
		fail();
	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testPlayThrowsWhenNoResourceGiven() throws LineUnavailableException, IOException {
		SoundPlayerApi<T> line = getEmptyT();
		line.play();
	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testPauseThrowsWhenNoResourceGiven() {
		SoundPlayerApi<T> line = getEmptyT();
		line.pause();
	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testStopThrowsWhenNoResourceGiven() {
		SoundPlayerApi<T> line = getEmptyT();
		line.stop();
	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testFowardThrowsWhenNoResourceGiven() {
		SoundPlayerApi<T> line = getEmptyT();
		line.foward(1);
	}

	@Test(expected=NoSourceWasGivenException.class)
	public void testBackThrowsWhenNoResourceGiven() throws LineUnavailableException, IOException {
		SoundPlayerApi<T> line = getEmptyT();
		line.back(1);
	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testGetDurationThrowsWhenNoResourceGiven() {
		SoundPlayerApi<T> line = getEmptyT();
		line.getDuration();
	}
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testSetPositionThrowsWhenNoResourceGiven() {
		SoundPlayerApi<T> line = getEmptyT();
		line.setPosition(0);
	}	
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testSetLoopThrowsWhenNoResourceGiven() {
		SoundPlayerApi<T> line = getEmptyT();
		line.setLoop(0);
	}	
	
	@Test(expected=NoSourceWasGivenException.class)
	public void testGetPositionThrowsWhenNoResourceGiven() {
		SoundPlayerApi<T> line = getEmptyT();
		line.getPosition();
	}
	
	/******************************************************/
	
	protected abstract SoundPlayerApi<T> getEmptyT();
	
	protected abstract T convert(T line);
	
	protected SoundPlayerApi<T> getSettedT() {
		SoundPlayerApi<T> line = getEmptyT();
		try {
			line.setResource(getClass().getResourceAsStream(path));
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
			fail();
		}
		return line;
	}

	protected SoundPlayerApi<T> getMockedApi(){
		SoundPlayerApi<T> p = getSettedT();
		mocktedLine = spy(convert(p.getDataLine()));
		mockedStream = spy(p.getStream());
		try {
			p.setResource(mockedStream, mocktedLine);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
			fail();
		}
		return p;
	}
}
