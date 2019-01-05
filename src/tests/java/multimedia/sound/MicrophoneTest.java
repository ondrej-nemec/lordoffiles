package multimedia.sound;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.junit.Test;

import multimedia.DataLineFactory;

public class MicrophoneTest {
	
	private final TargetDataLine line;
	private final AudioFormat format;
	private final Microphone mic;
	
	public MicrophoneTest() {
		this.line = mock(TargetDataLine.class);
		this.format = new AudioFormat(8000.0f, 16, 1, true, true);
		this.mic = new Microphone();
	}

	@Test
	public void testCaptureWithConsumerWorks() throws LineUnavailableException {
		when(line.read(any(), eq(0), eq(mic.getBufferSize()))).thenReturn(1).thenReturn(-1);
		
		@SuppressWarnings("unchecked")
		Consumer<byte[]> consumer = mock(Consumer.class);
		
		mic.capture(line, format, consumer);
		
		verify(consumer, times(1)).accept(any());
		verify(line, times(1)).open(format);
		verify(line, times(1)).start();
		verify(line, times(1)).stop();
		verify(line, times(1)).drain();
		verify(line, times(1)).close();

	}

	@Test
	public void testCaptureWithByteArrayWorks() throws LineUnavailableException {
		when(line.read(any(), eq(0), eq(mic.getBufferSize()))).thenReturn(1).thenReturn(-1);
		
		ByteArrayOutputStream stream = mock(ByteArrayOutputStream.class);
		
		mic.capture(line, format, stream);
		
		verify(stream, times(1)).write(any(), eq(0), anyInt());
		verify(line, times(1)).open(format);
		verify(line, times(1)).start();
		verify(line, times(1)).stop();
		verify(line, times(1)).drain();
		verify(line, times(1)).close();

	}

	@Test
	public void testCaptureWithPipeWorks() throws LineUnavailableException, IOException {
		when(line.read(any(), eq(0), eq(mic.getBufferSize()))).thenReturn(1).thenReturn(-1);
		
		PipedOutputStream pipe = mock(PipedOutputStream.class);
		
		mic.capture(line, format, pipe);
		
		verify(pipe, times(1)).write(any(), eq(0), anyInt());
		verify(line, times(1)).open(format);
		verify(line, times(1)).start();
		verify(line, times(1)).stop();
		verify(line, times(1)).drain();
		verify(line, times(1)).close();
	}
	
	@Test
	public void testCaptureWithByteArrayEndToEnd() throws LineUnavailableException {
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true); 
		TargetDataLine line = DataLineFactory.getTargetLine(format);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.submit(()->{
			try {
				mic.capture(line, format, stream);
			} catch (LineUnavailableException e) {
				fail("LineUnavailableException: " + e.getMessage());
			}
		});
		try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		mic.capture = false;
		
		//TODO fill data
		byte[] actual = new byte[] {};
		assertEquals(actual, stream.toByteArray());
		
		fail("Not implement - data to assert");
	}
}
