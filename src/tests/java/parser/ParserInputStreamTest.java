package parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class ParserInputStreamTest {
	
	@Test
	public void testNextWorks() throws IOException {		
		char t = 't';
		char f = 'f';
		
		InputStream is = mock(InputStream.class);
		InputFormat format = mock(InputFormat.class);
		
		when(format.parse(t)).thenReturn(true);
		when(format.parse(f)).thenReturn(false);
		
		ParserInputStream parser = new ParserInputStream(is, format);
		
		when(is.read()).thenReturn((int)t).thenReturn((int)t).thenReturn((int)f);		
		assertTrue(parser.next());
		
		verify(format, times(2)).parse(t);
		verify(format, times(1)).parse(f);
		
		when(is.read()).thenReturn(-1);
		assertFalse(parser.next());	
	}

}
