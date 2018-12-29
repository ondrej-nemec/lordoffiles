package multimedia.sound;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.junit.Ignore;
import org.junit.Test;

public class ReproductorTest {

	private final SourceDataLine line;
	
	public ReproductorTest() {
		this.line = mock(SourceDataLine.class);
	}
	
	//TODO Reproductor API is not clear yet
	
	/*
	
	@Test
	public void testPlayWorksWithPlayAction() throws IOException, LineUnavailableException {
		when(stream.read(any(), eq(0), eq(loader.BUFFER_SIZE))).thenReturn(0).thenReturn(-1);
		
		loader.play();
		
		verify(stream, times(2)).read(any(), eq(0), eq(loader.BUFFER_SIZE));
		verify(line, times(1)).write(any(), eq(0), anyInt());

		verify(line, times(1)).open(any());
		verify(line, times(1)).start();
		verify(line, times(1)).close();
		verify(line, times(1)).stop();
		verify(stream, times(1)).getFormat();
		
		verifyNoMoreInteractions(stream);
		verifyNoMoreInteractions(line);
	}
	
	@Test
	@Ignore //TODO how to test this infinity cyklus
	public void testPlayWorksWithPauseAction() throws IOException, LineUnavailableException {
		loader.ACTION = PlayingAction.PAUSE;
		loader.play();
		verifyZeroInteractions(stream);
		verifyZeroInteractions(line);
	}

	@Test
	public void testPlayWorksWithStopAction() throws IOException, LineUnavailableException {
		loader.ACTION = PlayingAction.STOP;
		
		loader.play();
		
		verify(line, times(1)).open(any());
		verify(line, times(1)).start();
		verify(line, times(1)).close();
		verify(line, times(1)).stop();
		verify(stream, times(1)).getFormat();
		
		verifyNoMoreInteractions(stream);
		verifyNoMoreInteractions(line);
	}
	*/
}
