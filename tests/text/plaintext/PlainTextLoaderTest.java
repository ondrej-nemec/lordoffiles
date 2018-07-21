package text.plaintext;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.junit.Test;

public class PlainTextLoaderTest {
	private String stringFromConsumer = "";
	
	@Test
	public void testReadWithConsumerWorks() {
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(
						getClass().getResourceAsStream("/text/plaintext/read-consumer.txt")))){
			PlainTextLoader loader = new PlainTextLoader();
			loader.read(br, (a)->{
				if(a.length() == 4)
					setTestString(a);
			});
			assertEquals("This is Java", stringFromConsumer);
		} catch (Exception e){
			e.printStackTrace();
			fail("IOException");
		}
	}
	
	private void setTestString(String a){
		stringFromConsumer +=a;
	}
	
	@Test
	public void testReadAsOneStringWorks() {
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(
						getClass().getResourceAsStream("/text/plaintext/read-string.txt")))){
			PlainTextLoader loader = new PlainTextLoader();
			
			assertEquals(
					"This is text of success reading of file.\nFile has two rows;",
					loader.readAsOneString(br)
				);
		} catch (Exception e){
			e.printStackTrace();
			fail("IOException");
		}
	}
	
	@Test
	public void testReadLinesWorks() {
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(
						getClass().getResourceAsStream("/text/plaintext/read-lines.txt")))){
			PlainTextLoader loader = new PlainTextLoader();
			
			assertEquals(
					Arrays.asList("First line", "Second line", "Third line"),
					loader.read(br)
				);
		} catch (Exception e){
			e.printStackTrace();
			fail("IOException");
		}
	}
	
	@Test
	public void testReadGridWorks() {
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(
						getClass().getResourceAsStream("/text/plaintext/read-grid.txt")))){
			PlainTextLoader loader = new PlainTextLoader();
			
			assertEquals(
					Arrays.asList(
							Arrays.asList("key", "message"),
							Arrays.asList("second", "mess"),
							Arrays.asList("grid", "here")
							),
					loader.read(br, "-")
				);
		} catch (Exception e){
			e.printStackTrace();
			fail("IOException");
		}
	}
}
