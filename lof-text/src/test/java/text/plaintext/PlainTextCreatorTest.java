package text.plaintext;

import static org.mockito.Mockito.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PlainTextCreatorTest{
	//TODO add some end-to-end tests?
	
	@Test
	public void testWriteStringWorks() throws IOException {
		String data = "This is test data\nNew line\tafter tab";
		
		try(BufferedWriter bw = mock(BufferedWriter.class)){
		PlainTextCreator creator = new PlainTextCreator(bw);
			creator.write(data);
			
			verify(bw, times(1)).write(data);
			verify(bw, times(1)).newLine();
			verify(bw, times(1)).flush();
			verifyNoMoreInteractions(bw);
		}
	}
	
	@Test
	public void testWriteLinesWorks() throws IOException {
		List<String> data = Arrays.asList("First line","Second line","Third line");
		
		try(BufferedWriter bw = mock(BufferedWriter.class)){
			PlainTextCreator creator = new PlainTextCreator(bw);
			creator.write(data);
			
			for(String line : data){
				verify(bw).write(line);
			}
			verify(bw, times(3)).newLine();
			verify(bw, times(1)).flush();
			verifyNoMoreInteractions(bw);
		}
	}
	
	@Test
	public void testWriteGridWorks() throws IOException {
		List<List<String>> data = Arrays.asList(
					Arrays.asList("a-a", "a-b", "a-c"),
					Arrays.asList("b-a", "b-b", "b-c"),
					Arrays.asList("c-a", "c-b", "c-c")
					);
		
		try(BufferedWriter bw = mock(BufferedWriter.class)){
			PlainTextCreator creator = new PlainTextCreator(bw);
			creator.write(data, ";");
			
			for(List<String> line : data){
				verify(bw).write(line.get(0) + ";" + line.get(1) + ";" + line.get(2));
			}
			verify(bw, times(3)).newLine();
			verify(bw, times(1)).flush();
			verifyNoMoreInteractions(bw);
		}
	}
}
