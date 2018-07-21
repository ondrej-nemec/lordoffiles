package multimedia.sound;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public class SoundRecorder {

	public void recording(){
		/*
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);  //(22000, 16, 2, true, true)
		
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if(!AudioSystem.isLineSupported(info)){
			//error
		}
		try{
			TargetDataLine line = AudioSystem.getTargetDataLine(format);
		//	line = (TargetDataLine)AudioSystem.getLine(info);
			line.open(format);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*/
		AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);  //(22000, 16, 2, true, true)
		
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if(!AudioSystem.isLineSupported(info)){
			//error
		}
		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		for(Mixer.Info mInfo : mixInfos){
			Mixer m = AudioSystem.getMixer(mInfo);
			Line.Info[] linInfos = m.getSourceLineInfo();
			for(Line.Info lInfo : linInfos){
				System.out.println(mInfo.getName() + " " + linInfos);
				Line lin = null;
				try {  lin = m.getLine(lInfo);
				} catch (LineUnavailableException e) {e.printStackTrace();}
				System.out.println("\t " + lin);
			}
			linInfos = m.getTargetLineInfo();
			for(Line.Info lInfo : linInfos){
				System.out.println(mInfo.getName() + " " + linInfos);
				Line lin = null;
				try {  lin = m.getLine(lInfo);
				} catch (LineUnavailableException e) {e.printStackTrace();}
				System.out.println("\t " + lin);
			}
			
		}
		try{
			TargetDataLine line = AudioSystem.getTargetDataLine(format);
		//	line = (TargetDataLine)AudioSystem.getLine(info);
			line.open(format);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		
		//*/
		
	}
	
	public int save(AudioInputStream ais, AudioFileFormat.Type type, File file) throws IOException{
		return AudioSystem.write(ais, type, file);
	}
	
	public int save(AudioInputStream ais, AudioFileFormat.Type type, OutputStream os) throws IOException{
		return AudioSystem.write(ais, type, os);
	}
	
}
