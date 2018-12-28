package multimedia.sound;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import org.junit.Test;

public class SoundPlayerTest {
	
	private final AudioInputStream stream;
	private final SourceDataLine line;
	private final SoundPlayer player;
	
	public SoundPlayerTest() {
		this.stream = mock(AudioInputStream.class);
		this.line = mock(SourceDataLine.class);
		this.player = new SoundPlayer(stream, line);
	}

	@Test
	public void testPlayWorksWithPlayAction() throws IOException, LineUnavailableException {
		when(stream.read(any(), eq(0), eq(player.BUFFER_SIZE))).thenReturn(0).thenReturn(-1);
		
		player.play();
		
		verify(stream, times(2)).read(any(), eq(0), eq(player.BUFFER_SIZE));
		verify(line, times(1)).write(any(), eq(0), anyInt());

		verify(line, times(1)).open(any());
		verify(line, times(1)).start();
		verify(line, times(1)).close();
		verify(line, times(1)).stop();
		verify(stream, times(1)).getFormat();
		
		verifyNoMoreInteractions(stream);
		verifyNoMoreInteractions(line);
	}

	//TODO how to test this infinity cyklus
	//@Test
	public void testPlayWorksWithPauseAction() throws IOException, LineUnavailableException {
		player.ACTION = PlayingAction.PAUSE;
		player.play();
		verifyZeroInteractions(stream);
		verifyZeroInteractions(line);
	}

	@Test
	public void testPlayWorksWithStopAction() throws IOException, LineUnavailableException {
		player.ACTION = PlayingAction.STOP;
		
		player.play();
		
		verify(line, times(1)).open(any());
		verify(line, times(1)).start();
		verify(line, times(1)).close();
		verify(line, times(1)).stop();
		verify(stream, times(1)).getFormat();
		
		verifyNoMoreInteractions(stream);
		verifyNoMoreInteractions(line);
	}
}
