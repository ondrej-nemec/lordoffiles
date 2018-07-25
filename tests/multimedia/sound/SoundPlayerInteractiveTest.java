package multimedia.sound;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayerInteractiveTest {

	private String path = "/multimedia/sound/sound.wav";
	int time = 35;
	
	public SoundPlayerInteractiveTest() {
		SoundPlayer s = new SoundPlayer();
		System.err.println("This is sound test of SoundPlayer class, duration of this test is: " + time);
		for(int i = 0; i < time; i++){
			switch (i) {
			case 1:
				try {
					s.setStream(getClass().getResourceAsStream(path), false);
					System.out.println("Stream setted");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {e.printStackTrace();}
				break;
			case 3:
				System.out.println("stop - nothing happend");
				try {
					s.stop();
				} catch (IOException e2) {e2.printStackTrace();}
				break;
			case 6:
				System.out.println("play - music start");
				try {
					s.play();
				} catch (LineUnavailableException | IOException e1) {e1.printStackTrace();}
				break;
			case 12:
				System.out.println("pause - music paused");
				try {
					s.pause();
				} catch (IOException e3) {e3.printStackTrace();}
				break;
			case 17:
				System.out.println("play - music continues");
				try {
					s.play();
				} catch (LineUnavailableException | IOException e1) {e1.printStackTrace();}
				break;
			case 23:
				System.out.println("stop - music stop");
				try {
					s.stop();
				} catch (IOException e2) {e2.printStackTrace();}
				break;
			case 27:
				System.out.println("play - music play from start");
				try {
					s.play();
				} catch (LineUnavailableException | IOException e1) {e1.printStackTrace();}
				break;
			//TODO continue with positions and loop
			
			default:
				break;
			}
			if(time + 1 == i)
				System.out.println("End");
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public static void main(String[] args) {
		new SoundPlayerInteractiveTest();
	}
}
