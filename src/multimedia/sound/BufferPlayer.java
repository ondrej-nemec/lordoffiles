package multimedia.sound;

import java.io.IOException;
import java.util.function.Function;

import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class BufferPlayer extends SoundPlayerApi<SourceDataLine>{

	class Play extends Thread {
		
		@Override
		public void run() {
			super.run();
			int bufferSize = 128000;
			byte[] data = new byte[bufferSize];
			int nBytesRead = 0;
			
			while(nBytesRead != -1 ){
				if(!isPaused) {
					try {
						while(toSkip > 0){
							toSkip = toSkip - stream.skip(toSkip);
						}
						nBytesRead = stream.read(data, 0, data.length);
					} catch (IOException e) {e.printStackTrace();}
					
					if(nBytesRead >= 0){
						/*int nBytesWritten =*/ line.write(data, 0, nBytesRead);
					}
				}
			}
			if(actualLoop < loopCount && actualLoop >= 0){
				try {
					stream.reset();
					run();
				} catch (IOException e) {e.printStackTrace();}
				actualLoop++;
			}
		}
	}
	
	private int actualLoop = 0;
	
	private boolean isPaused;
	
	private long toSkip = 0;
	/***************/
	
	private Thread run;
	
	@Override
	protected Function<DataLine, SourceDataLine> fromLineToT() {
		return (a)->(SourceDataLine)a;
	}
	
	@Override
	protected Class<SourceDataLine> clazz() {
		return SourceDataLine.class;
	}

	@Override
	public void play() throws LineUnavailableException, IOException {
		throwIfNoResource();
		//after pause
		isPaused = false;
		//after stop
		if(!line.isOpen()){
			if(stream.markSupported())
				stream.reset();
			line.open(stream.getFormat());
			line.start();
			
			run = new Play();
			run.start();
		}
		//nothing to do if it's not stopped or paused
	}

	@Override
	public void pause() {
		throwIfNoResource();
		if(line.isOpen())
			isPaused = true;
	}

	@Override
	public void stop() {
		throwIfNoResource();
		run.interrupt();
		run = null;
		line.stop();
		line.close();
		isPaused = false;
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
	}

	@Override
	public int getStatus() {
		if(line == null || stream == null)
			return SoundPlayerApi.NO_SOURCE;
		if(isPaused)
			return SoundPlayerApi.PAUSED;
		if(line.isActive())
			return SoundPlayerApi.PLAYED;
		return SoundPlayerApi.STOPPED;
	}

	@Override
	public long getDuration() {
		// length of stream in sample frames * actual position in micro-seconds / actual position in sample frames
		return (long)stream.getFrameLength() * line.getMicrosecondPosition()/(long)line.getLongFramePosition();
	}

	@Override
	public void setPosition(long microSeconds) {
		if(microSeconds > getPosition()){
			//TODO sometimes skip, sometime not
			toSkip = microSeconds  //micro-seconds to skip
					* line.getLongFramePosition() / (long)line.getMicrosecondPosition() //how many sample frame has one micro-second
					* format.getSampleSizeInBits() / (long) 8; // size of sample frame in bytes
		}else if(microSeconds < getPosition()){
			//TODO
			/*
			long msInterval = getPosition() - microSeconds;
			loopCount = 1;
			foward(getDuration() + msInterval);
			*/
		}
	}

	@Override
	public void setLoop(int count) {
		this.loopCount = count;
	}

}
