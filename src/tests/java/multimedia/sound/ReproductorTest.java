package multimedia.sound;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;
import java.util.function.Supplier;

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
	public void testPlayWorks() throws LineUnavailableException, IOException {
		@SuppressWarnings("unchecked")
		Supplier<byte[]> consumer = mock(Supplier.class);
		AudioFormat format = mock(AudioFormat.class);
		reproductor.play(format, consumer);
		
		verify(line, times(1)).write(any(), eq(0), anyInt());
		verify(consumer, times(1)).get();
				
		verify(line, times(1)).open(any());
		verify(line, times(1)).start();
		verify(line, times(1)).close();
		verify(line, times(1)).stop();
		
		verifyNoMoreInteractions(line);
	}
	
}
