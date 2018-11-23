package parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

import exceptions.ParserSyntaxException;

public class CsvInputFormatTest {
	
	@Test
	public void testParseGrid() {
		CsvInputFormat f = new CsvInputFormat();
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
	
	@Test
	public void testParseColonInQuotes() {
		CsvInputFormat f = new CsvInputFormat();
		
		//colon in quots
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse(','));
		assertEquals(true, f.parse('"'));
		assertEquals(false, f.parse(','));
		assertEquals(",", f.getValue());
		assertEquals(0, f.getLine());
	}

	
	@Test
	public void testParseNewLineWindows() {
		CsvInputFormat f = new CsvInputFormat();

		//new line windows
		assertEquals(true, f.parse('n'));
		assertEquals(true, f.parse('\r'));
		assertEquals(false, f.parse('\n'));
		assertEquals("n", f.getValue());
		assertEquals(0, f.getLine());
	
	}
	
	@Test
	public void testParseQuotsInText() {
		CsvInputFormat f = new CsvInputFormat();

		//double quots in text
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('a'));
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		assertEquals(false, f.parse(','));
		assertEquals("\"a\"", f.getValue());
		assertEquals(0, f.getLine());
	}
	
	@Test
	public void testParseIgnorigBackslash() {
		CsvInputFormat f = new CsvInputFormat();
		
		// ignoring \
		assertEquals(true, f.parse('\\'));
		assertEquals(false, f.parse(','));
		assertEquals("\\", f.getValue());
		assertEquals(0, f.getLine());
	}
	
	@Test
	public void testParseNewLineInQuots() {
		CsvInputFormat f = new CsvInputFormat();
		
		// \n in quots
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('a'));
		assertEquals(true, f.parse('\n'));
		assertEquals(true, f.parse('b'));
		assertEquals(true, f.parse('"'));
		assertEquals(false, f.parse(','));
		assertEquals("a\nb", f.getValue());
		assertEquals(0, f.getLine());

	}
	
	@Test
	public void testParseNotIgnoteRInQuotes() {
		CsvInputFormat f = new CsvInputFormat();
		
		//not ignore \r in quotes
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('\r'));
		assertEquals(true, f.parse('"'));
		assertEquals(false, f.parse(','));
		assertEquals("\r", f.getValue());
		assertEquals(0, f.getLine());
	}
	
	@Test
	public void testParseMoreQuots() {
		CsvInputFormat f = new CsvInputFormat();

		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		assertEquals(false, f.parse(','));
		assertEquals("\"\"\"", f.getValue());
		assertEquals(0, f.getLine());
	}
	
	@Test(expected=ParserSyntaxException.class)
	public void testParseThrowsWhenQuotesNotEnded() {
		CsvInputFormat f = new CsvInputFormat();

		//double quots in text
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		f.parse(',');
	}
	
	@Test(expected=ParserSyntaxException.class)
	public void testParseThrowsWhenQuotesEndButNotStart() {
		CsvInputFormat f = new CsvInputFormat();

		//double quots in text
		assertEquals(true, f.parse('a'));
		assertEquals(true, f.parse('"'));
		f.parse(',');
	}
	
	@Test
	public void endToEndTest() {
		try (InputStream is = new FileInputStream("src/tests/res/parser/csv-input.csv")) {
			CsvInputFormat format = new CsvInputFormat();
			ParserInputStream<CsvInputFormat> parser = new ParserInputStream<CsvInputFormat>(is, format);
			
			int i = 1;
			
			while (parser.next()) {
				switch(i) {
				case 1:
					assertEquals("simple text", format.getValue());
					assertEquals(0, format.getLine());
					break;
				case 2:
					assertEquals("\"text in quotes\"", format.getValue());
					assertEquals(0, format.getLine());
					break;
				case 3:
					assertEquals("text,with,colons", format.getValue());
					assertEquals(0, format.getLine());
					break;
				case 4:
					assertEquals("\\\"", format.getValue());
					assertEquals(1, format.getLine());
					break;
				case 5:
					assertEquals(" \"quotes\"", format.getValue());
					assertEquals(1, format.getLine());
					break;
				case 6:
					assertEquals("new\r\nline", format.getValue());
					assertEquals(1, format.getLine());
					break;
				case 7:
					assertEquals("", format.getValue());
					assertEquals(1, format.getLine());
					break;
				case 8:
					assertEquals("10000", format.getValue());
					assertEquals(1, format.getLine());
					break;
				case 9:
					assertEquals("'single", format.getValue());
					assertEquals(2, format.getLine());
					break;
				case 10:
					assertEquals(" quots'", format.getValue());
					assertEquals(2, format.getLine());
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
}
