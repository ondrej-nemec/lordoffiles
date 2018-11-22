package parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import exceptions.ParserSyntaxException;

public class CsvFormatTest {
	
	@Test
	public void testParseGrid() {
		CsvFormat f = new CsvFormat();
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
		CsvFormat f = new CsvFormat();
		
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
		CsvFormat f = new CsvFormat();

		//new line windows
		assertEquals(true, f.parse('n'));
		assertEquals(true, f.parse('\r'));
		assertEquals(false, f.parse('\n'));
		assertEquals("n", f.getValue());
		assertEquals(0, f.getLine());
	
	}
	
	@Test
	public void testParseQuotsInText() {
		CsvFormat f = new CsvFormat();

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
		CsvFormat f = new CsvFormat();
		
		// ignoring \
		assertEquals(true, f.parse('\\'));
		assertEquals(false, f.parse(','));
		assertEquals("\\", f.getValue());
		assertEquals(0, f.getLine());
	}
	
	@Test
	public void testParseNewLineInQuots() {
		CsvFormat f = new CsvFormat();
		
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
		CsvFormat f = new CsvFormat();
		
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
		CsvFormat f = new CsvFormat();

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
		CsvFormat f = new CsvFormat();

		//double quots in text
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		f.parse(',');
	}
	
	@Test(expected=ParserSyntaxException.class)
	public void testParseThrowsWhenQuotesEndButNotStart() {
		CsvFormat f = new CsvFormat();

		//double quots in text
		assertEquals(true, f.parse('a'));
		assertEquals(true, f.parse('"'));
		f.parse(',');
	}
}
