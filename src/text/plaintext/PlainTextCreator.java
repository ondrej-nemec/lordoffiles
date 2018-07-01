package text.plaintext;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import text.OutputTextBuffer;

public class PlainTextCreator extends OutputTextBuffer{

	public boolean write(final BufferedWriter bw, final String data) throws IOException{
		bw.write(data);
		bw.flush();
		return true;
	}
	
	public boolean write(final BufferedWriter bw, final List<String> data) throws IOException{
		for(int i=0; i<data.size();i++){
			bw.write(data.get(i));
		}
		bw.flush();
		return true;
	}
	
	public boolean write(final BufferedWriter bw, final List<List<String>> data, final String split) throws IOException{
		for(int i = 0;i<data.size();i++){
			if(data.get(i) != null){
				String line = data.get(i).get(0);
				for(int j = 1; j <data.get(i).size(); j++){
					line += split + data.get(i).get(j);
				}
				bw.write(line);
				bw.newLine();
			}
		}
		bw.flush();
		return true;
	}
}
