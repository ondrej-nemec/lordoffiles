package text.plaintext;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class PlainTextLoader {

	private final BufferedReader br;
	
	public PlainTextLoader(final BufferedReader br) {
		this.br = br;
	}
	
	public void read(final Consumer<String> consumer) throws IOException {
		String line = br.readLine();
		while(line != null){
			consumer.accept(line);
			line = br.readLine();
		}
	}

	public String readAsOneString() throws IOException {
		String result = "";
		String line = br.readLine();
		if(line != null){
			result+= line;
			line = br.readLine();
		}
		while(line!=null){
			result+= "\n" + line;
			line = br.readLine();
		}
		return result;
	}
	
	public List<String> read() throws IOException {
		List<String> result = new ArrayList<>();
		String line = br.readLine();
		while(line!=null){
			result.add(line);
			line = br.readLine();
		}
		return result;
	}
	
	public Collection<List<String>> read(final String split) throws IOException {
		List<List<String>> result = new ArrayList<>();
		String line = br.readLine();
		while(line != null){
			String[] cels = line.split(split);
			List<String> row = new ArrayList<>();
			for(int i = 0; i < cels.length; i++){
				row.add(cels[i]);
			}
			result.add(row);
			line = br.readLine();
		}
		return result;
	}
}
