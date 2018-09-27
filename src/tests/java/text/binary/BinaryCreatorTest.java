package text.binary;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class BinaryCreatorTest {

	@Test
	public void testWriteWorks() {
		try(OutputStream os = mock(OutputStream.class)) {
			BinaryCreator c = new BinaryCreator();
			byte[] data = new byte[] {72, 101, 108, 108, 111, 32, 119, 111, 114, 108, 100, 33};
			
			c.write(os, data);
			
			verify(os).write(data);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
}
