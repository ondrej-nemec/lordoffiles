package multimedia.sound;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlaybackEndToEndTest {

	private final long DURATION = 185835102; // 185, 835 102 s
	
	public PlaybackEndToEndTest() {
		try(
				AudioInputStream stream = AudioInputStreamFactory
				.getStream(getClass().getResourceAsStream("/multimedia/sound/sound-input.wav"));
			) {
			Clip clip = DataLineFactory.getClip(stream);
			Playback play = new Playback(stream, clip);
			
			int max = 45;
			for (int i = 0; i < max; i++) {
				System.out.println("Time: " + i);
				switch(i) {
				case 2:
					play.play();
					print("play - music start");
					break;
				case 7:
					play.play();
					print("play - nothing happend");
					break;
				case 9:
					play.pause();
					print("pause");
					break;
				case 12:
					play.play();
					print("play - music start from previous position");
					break;
				case 16:
					play.stop();
					print("stop");
					break;
				case 20:
					play.play();
					print("play - music start from begin");
					break;
				case 25:
					play.foward(DURATION/2);
					print("foward " + DURATION/2);
					break;
				case 30:
					play.back(DURATION/3);
					print("back " + DURATION/3);
					break;
				case 33:
					play.back(DURATION);
					print("back, too back");
					break;
				case 38:
					play.foward(2*DURATION);
					print("foward, too foward");
					printClip(clip);
					break;
				case 40:
					play.play();
					print("play");
					printClip(clip);
					break;
				default: break;						
				}
				try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
			}
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	private void print(Object message) {
		System.out.println("\t" + message);
	}
	
	private void printClip(Clip clip) {
		print("clip active " + clip.isActive());
		print("clip open " + clip.isOpen());
		print("clip running " + clip.isRunning());
	}
	
	public static void main(String[] args) {
		new PlaybackEndToEndTest();
	}
}
