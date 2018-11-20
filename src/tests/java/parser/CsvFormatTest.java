package parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CsvFormatTest {
	
	@Test
	public void testParseGrid() {
		CsvFormat f = new CsvFormat();
		//just text
		assertEquals(true, f.parse('t'));
		assertEquals("t", f.getValue());
		assertEquals(0, f.getRowIndex());
		assertEquals(0, f.getColumnIndex());
		
		// first row
		assertEquals(false, f.parse(','));
		assertEquals("t", f.getValue());
		assertEquals(0, f.getRowIndex());
		assertEquals(0, f.getColumnIndex());
		
		// second text
		assertEquals(true, f.parse('s'));
		assertEquals(false, f.parse(','));
		assertEquals("s", f.getValue());
		assertEquals(0, f.getRowIndex());
		assertEquals(1, f.getColumnIndex());
		
		//new line
		assertEquals(true, f.parse('n'));
		assertEquals(false, f.parse('\n'));
		assertEquals("n", f.getValue());
		assertEquals(1, f.getRowIndex());
		assertEquals(0, f.getColumnIndex());
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
		assertEquals(0, f.getRowIndex());
		assertEquals(0, f.getColumnIndex());
	}

	
	@Test
	public void testParseNewLineWindows() {
		CsvFormat f = new CsvFormat();

		//new line windows
		assertEquals(true, f.parse('n'));
		assertEquals(true, f.parse('\r'));
		assertEquals(false, f.parse('\n'));
		assertEquals("n", f.getValue());
		assertEquals(1, f.getRowIndex());
		assertEquals(0, f.getColumnIndex());
	
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
		assertEquals(0, f.getRowIndex());
		assertEquals(0, f.getColumnIndex());
	}
	
	@Test
	public void testParseIgnorigBackslash() {
		CsvFormat f = new CsvFormat();
		
		// ignoring \
		assertEquals(true, f.parse('\\'));
		assertEquals(false, f.parse(','));
		assertEquals("\\", f.getValue());
		assertEquals(0, f.getRowIndex());
		assertEquals(0, f.getColumnIndex());
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
		assertEquals(0, f.getRowIndex());
		assertEquals(0, f.getColumnIndex());

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
		assertEquals(0, f.getRowIndex());
		assertEquals(0, f.getColumnIndex());
	}
	
	@Test
	public void testParseSpecialCase() {
		CsvFormat f = new CsvFormat();

		//double quots in text
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('a'));
		assertEquals(true, f.parse('"'));
		assertEquals(true, f.parse('"'));
	//	assertEquals(false, f.parse(','));
		assertEquals("\"a\"", f.getValue());
		assertEquals(0, f.getRowIndex());
		assertEquals(0, f.getColumnIndex());
	}


}
