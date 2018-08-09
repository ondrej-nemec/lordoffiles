package multimedia.sound;

import java.io.IOException;
import java.util.function.Function;

import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class BufferPlayer extends SoundPlayerApi<SourceDataLine>{

	class Play extends Thread {
		
		public Play() {
			super("Buffer player");
		}
		
		@Override
		public void run() {
			super.run();
			play();
		}
		
		private void play(){
			int bufferSize = 128000;
			byte[] data = new byte[bufferSize];
			int nBytesRead = 0;
			
			while(nBytesRead != -1 ){
				if(!isPaused) {
					try {
						long skipped = -1;
						while(toSkip > 0 && skipped != 0){
							synchronize();
							skipped = stream.skip(toSkip);
							toSkip = toSkip - skipped;
						}
						nBytesRead = stream.read(data, 0, data.length);
					} catch (IOException e) {e.printStackTrace();}
					
					if(nBytesRead >= 0){
						/*int nBytesWritten =*/ line.write(data, 0, nBytesRead);
					}else if(actualLoop < loopCount && actualLoop >= 0){
						actualLoop++;
						try {
							stream.reset();
							nBytesRead = 0;
						} catch (IOException e) {e.printStackTrace();}
					}
				}
			}
			stopAfterEnd();
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
		if(run != null){
			run.interrupt();
			run = null;
		}
		line.stop();
		line.close();
		isPaused = false;
	}
	
	private void stopAfterEnd(){
		stop();
	}

	@Override
	public void foward(long msInterval) {
		throwIfNoResource();
		if(msInterval >= 0){
			setPosition(getPosition() + msInterval);
		}
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
		throwIfNoResource();
		long pos = line.getLongFramePosition();
		// length of stream in sample frames * actual position in micro-seconds / actual position in sample frames
		return (long)stream.getFrameLength() * line.getMicrosecondPosition()/(pos==0?1:pos);
	}

	@Override
	public void setPosition(long microSeconds) {
		throwIfNoResource();
		System.err.println(microSeconds + "  " + getPosition());
		
		if(microSeconds > getPosition()){
			long pos = line.getMicrosecondPosition();
			toSkip = microSeconds  //micro-seconds to skip
					* line.getLongFramePosition() / (pos==0?1:pos) //how many sample frame has one micro-second
					* format.getSampleSizeInBits() / (long) 8; // size of sample frame in bytes

		}else if(microSeconds < getPosition()){
			//TODO
			//*
			long msInterval = getPosition() - microSeconds;
			loopCount = 1;
			foward(getDuration() + msInterval);
			line.start();
			//*/
		}
	}

	@Override
	public void setLoop(int count) {
		throwIfNoResource();
		this.loopCount = count;
	}
	
	private void synchronize(){
		
	}

}
