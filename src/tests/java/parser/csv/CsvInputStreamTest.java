package parser.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import exceptions.ParserSyntaxException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import parser.csv.CsvInputStream;

@RunWith(JUnitParamsRunner.class)
public class CsvInputStreamTest {
	
	@Test
	public void testNextWorks() throws IOException {		
		char t = 't';
		char f = 'f';
		
		InputStream is = mock(InputStream.class);
		
		CsvInputStream parser = spy(new CsvInputStream(is));
		
		when(parser.parse(t)).thenReturn(true);
		when(parser.parse(f)).thenReturn(false);
		
		when(is.read()).thenReturn((int)t).thenReturn((int)t).thenReturn((int)f);		
		assertTrue(parser.next());
		
		verify(parser, times(2)).parse(t);
		verify(parser, times(1)).parse(f);
		
		when(is.read()).thenReturn(-1);
		assertFalse(parser.next());	
	}
	
	@Test
	public void testParseGrid() {
		CsvInputStream f = new CsvInputStream(mock(InputStream.class));
		//just text
		assertEquals(true, f.parse('t'));
		assertEquals("t", f.getValue());
		assertEquals(0, f.getLine());
		
		// first row
		assertEquals(false, f.parse(','));
		assertEquals("t", f.getValue());
		assertEquals(0, f.getLine());
		
		// second text
		assertEquals(true, f.parse('s'));
		assertEquals(false, f.parse(','));
		assertEquals("s", f.getValue());
		assertEquals(0, f.getLine());
		
		//new line
		assertEquals(true, f.parse('n'));
		assertEquals(false, f.parse('\n'));
		assertEquals("n", f.getValue());
		assertEquals(0, f.getLine());
		
		// after new line
		assertEquals(true, f.parse('a'));
		assertEquals(false, f.parse(','));		
		assertEquals("a", f.getValue());
		assertEquals(1, f.getLine());
	}
	
	@Test(expected=ParserSyntaxException.class)
	public void testParseThrowsWhenQuotesNotEnded() {
		CsvInputStream f = new CsvInputStream(mock(InputStream.class));

		//double quots in text
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		f.parse(',');
	}
	
	@Test(expected=ParserSyntaxException.class)
	public void testParseThrowsWhenQuotesEndButNotStart() {
		CsvInputStream f = new CsvInputStream(mock(InputStream.class));

		//double quots in text
		assertEquals(true, f.parse('a'));
		assertEquals(true, f.parse('"'));
		f.parse(',');
	}
	
	@Test
	public void endToEndTest() {
		try (InputStream is = new FileInputStream("src/tests/res/parser/csv-input.csv")) {
			CsvInputStream parser = new CsvInputStream(is);
			
			int i = 1;
			
			while (parser.next()) {
				switch(i) {
				case 1:
					assertEquals("simple text", parser.getValue());
					assertEquals(0, parser.getLine());
					break;
				case 2:
					assertEquals("\"text in quotes\"", parser.getValue());
					assertEquals(0, parser.getLine());
					break;
				case 3:
					assertEquals("text,with,colons", parser.getValue());
					assertEquals(0, parser.getLine());
					break;
				case 4:
					assertEquals("\\\"", parser.getValue());
					assertEquals(1, parser.getLine());
					break;
				case 5:
					assertEquals(" \"quotes\"", parser.getValue());
					assertEquals(1, parser.getLine());
					break;
				case 6:
					assertEquals("new\r\nline", parser.getValue());
					assertEquals(1, parser.getLine());
					break;
				case 7:
					assertEquals("", parser.getValue());
					assertEquals(1, parser.getLine());
					break;
				case 8:
					assertEquals("10000", parser.getValue());
					assertEquals(1, parser.getLine());
					break;
				case 9:
					assertEquals("'single", parser.getValue());
					assertEquals(2, parser.getLine());
					break;
				case 10:
					assertEquals(" quots'", parser.getValue());
					assertEquals(2, parser.getLine());
					break;
				default:
					throw new RuntimeException("More that " + i + " elements");
				}
				i++;
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	@Parameters
	public void testParseWorks(char[] toParse, boolean[] expectedReturn, String value, int line) {
		CsvInputStream f = new CsvInputStream(mock(InputStream.class));
		
		if (toParse.length != expectedReturn.length)
			fail();
		
		for (int i = 0; i < toParse.length; i++) {
			assertEquals(expectedReturn[i], f.parse(toParse[i]));
		}
		
		assertEquals(value, f.getValue());
		assertEquals(line, f.getLine());
	}
	
	public Collection<List<Object>> parametersForTestParseWorks() {
		return Arrays.asList(
				Arrays.asList(
						//colon in quots
						new char[] {'"', ',', '"', ','},
						new boolean[] {true, true, true, false},
						",",
						0
				),
				Arrays.asList(
						//new line windows
						new char[] {'n', '\r', '\n'},
						new boolean[] {true, true, false},
						"n",
						0
				),
				Arrays.asList(
						//double quots in text
						new char[] {'"', '"', 'a', '"', '"', ','},
						new boolean[] {true, true, true, true, true, false},
						"\"a\"",
						0
				),
				Arrays.asList(
						// ignoring \
						new char[] {'\\', ','},
						new boolean[] {true, false},
						"\\",
						0
				),
				Arrays.asList(
						// \n in quots
						new char[] {'"', 'a', '\n', 'b', '"', ','},
						new boolean[] {true, true, true, true, true, false},
						"a\nb",
						0
				),
				Arrays.asList(
						//not ignore \r in quotes
						new char[] {'"', '\r', '"', ','},
						new boolean[] {true, true, true, false},
						"\r",
						0
				),
				Arrays.asList(
						//more quots
						new char[] {'"', '"', '"', '"', '"', '"', ','},
						new boolean[] {true, true, true, true, true, true, false},
						"\"\"\"",
						0
				)
		);
	}

}
