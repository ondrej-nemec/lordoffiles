package multimedia.sound;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import multimedia.AudioInputStreamFactory;
import multimedia.DataLineFactory;

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
	public void testPlayWithByteArrayEndToEnd() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		AudioInputStream stream = AudioInputStreamFactory.getStream("src/tests/res/multimedia/sound-input.wav");
		SourceDataLine line = DataLineFactory.getSourceLine(stream);
		
		ByteArrayInputStream data = new ByteArrayInputStream(new byte[] {}); //TODO fill data
		
		Reproductor rep = new Reproductor(line);
		
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.submit(()->{
			try {
				rep.play(format, data);
			} catch (IOException | LineUnavailableException e) {
				fail("LineUnavailableException: " + e.getMessage());
			}
		});
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		rep.play = false;
				
		fail("Not implement - data to assert");
	}
	
}
