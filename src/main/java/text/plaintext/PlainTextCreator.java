package text.plaintext;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class PlainTextCreator {

	private final BufferedWriter bw;
	
	public PlainTextCreator(final BufferedWriter bw) {
		this.bw = bw;
	}
	
	public boolean write(final String data) throws IOException {
		bw.write(data);
		bw.newLine();
		bw.flush();
		return true;
	}
	
	public boolean write(final List<String> data) throws IOException {
		for(int i=0; i<data.size();i++){
			bw.write(data.get(i));
			bw.newLine();
		}
		bw.flush();
		return true;
	}
	
	public boolean write(final List<List<String>> data, final String split) throws IOException {
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
