package text.plaintext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

import org.junit.Test;

public class PlainTextLoaderTest {
	
	private String stringFromConsumer = "";
	
	private String path = "src/tests/res/text/plaintext/";
	
	@Test
	public void testReadWithConsumerWorks() {
		try(BufferedReader br = mockBuilder(path + "read-consumer.txt")){
			PlainTextLoader loader = new PlainTextLoader();
			loader.read(br, (a)->{
				if(a.length() == 4)
					setTestString(a);
			});
			assertEquals("This is Java", stringFromConsumer);
			verify(br, times(7)).readLine();
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
		try(BufferedReader br = mockBuilder(path + "read-string.txt")){
			PlainTextLoader loader = new PlainTextLoader();
			
			assertEquals(
					"This is text of success reading of file.\nFile has two rows;",
					loader.readAsOneString(br)
				);
			verify(br, times(3)).readLine();
		} catch (Exception e){
			e.printStackTrace();
			fail("IOException");
		}
	}
	
	@Test
	public void testReadLinesWorks() {
		try(BufferedReader br = mockBuilder(path + "read-lines.txt")){
			PlainTextLoader loader = new PlainTextLoader();
			
			assertEquals(
					Arrays.asList("First line", "Second line", "Third line"),
					loader.read(br)
				);
			verify(br, times(4)).readLine();
		} catch (Exception e){
			e.printStackTrace();
			fail("IOException");
		}
	}
	
	@Test
	public void testReadGridWorks() {
		try(BufferedReader br = mockBuilder(path + "read-grid.txt")){
			PlainTextLoader loader = new PlainTextLoader();
			
			assertEquals(
					Arrays.asList(
							Arrays.asList("key", "message"),
							Arrays.asList("second", "mess"),
							Arrays.asList("grid", "here")
							),
					loader.read(br, "-")
				);
			verify(br, times(4)).readLine();
		} catch (Exception e){
			e.printStackTrace();
			fail("IOException");
		}
	}
	
	private BufferedReader mockBuilder(String file) throws FileNotFoundException{
		return mock(
				BufferedReader.class,
				withSettings()
					.useConstructor(new FileReader(file))
					.defaultAnswer(CALLS_REAL_METHODS)
			);
	}
}
