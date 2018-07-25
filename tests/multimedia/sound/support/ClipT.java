package multimedia.sound.support;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.Control.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;

public class ClipT implements Clip{

	private Clip clip;
	
	public ClipT(Clip clip) {
		this.clip = clip;
	}
	
	@Override
	public int available() {
		return clip.available();
	}

	@Override
	public void drain() {
		clip.drain();
	}

	@Override
	public void flush() {
		clip.flush();
	}

	@Override
	public int getBufferSize() {
		return clip.getBufferSize();
	}

	@Override
	public AudioFormat getFormat() {
		return clip.getFormat();
	}

	@Override
	public int getFramePosition() {
		return clip.getFramePosition();
	}

	@Override
	public float getLevel() {
		return clip.getLevel();
	}

	@Override
	public long getLongFramePosition() {
		return clip.getLongFramePosition();
	}

	@Override
	public long getMicrosecondPosition() {
		return clip.getMicrosecondPosition();
	}

	@Override
	public boolean isActive() {
		return clip.isActive();
	}

	@Override
	public boolean isRunning() {
		return clip.isRunning();
	}

	@Override
	public void start() {
		clip.start();
	}

	@Override
	public void stop() {
		clip.stop();
	}

	@Override
	public void addLineListener(LineListener arg0) {
		clip.addLineListener(arg0);
	}

	@Override
	public void close() {
		clip.close();
	}

	@Override
	public Control getControl(Type arg0) {
		return clip.getControl(arg0);
	}

	@Override
	public Control[] getControls() {
		return clip.getControls();
	}

	@Override
	public javax.sound.sampled.Line.Info getLineInfo() {
		return clip.getLineInfo();
	}

	@Override
	public boolean isControlSupported(Type arg0) {
		return clip.isControlSupported(arg0);
	}

	@Override
	public boolean isOpen() {
		return clip.isOpen();
	}

	@Override
	public void open() throws LineUnavailableException {
		clip.open();
	}

	@Override
	public void removeLineListener(LineListener arg0) {
		clip.removeLineListener(arg0);
	}

	@Override
	public int getFrameLength() {
		return clip.getFrameLength();
	}

	@Override
	public long getMicrosecondLength() {
		return clip.getMicrosecondLength();
	}

	@Override
	public void loop(int arg0) {
		clip.loop(arg0);
	}

	@Override
	public void open(AudioInputStream arg0) throws LineUnavailableException, IOException {
		clip.open(arg0);
	}

	@Override
	public void open(AudioFormat arg0, byte[] arg1, int arg2, int arg3) throws LineUnavailableException {
		clip.open();
	}

	@Override
	public void setFramePosition(int arg0) {
		clip.setFramePosition(arg0);
	}

	@Override
	public void setLoopPoints(int arg0, int arg1) {
		clip.setLoopPoints(arg0, arg1);
	}

	@Override
	public void setMicrosecondPosition(long arg0) {
		clip.setMicrosecondPosition(arg0);
	}

}
