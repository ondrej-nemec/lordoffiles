package multimedia.sound;

import java.io.IOException;

import javax.sound.sampled.Control;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import exceptions.NoSourceWasGivenException;

public class APlayer {
	
	private String path = "/multimedia/sound/sound.wav";

	private long duration = 185835102; // 185, 835 102 s
	
	public APlayer() {
		SoundPlayerApi<?> p;
		/*
		p = new MemoryPlayer();
		/*/
		p = new BufferPlayer();
		//*/
		player(p);
	}
	
	private void player(SoundPlayerApi<?> p){
		
		int max = 55;
		for(int i = 0; i < max; i++){
			System.out.println("Time: " + i + "; threads: " + Thread.activeCount() + " ----- status: " + status(p.getStatus()));
			switch (i) {
			case 0:
				try {
					p.play();
				} catch (LineUnavailableException | IOException e1) {e1.printStackTrace();
				} catch (NoSourceWasGivenException e) {action("play - exception");}
				break;
			case 1:
				try {
					p.setResource(getClass().getResourceAsStream(path));
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {e1.printStackTrace();}
				action("stream");
				break;
			case 2:
				//info(p);
				break;
			case 4:
				try {
					p.play();
				} catch (LineUnavailableException | IOException e1) {e1.printStackTrace();}
				action("play");
				break;
			case 5:
				//info(p);
				break;
			case 6:
				try {
					p.play();
				} catch (LineUnavailableException | IOException e1) {e1.printStackTrace();}
				action("play");
				break;
			case 9:
				p.pause();
				action("pause");
				break;
			case 11:
				p.pause();
				action("pause");
				break;
			case 14:	
				try {
					p.play();
				} catch (LineUnavailableException | IOException e1) {e1.printStackTrace();}
				action("play");
				break;
			/************/
			case 18:
				p.stop();
				action("stop");
				break;
			case 20:
				p.pause();
				action("pause");
				break;
			case 23:	
				try {
					p.play();
				} catch (LineUnavailableException | IOException e1) {e1.printStackTrace();}
				action("play");
				break;
			/***********/
			case 26:
				p.foward(2*duration/3);
				action("foward");
				break;
			case 30:
				try {
					p.back(duration/3);
				} catch (LineUnavailableException | IOException e1) {e1.printStackTrace();}
				action("back");
				break;	
			case 33:
				p.foward(2*duration);
				action("foward - more time: " + p.getPosition() );
				break;
			case 36:
				try {
					p.back(3*duration);
				} catch (LineUnavailableException | IOException e1) {e1.printStackTrace();}
				action("back - less time: " + p.getPosition() );
				break;		
			/***********/
			case 40:
				p.setLoop(1);
				p.foward(duration - p.getPosition() - 10000);
				action("loop setted - fowart nearly to end");
				break;
			default:
				break;
			}
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		}
		info(p);
		p.stop();
		System.out.println("Time: " + max + "; threads: " + Thread.activeCount());
		System.out.println("END");
		
	}


	private String status(int status) {
		switch (status) {
		case 0:
			return "NO_SOURCE";
		case 1:
			return "STOPPED";
		case 2:
			return "PLAYED";
		case 3:
			return "PAUSED";
		case 4:
			return "ENDED";
		default:
			return "--";
		}
	}

	private void info(SoundPlayerApi<?> p) {
		System.out.println("CONTROL");
		for(Control c : p.getDataLine().getControls()){
			System.out.println(c);
		}
		System.out.println("INFO");
		System.out.println(p.getDataLine().getLineInfo());
		System.out.println("FORMAT");
		System.out.println(p.getDataLine().getFormat());
	}

	private void action(String action){
		System.out.println("\t " + action);
	}
	
	
	/*****************/
	public static void main(String[] args) {
		new APlayer();
	}
}
