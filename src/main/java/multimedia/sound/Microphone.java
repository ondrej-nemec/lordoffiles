package multimedia.sound;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.function.Consumer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Microphone {
	protected interface Writer {		
		void write(byte[] data, int off, int len) throws LineUnavailableException, IOException;		
	}

	public boolean capture = true;
	
	private int bufferSize;
	
	public void capture(TargetDataLine line, AudioFormat format, Consumer<byte[]> consumer) throws LineUnavailableException {
		try { capture(line, format, (data, off, len)->{consumer.accept(data);});
		} catch (IOException ignored) {} // this couldn´t throw
	}
	
	public void capture(TargetDataLine line, AudioFormat format, ByteArrayOutputStream stream) throws LineUnavailableException {
		try { capture(line, format, (data, off, len)->{stream.write(data, off, len);});
		} catch (IOException ignored) {} // this couldn´t throw
	}
	
	public void capture(TargetDataLine line, AudioFormat format, PipedOutputStream pipe) throws LineUnavailableException, IOException {
		capture(line, format, (data, off, len)->{pipe.write(data, off, len);});
	}
	
	public int getBufferSize() {
		return bufferSize;
	}
	
	protected void capture(TargetDataLine line, AudioFormat format, Writer writer)  throws LineUnavailableException, IOException {
		line.open(format);
	    
	    int numBytesRead = 0;
	    byte[] data = new byte[line.getBufferSize() / 5];
	    
	    line.start();
	    while (numBytesRead != -1 && capture) {
		   numBytesRead =  line.read(data, 0, data.length);
		   if (numBytesRead >= 0)
			   writer.write(data, 0, numBytesRead);
	    }
	    line.stop();
	    line.drain();
	    line.close();
	}
}
