package text.plaintext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

public class PlainTextCreatorTest{
	private String writeString = "res/text/plaintext/write-string.txt";
	private String writeLines = "res/text/plaintext/write-lines.txt";
	private String writeGrid = "res/text/plaintext/write-grid.txt";
	
	@Test
	public void testWriteStringWorks() {
		PlainTextCreator creator = new PlainTextCreator();
		String data = "This is test data\nNew line\tafter tab";
		
		try(BufferedWriter bw = mockBuffer(writeString)){
			clearRepository(writeString);
			creator.write(bw, data);
			
			verify(bw, times(1)).write(data);
			verify(bw, times(1)).flush();
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		}
	}
	
	@Test
	public void testWriteLinesWorks() {
		PlainTextCreator creator = new PlainTextCreator();
		List<String> data = Arrays.asList("First line","Second line","Third line");
		
		try(BufferedWriter bw = mockBuffer(writeLines)){
			clearRepository(writeLines);
			creator.write(bw, data);
			
			for(String line : data){
				verify(bw).write(line);
			}
			verify(bw, times(2)).newLine();
			verify(bw, times(5)).write(Mockito.anyString());
			verify(bw, times(1)).flush();
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		}
	}
	
	@Test
	public void testWriteGridWorks() {
		PlainTextCreator creator = new PlainTextCreator();
		List<List<String>> data = Arrays.asList(
					Arrays.asList("a-a", "a-b", "a-c"),
					Arrays.asList("b-a", "b-b", "b-c"),
					Arrays.asList("c-a", "c-b", "c-c")
					);
		
		try(BufferedWriter bw = mockBuffer(writeGrid)){
			clearRepository(writeGrid);
			creator.write(bw, data, ";");
			
			for(List<String> line : data){
				verify(bw).write(line.get(0) + ";" + line.get(1) + ";" + line.get(2));
			}
			verify(bw, times(2)).newLine();
			verify(bw, times(5)).write(Mockito.anyString());
			verify(bw, times(1)).flush();
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		}
	}
	
	private BufferedWriter mockBuffer(String file) throws IOException{
		return mock(
				BufferedWriter.class,
				withSettings()
					.useConstructor(new FileWriter(file))
					.defaultAnswer(CALLS_REAL_METHODS)
			);
	}
	
	private void clearRepository(String path) throws IOException{
		File file = new File(path);
		if(file.exists())
			file.delete();
		file.createNewFile();
	}

}
