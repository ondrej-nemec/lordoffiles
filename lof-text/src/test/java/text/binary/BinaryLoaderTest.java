package text.binary;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

import org.junit.Test;

public class BinaryLoaderTest {

	private String file = "/text/binary/input.txt";
	
	private int index = 0;
	
	private byte[] result = new byte[4];
	
	@Test
	public void testReadWithConsumer() throws IOException {
		try(InputStream is = getClass().getResourceAsStream(file)) {
			BinaryLoader l = new BinaryLoader(is);
			Consumer<byte[]> consumer = (a)->{
				add(a[0]);
			};
			
			l.read(consumer, 3);
				
			assertArrayEquals(
					new byte[] {72, 108, 119, 108}, 
					result
				);
		}		
	}
	
	private void add(byte b) {
		result[index++] = b;
	}
	
	@Test
	public void testReadWholeContent() throws IOException {
		try(InputStream is = getClass().getResourceAsStream(file)) {
			BinaryLoader l = new BinaryLoader(is);
			
			assertArrayEquals(
					new byte[] {72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33},
					l.read()
				);			
		}
	}
}
