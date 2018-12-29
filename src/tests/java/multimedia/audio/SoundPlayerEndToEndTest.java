package multimedia.sound;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayerEndToEndTest {

	public SoundPlayerEndToEndTest() {
		try(
				AudioInputStream stream = AudioInputStreamFactory
				.getStream(getClass().getResourceAsStream("/multimedia/sound/sound-input.wav"));
			) {
			SourceDataLine line = DataLineFactory.getSourceLine(stream);
			SoundPlayer play = new SoundPlayer(stream, line);
			
			runInThread(play);
			
			int max = 20;
			for (int i = 0; i < max; i++) {
				System.out.println("Time: " + i);
				switch(i) {
				case 0: print("Music start"); break;
				case 7: 
					play.ACTION = PlayingAction.PAUSE;
					print("Pause"); break;
				case 11: 
					play.ACTION =  PlayingAction.PLAY;
					print("Play"); break;
				case 15: 
					play.ACTION =  PlayingAction.STOP;
					print("Stop"); break;
				case 18: 
					play.ACTION =  PlayingAction.PLAY;
					print("Play - nothing happend, playing was stopped"); break;
				default: break;						
				}
				try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
			}
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	private void runInThread(SoundPlayer player) {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.submit(()->{
			try {
				player.play();
			} catch (IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
		});
	}
	
	private void print(Object message) {
		System.out.println("\t" + message);
	}
	public static void main(String[] args) {
		new SoundPlayerEndToEndTest();
	}
}
