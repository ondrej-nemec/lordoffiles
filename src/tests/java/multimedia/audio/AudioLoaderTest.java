package multimedia.audio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.function.Consumer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.Test;

import multimedia.AudioInputStreamFactory;
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
	public void testLoadWithByteArrayWorks() throws IOException {
		when(stream.read(any(), eq(0), eq(loader.BUFFER_SIZE))).thenReturn(0).thenReturn(-1);
		ByteArrayOutputStream out = mock(ByteArrayOutputStream.class);
		
		loader.load(stream, out);
		
		verify(out, times(1)).write(any());
	}
	
	@Test
	public void testLoadWithPipeWorks() throws IOException {
		PipedOutputStream pos = mock(PipedOutputStream.class);
		when(stream.read(any(), eq(0), eq(loader.BUFFER_SIZE))).thenReturn(0).thenReturn(-1);
					
		loader.load(stream, pos);
		
		verify(pos, times(1)).write(any());
	}	
	
	@Test
	public void testLoadWithByteArrayEndToEnd() throws UnsupportedAudioFileException, IOException {
		AudioInputStream stream = AudioInputStreamFactory.getStream("src/tests/res/multimedia/sound-input.wav");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		loader.load(stream, out);		
		
		//TODO fill data
		byte[] actual = new byte[] {};
		assertEquals(actual, out.toByteArray());
		
		fail("Not implement - data to assert");	}
	
}
