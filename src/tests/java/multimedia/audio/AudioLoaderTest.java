package multimedia.audio;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.function.BiConsumer;

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
	public void testLoadWithConsumerWorks() {
		try {
			when(stream.read(any(), eq(0), eq(loader.BUFFER_SIZE))).thenReturn(0).thenReturn(-1);
			@SuppressWarnings("unchecked")
			BiConsumer<byte[], Integer> biConsumer = mock(BiConsumer.class);
			
			loader.load(stream, biConsumer);
			verify(biConsumer, times(1)).accept(any(), eq(0));
		} catch (IOException e) {
			fail("IOException " + e.getMessage());
		}
	}

}
