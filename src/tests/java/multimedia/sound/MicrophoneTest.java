package multimedia.sound;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.util.function.Consumer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.junit.Test;

public class MicrophoneTest {
	
	private final TargetDataLine line;
	private final AudioFormat format;
	private final Microphone mic;
	
	public MicrophoneTest() {
		this.line = mock(TargetDataLine.class);
		this.format = mock(AudioFormat.class);
		this.mic = new Microphone();
	}

	@Test
	public void testCaptureWithConsumerWorks() {
		try {
			when(line.read(any(), eq(0), eq(mic.getBufferSize()))).thenReturn(1).thenReturn(-1);
			
			@SuppressWarnings("unchecked")
			Consumer<byte[]> consumer = mock(Consumer.class);
			
			mic.capture(line, format, consumer);
			
			verify(consumer, times(2)).accept(any());
			verify(line, times(1)).open(format);
			verify(line, times(1)).start();
			verify(line, times(1)).stop();
			verify(line, times(1)).drain();
			verify(line, times(1)).close();
			
			
		} catch (LineUnavailableException e) {
			fail("LineUnavailableException " + e.getMessage());
		}
	}
	
	@Test
	public void testCaptureWithArrayWorks() {
		try {
			when(line.read(any(), eq(0), eq(mic.getBufferSize()))).thenReturn(1).thenReturn(-1);
			
			ByteArrayOutputStream output = mock(ByteArrayOutputStream.class);
			
			mic.capture(line, format, output);
			
			verify(output, times(1)).write(any(), eq(0), eq(mic.getBufferSize()));
			verify(line, times(1)).open(format);
			verify(line, times(1)).start();
			verify(line, times(1)).stop();
			verify(line, times(1)).drain();
			verify(line, times(1)).close();
			
			
		} catch (LineUnavailableException e) {
			fail("LineUnavailableException " + e.getMessage());
		}
	}
	
}
