package text.plaintext;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import text.BufferedReaderFactory;

public class PlainTextLoaderTest {
	
	private String stringFromConsumer = "";
	
	private String path = "/text/plaintext/";
	
	@Test
	public void testReadWithConsumerWorks() throws IOException {
		try(BufferedReader br = BufferedReaderFactory.buffer(getClass().getResourceAsStream(path + "read-consumer.txt"))){
			PlainTextLoader loader = new PlainTextLoader(br);
			loader.read((a)->{
				if(a.length() == 4)
					setTestString(a);
			});
			assertEquals("This is Java", stringFromConsumer);
		}
	}
	
	private void setTestString(String a){
		stringFromConsumer +=a;
	}
	
	@Test
	public void testReadAsOneStringWorks() throws IOException {
		try(BufferedReader br = BufferedReaderFactory.buffer(getClass().getResourceAsStream(path + "read-string.txt"))){
			PlainTextLoader loader = new PlainTextLoader(br);			
			assertEquals(
					"This is text of success reading of file.\nFile has two rows;",
					loader.readAsOneString()
				);
		}
	}
	
	@Test
	public void testReadLinesWorks() throws IOException {
		try(BufferedReader br = BufferedReaderFactory.buffer(getClass().getResourceAsStream(path + "read-lines.txt"))){
			PlainTextLoader loader = new PlainTextLoader(br);			
			assertEquals(
					Arrays.asList("First line", "Second line", "Third line"),
					loader.read()
				);
		}
	}
	
	@Test
	public void testReadGridWorks() throws IOException {
		try(BufferedReader br = BufferedReaderFactory.buffer(getClass().getResourceAsStream(path + "read-grid.txt"))){
			PlainTextLoader loader = new PlainTextLoader(br);			
			assertEquals(
					Arrays.asList(
							Arrays.asList("key", "message"),
							Arrays.asList("second", "mess"),
							Arrays.asList("grid", "here")
							),
					loader.read("-")
				);
		}
	}
}
