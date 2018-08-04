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
					nBytesRead = stream.read(data, 0, data.length);
				} catch (IOException e) {e.printStackTrace();}
				
				if(nBytesRead >= 0){
					nBytesWritten = line.write(data, 0, nBytesRead);
				}
				
				}
			}
		}
	}
	
	private int nBytesWritten = 0;

	private boolean isPaused;
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
	/*	paused = false;
		if(!line.isOpen()){
			if(stream.markSupported())
				stream.reset();
			
			line.open(stream.getFormat());
			line.start();
			
			run = new Play();
			run.start();
			
		}else{
			line.start();
		}
		*/
		/*
		if(paused){
			paused = false;
			run.start();
		}else{
			if(stream.markSupported())
				stream.reset();
			
			line.open(stream.getFormat());
			line.start();
			
			run = new Play();
			run.start();
		}
		*/
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
		//nothing do
		
		
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void back(long msInterval) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPosition(long microSeconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLoop(int count) {
		// TODO Auto-generated method stub
		
	}

}
