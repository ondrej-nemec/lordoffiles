package multimedia.audio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
	public void testLoadWorks() throws IOException {
		when(stream.read(any(), eq(0), eq(loader.BUFFER_SIZE))).thenReturn(0).thenReturn(-1);
		AudioLoader.Writer writer = mock(AudioLoader.Writer.class);
		
		loader.load(stream, writer);
		verify(writer, times(1)).write(any(), eq(0), anyInt());
	}
		
	@Test
	public void testLoadWithByteArrayEndToEnd() throws UnsupportedAudioFileException, IOException {
		AudioInputStream stream = AudioInputStreamFactory.getStream("src/tests/res/multimedia/sound-input.wav");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		loader.load(stream, out);		
		
		//TODO fill data
		fail("Not implement - data to assert");
		
		byte[] actual = new byte[] {};
		assertEquals(actual, out.toByteArray());
		
	}
}
