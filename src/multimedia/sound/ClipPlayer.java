package multimedia.sound;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

import exceptions.NoSourceWasGivenException;

public class ClipPlayer extends AbstractSoundPlayer<Clip> implements SoundPlayerAPI{

	private int loopCount = 0;
	
	@Override
	public void play() throws LineUnavailableException, IOException {
		if(stream == null)
			throw new NoSourceWasGivenException();
	//	if(line == null || !line.isOpen())
	//		actionAfterStreamSetting();
		line.start();
	}

	@Override
	public void pause() {
		if(line == null)
			throw new NoSourceWasGivenException();
		line.stop();
	}

	@Override
	public void stop() throws IOException {
		if(line == null || stream == null)
			throw new NoSourceWasGivenException();
		line.stop();
		line.close();
		line.flush();
		stream.close();
		line = null;
		stream = null;
	}

	@Override
	public void foward(long msInterval) {
		if(line == null)
			throw new NoSourceWasGivenException();
		if(msInterval >= 0)
			setPosition(getPosition() + msInterval);
	}

	@Override
	public void back(long msInterval) {
		if(line == null)
			throw new NoSourceWasGivenException();
		if(msInterval >= 0)
			setPosition(getPosition() - msInterval);
	}

	@Override
	public int getStatus() {
		if(line == null || stream == null)
			return ClipPlayer.NO_SOURCE;
		if(line.isRunning())
			return ClipPlayer.PLAYED;
		if(line.isOpen())
			return ClipPlayer.PAUSED;
		return ClipPlayer.STOPPED;
	}

	@Override
	public long getDuration() {
		if(line == null)
			throw new NoSourceWasGivenException();
		return line.getMicrosecondLength();
	}

	@Override
	public void setLoop(int count) {
		if(line == null)
			throw new NoSourceWasGivenException();
		if(count < 0){
			this.loopCount = Clip.LOOP_CONTINUOUSLY;
			line.loop(Clip.LOOP_CONTINUOUSLY);
		}else{
			loopCount = count;
			line.loop(count);
		}
	}

	@Override
	public int getLoopCount() {
		return loopCount;
	}

	@Override
	public long getPosition() {
		if(line == null)
			throw new NoSourceWasGivenException();
		return line.getMicrosecondPosition();
	}

	@Override
	public void setPosition(long microSeconds) {
		if(line == null)
			throw new NoSourceWasGivenException();
		line.setMicrosecondPosition(microSeconds);
	}

	@Override
	protected void actionAfterStreamSetting() throws LineUnavailableException, IOException {
		format = stream.getFormat();		
		DataLine.Info info = new DataLine.Info(Clip.class, format);
		line = (Clip)AudioSystem.getLine(info);
		line.open(stream);
	}

}
