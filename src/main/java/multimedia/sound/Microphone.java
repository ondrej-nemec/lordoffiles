package multimedia.sound;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.function.Consumer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Microphone {

	public boolean capture = true;
	
	private int bufferSize;
	
	public void capture(TargetDataLine line, AudioFormat format, Consumer<byte[]> consumer) throws LineUnavailableException {
		line.open(format);
	    
	    int numBytesRead = 0;
	    byte[] data = new byte[line.getBufferSize() / 5];
	    
	    line.start();
	    while (numBytesRead != -1 && capture) {
		   numBytesRead =  line.read(data, 0, data.length);
		   if (numBytesRead >= 0)
			   consumer.accept(data);
	    }
	    line.stop();
	    line.drain();
	    line.close();
	}
	
	public void capture(TargetDataLine line, AudioFormat format, ByteArrayOutputStream stream) throws LineUnavailableException {
		line.open(format);
	    
	    int numBytesRead = 0;
	    byte[] data = new byte[line.getBufferSize() / 5];
	    
	    line.start();
	    while (numBytesRead != -1 && capture) {
		   numBytesRead =  line.read(data, 0, data.length);
		   if (numBytesRead >= 0)
			   stream.write(data, 0, numBytesRead);
	    }
	    line.stop();
	    line.drain();
	    line.close();
	}
	
	public void capture(TargetDataLine line, AudioFormat format, PipedOutputStream pipe) throws LineUnavailableException, IOException {
		line.open(format);
	    
	    int numBytesRead = 0;
	    byte[] data = new byte[line.getBufferSize() / 5];
	    
	    line.start();
	    while (numBytesRead != -1 && capture) {
		   numBytesRead =  line.read(data, 0, data.length);
		   if (numBytesRead >= 0)
			   pipe.write(data, 0, numBytesRead);
	    }
	    line.stop();
	    line.drain();
	    line.close();
	}
	
	public int getBufferSize() {
		return bufferSize;
	}
}
