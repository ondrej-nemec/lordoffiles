package multimedia.sound;

import javax.sound.sampled.SourceDataLine;

import multimedia.sound.support.SourceDataLineT;

public class BufferPlayerTest extends SoundPlayerApiTest<SourceDataLine>{

	@Override
	protected SoundPlayerApi<SourceDataLine> getEmptyT() {
		return new BufferPlayer();
	}

	@Override
	protected SourceDataLine convert(SourceDataLine line) {
		return new SourceDataLineT(line);
	}

}
