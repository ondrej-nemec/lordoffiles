package multimedia.sound;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import javax.sound.sampled.Clip;

import org.junit.Test;
import org.mockito.Mockito;

import multimedia.sound.support.ClipT;

public class MemoryPlayerTest extends SoundPlayerApiTest<Clip> {
	
	private long delta = 100000;

	@Override
	protected SoundPlayerApi<Clip> getEmptyT() {
		return new MemoryPlayer();
	}

	@Override
	protected Clip convert(Clip line) {
		return new ClipT(line);
	}
	
	@Test
	public void testPlayWorks() throws Throwable{
		SoundPlayerApi<Clip> p = getMockedApi();
		p.play();
		
		verify(mocktedLine, times(1)).isOpen();
		verify(mocktedLine, times(1)).start();
		verify(mocktedLine, times(1)).open(mockedStream);
		verifyNoMoreInteractions(mocktedLine);

		p.finalize();
	}
	
	@Test
	public void testPauseWorks() throws Throwable{
		SoundPlayerApi<Clip> p = getMockedApi();
		p.pause();
		
		verify(mocktedLine, times(1)).stop();
		verifyNoMoreInteractions(mocktedLine);

		p.finalize();
	}
	
	@Test
	public void testStopWorks() throws Throwable{
		SoundPlayerApi<Clip> p = getMockedApi();
		p.stop();
		
		verify(mocktedLine, times(1)).stop();
		verify(mocktedLine, times(1)).close();
		verifyNoMoreInteractions(mocktedLine);

		p.finalize();
	}
	
	@Test
	public void testFowardWorks() throws Throwable{
		SoundPlayerApi<Clip> p = getMockedApi();
		long interval = 1000000;
		p.play();
		long pos = p.getPosition();
		
		p.foward(interval);
		
		assertEquals(interval + pos, p.getPosition(), delta);
		
		verify(mocktedLine, times(3)).getMicrosecondPosition();
		verify(mocktedLine, times(1)).setMicrosecondPosition(interval + pos);

		p.finalize();
	}
	
	@Test
	public void testBackWorks() throws Throwable{
		SoundPlayerApi<Clip> p = getMockedApi();
		long interval = 2000000;
		p.play();
		long pos = p.getPosition();
		
		p.foward(interval);
		p.back(interval/2);
		
		assertEquals(interval/2 + pos, p.getPosition(), delta);
		
		verify(mocktedLine, times(2)).setMicrosecondPosition(Mockito.anyLong());
		verify(mocktedLine, times(4)).getMicrosecondPosition();

		p.finalize();
	}
	
	@Test
	public void testGetStatusWorks() throws Throwable{
		SoundPlayerApi<Clip> p = getEmptyT();
		
		assertEquals(MemoryPlayer.NO_SOURCE, p.getStatus());
		
		p = getMockedApi();
		p.play();
		assertEquals(MemoryPlayer.PLAYED, p.getStatus());
		
		p.pause();
		assertEquals(MemoryPlayer.PAUSED, p.getStatus());
		
		p.stop();
		assertEquals(MemoryPlayer.STOPPED, p.getStatus());
		
		verify(mocktedLine, times(1)).open(mockedStream);
		verify(mocktedLine, times(1)).start();
		verify(mocktedLine, times(1)).close();
		verify(mocktedLine, times(2)).stop();
		verify(mocktedLine, times(3)).isOpen();
		verify(mocktedLine, times(3)).isActive();
		
		p.finalize();
	}
	
	@Test
	public void testSetLoopWorks() throws Throwable{
		SoundPlayerApi<Clip> p = getMockedApi();
		
		p.setLoop(4);
		verify(mocktedLine, times(1)).loop(4);

		p.setLoop(-1);
		verify(mocktedLine, times(1)).loop(Clip.LOOP_CONTINUOUSLY);
		
		verifyNoMoreInteractions(mocktedLine);
		
		p.finalize();
	}
	
	@Test
	public void setPositionWorks() throws Throwable{
		SoundPlayerApi<Clip> p = getMockedApi();
		long interval = 100000;
		
		p.setPosition(interval);
		
		verify(mocktedLine, times(1)).setMicrosecondPosition(interval);
		verifyNoMoreInteractions(mocktedLine);

		p.finalize();
	}
	
}
