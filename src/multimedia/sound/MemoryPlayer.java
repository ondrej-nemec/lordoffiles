package multimedia.sound;

import java.io.IOException;
import java.util.function.Function;

import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

public class MemoryPlayer extends SoundPlayerApi<Clip> {

	@Override
	protected Function<DataLine, Clip> fromLineToT() {
		return (a)->(Clip)a;
	}
	
	@Override
	protected Class<Clip> clazz() {
		return Clip.class;
	}
	
	@Override
	public void play() throws LineUnavailableException, IOException {
		throwIfNoResource();
		if(!line.isOpen()){
			line = createLine();
			line.open(stream);
		}
		line.start();
	}

	@Override
	public void pause() {
		throwIfNoResource();
		line.stop();
	}

	@Override
	public void stop() {
		throwIfNoResource();
		line.stop();
		line.close();
		//line.flush();
	}

	@Override
	public void foward(long msInterval) {
		throwIfNoResource();
		if(msInterval >= 0)
			setPosition(getPosition() + msInterval);
	}

	@Override
	public void back(long msInterval) {
		throwIfNoResource();
		if(msInterval >= 0)
			setPosition(getPosition() - msInterval);
		line.start();
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		//foward more that duration - stop event
		return 0;
	}

	@Override
	public long getDuration() {
		throwIfNoResource();
		return line.getMicrosecondLength();
	}

	@Override
	public void setLoop(int count) {
		throwIfNoResource();
		if(count < 0){
			this.loopCount = Clip.LOOP_CONTINUOUSLY;
			line.loop(Clip.LOOP_CONTINUOUSLY);
		}else{
			loopCount = count;
			line.loop(count);
		}
	}

	@Override
	public void setPosition(long microSeconds) {
		throwIfNoResource();
		line.setMicrosecondPosition(microSeconds);
	}

}
