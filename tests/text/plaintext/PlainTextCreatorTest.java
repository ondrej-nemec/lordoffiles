package text.plaintext;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PlainTextCreatorTest {
	private String writeString = "tests/text/plaintext/write-string.txt";
	private String writeLines = "tests/text/plaintext/write-lines.txt";
	private String writeGrid = "tests/text/plaintext/write-grid.txt";
	
	@Test
	public void testWriteStringWorks() {
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(writeString))){
			clearRepository(writeString);
			
			PlainTextCreator creator = new PlainTextCreator();
			String data = "This is test data\nNew line\tafter tab";
			creator.write(bw, data);
			
			System.err.println("This file needs check: " + writeString);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		}
	}
	
	@Test
	public void testWriteLinesWorks() {
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(writeLines))){
			clearRepository(writeLines);
			
			PlainTextCreator creator = new PlainTextCreator();
			List<String> data = Arrays.asList("First line","Second line","Third line");
			creator.write(bw, data);
			
			System.err.println("This file needs check: " + writeLines);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		}
	}
	
	@Test
	public void testWriteGridWorks() {
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(writeGrid))){
			clearRepository(writeGrid);
			
			PlainTextCreator creator = new PlainTextCreator();
			List<List<String>> data = Arrays.asList(
					Arrays.asList("a-a", "a-b", "a-c"),
					Arrays.asList("b-a", "b-b", "b-c"),
					Arrays.asList("c-a", "c-b", "c-c")
					);
			creator.write(bw, data, ";");
			
			System.err.println("This file need checks: " + writeGrid);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		}
	}
	
	private void clearRepository(String path) throws IOException{
		File file = new File(path);
		if(file.exists())
			file.delete();
		file.createNewFile();
	}

}
