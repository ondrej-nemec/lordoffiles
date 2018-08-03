package multimedia.sound;

import java.io.IOException;
import java.util.function.Function;

import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class BufferPlayer extends SoundPlayerApi<SourceDataLine>{

	private int bufferSize = 128000;
	
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
		if(!line.isOpen()){
			line.open(stream.getFormat());
		}
		line.start();
		int nBytesRead = 0;
		byte[] data = new byte[bufferSize];
		
		while(nBytesRead != -1 ){
			nBytesRead = stream.read(data, 0, data.length);
			if(nBytesRead >= 0){
				/*int nBytesWritten = */line.write(data, 0, nBytesRead);
			}
		}
	}

	@Override
	public void pause() {
		throwIfNoResource();
		line.stop();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
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
		return 0;
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
