package text.binary;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.function.Consumer;

import org.junit.Test;

public class BinaryLoaderTest {

	private String file = "src/tests/res/text/binary/input.txt";
	
	private int index = 0;
	
	private byte[] result = new byte[4];
	
	@Test
	public void testReadWithConsumer() {
		try(InputStream is = new FileInputStream(file)) {
			BinaryLoader l = new BinaryLoader();
			Consumer<byte[]> consumer = (a)->{
				add(a[0]);
			};
			
			l.read(is, consumer, 3);
				
			assertArrayEquals(
					new byte[] {72, 108, 119, 108}, 
					result
				);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	private void add(byte b) {
		result[index++] = b;
	}
	
	@Test
	public void testReadWholeContent() {
		try(InputStream is = new FileInputStream(file)) {
			BinaryLoader l = new BinaryLoader();
			
			assertArrayEquals(
					new byte[] {72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33},
					l.read(is)
				);			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
