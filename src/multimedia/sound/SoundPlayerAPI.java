package multimedia.sound;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;

public interface SoundPlayerAPI {
	
	public void play() throws LineUnavailableException, IOException;
	
	public void pause();
	
	public void stop() throws IOException ;
	
	public void foward(long msInterval);
	
	public void back(long msInterval);
	
	public int getStatus();
	
	public long getDuration();
	
	public void setLoop(int count);
	
	public int getLoopCount();
	
	public long getPosition();
	
	public void setPosition(long microSeconds);
}
