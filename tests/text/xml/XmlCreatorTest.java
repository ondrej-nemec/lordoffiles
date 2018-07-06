package text.xml;

import static org.junit.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.junit.Test;

import text.xml.structures.XmlObject;

public class XmlCreatorTest {

	@Test
	public void testWriteConsumerWorks(){
		String path = "tests/text/xml/res/write-consumer.xml";
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
			clearRepository(path);
			Consumer<XMLStreamWriter> consumer = (out)->{
				try {
					out.writeStartElement("element");
					out.writeCharacters("Value");
					out.writeEndElement();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			
			XmlCreator creator = new XmlCreator();
			creator.write(bw, consumer);
			System.err.println("This file need checks: " + path);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		} catch (XMLStreamException e) {
			e.printStackTrace();
			fail("XMLStreamException");
		}
	}
	
	
	@Test
	public void testWriteXmlObjectWorks(){
		String path = "tests/text/xml/res/write-xmlobject.xml";
		
		Map<String, String> firstAttributes = new HashMap<>();
		firstAttributes.put("class", "class1");
		XmlObject first = new XmlObject("first", "Value of element", firstAttributes, new ArrayList<>());
		
		Map<String, String> secondAttributes = new HashMap<>();
		secondAttributes.put("class", "class2");
		secondAttributes.put("target", "blank");
		XmlObject second = new XmlObject("second", Arrays.asList(new XmlObject("subelement", "Sub-element")));
		second.setAttributes(secondAttributes);
		
		XmlObject xmlObjec1 = new XmlObject("element", Arrays.asList(first, second));
		
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(path))){
			clearRepository(path);
			XmlCreator creator = new XmlCreator();
			creator.write(bw, xmlObjec1);
			System.err.println("This file need checks: " + path);
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		} catch (XMLStreamException e) {
			e.printStackTrace();
			fail("XMLStreamException");
		}
	}
	
	private void clearRepository(String path) throws IOException{
		File file = new File(path);
		if(file.exists())
			file.delete();
		file.createNewFile();
	}
}
