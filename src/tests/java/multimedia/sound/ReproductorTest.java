package multimedia.sound;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PipedInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.junit.Test;

public class ReproductorTest {

	private final SourceDataLine line;
	private final Reproductor reproductor;
	
	public ReproductorTest() {
		this.line = mock(SourceDataLine.class);
		this.reproductor = new Reproductor(line);
	}
	
	@Test
	public void testPlayWithByteArrayWorks() throws LineUnavailableException, IOException {
		ByteArrayInputStream data = mock(ByteArrayInputStream.class);
		when(data.read(any())).thenReturn(0).thenReturn(-1);
		
		AudioFormat format = mock(AudioFormat.class);
		reproductor.play(format, data);
		
		verify(line, times(1)).write(any(), eq(0), anyInt());
		verify(data, times(2)).read(any());
				
		verify(line, times(1)).open(any());
		verify(line, times(1)).start();
		verify(line, times(1)).close();
		verify(line, times(1)).stop();
		
		verifyNoMoreInteractions(line);
	}
	
	@Test
	public void testPlayWithPipeWorks() throws LineUnavailableException, IOException {
		PipedInputStream data = mock(PipedInputStream.class);
		when(data.read(any())).thenReturn(0).thenReturn(-1);
		
		AudioFormat format = mock(AudioFormat.class);
		reproductor.play(format, data);
		
		verify(line, times(1)).write(any(), eq(0), anyInt());
		verify(data, times(2)).read(any());
				
		verify(line, times(1)).open(any());
		verify(line, times(1)).start();
		verify(line, times(1)).close();
		verify(line, times(1)).stop();
		
		verifyNoMoreInteractions(line);
	}
	
	@Test
	public void testPlayWithByteArrayEndToEnd() {
		fail("Not implement");
	}
	
}
