package multimedia.audio;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.function.Consumer;

import javax.sound.sampled.AudioInputStream;

import org.junit.Test;

import multimedia.audio.AudioLoader;

public class AudioLoaderTest {
	
	private final AudioInputStream stream;
	private final AudioLoader loader;
	
	public AudioLoaderTest() {
		this.stream = mock(AudioInputStream.class);
		this.loader = new AudioLoader();
	}
	
	@Test
	public void testLoadWithConsumerWorks() throws IOException {
		when(stream.read(any(), eq(0), eq(loader.BUFFER_SIZE))).thenReturn(0).thenReturn(-1);
		@SuppressWarnings("unchecked")
		Consumer<byte[]> biConsumer = mock(Consumer.class);
		
		loader.load(stream, biConsumer);
		verify(biConsumer, times(1)).accept(any());
	}
	
	@Test
	public void testLoadWithByteArrayWorks() {
		try {
			when(stream.read(any(), eq(0), eq(loader.BUFFER_SIZE))).thenReturn(0).thenReturn(-1);
			ByteArrayOutputStream out = mock(ByteArrayOutputStream.class);
			
			loader.load(stream, out);
			
			verify(out, times(1)).write(any());
		} catch (IOException e) {
			fail("IOException " + e.getMessage());
		}
	}
	
	@Test
	public void testLoadWithPipeWorks() {
		try(PipedOutputStream pos = mock(PipedOutputStream.class)) {
			when(stream.read(any(), eq(0), eq(loader.BUFFER_SIZE))).thenReturn(0).thenReturn(-1);
						
			loader.load(stream, pos);
			
			verify(pos, times(1)).write(any());
		} catch (IOException e) {
			fail("IOException " + e.getMessage());
		}
	}	
	
	@Test
	public void testLoadWithByteArrayEndToEnd() {
		fail("Not implement");
	}
	
}
