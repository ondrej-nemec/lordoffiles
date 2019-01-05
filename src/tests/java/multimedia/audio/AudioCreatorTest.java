package multimedia.audio;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;

import org.junit.Ignore;
import org.junit.Test;

public class AudioCreatorTest {

	private final AudioFormat format;
	private final AudioFileFormat.Type type;
	private final OutputStream os;
	private final AudioCreator creator;
		
	public AudioCreatorTest() {
		this.format = mock(AudioFormat.class);
		this.type = mock(AudioFileFormat.Type.class);
		this.os = mock(OutputStream.class);
		this.creator = spy(new AudioCreator(os));
	}

	@Ignore("hard test - static function audio system")
	@Test
	public void testSaveWithByteArrayWorks() throws IOException {
		ByteArrayOutputStream data = mock(ByteArrayOutputStream.class);
		when(data.toByteArray()).thenReturn(new byte[] {0});
		when(format.getFrameSize()).thenReturn(1);
		
		creator.save(format, type, data);
		
		verify(data, times(1)).toByteArray();
		verify(format, times(1)).getFrameSize();
	}

	@Ignore("hard test - static function audio system")
	@Test
	public void testSaveWithPipeWorks() throws IOException {
		PipedInputStream data = mock(PipedInputStream.class);
		when(data.read(any())).thenReturn(0).thenReturn(-1);
		when(format.getFrameSize()).thenReturn(1);
		
		creator.save(format, type, data);
		
		verify(data, times(2)).read(any());
		verify(creator, times(1)).save(format, type, data);
	}
	
	@Test
	public void testSaveWithByteArrayEndToEnd() throws IOException {
		try (OutputStream os = new FileOutputStream("src/tests/res/multimedia/sound-output.wav")) {
			AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
			AudioCreator cr = new AudioCreator(os);
			
			//TODO fill data
			fail("Not implement - fill data");
			ByteArrayOutputStream data = new ByteArrayOutputStream();
			
			cr.save(format, AudioFileFormat.Type.AU, data);
		} catch (IOException e) {
			throw new IOException(e);
		}
	}
	
}
