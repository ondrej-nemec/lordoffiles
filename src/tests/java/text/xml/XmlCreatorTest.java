package text.xml;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.junit.Test;
import org.mockito.Mockito;


import text.xml.structures.XmlObject;

public class XmlCreatorTest {

	private String pathConsumer = "src/tests/res/text/xml/write-consumer.xml";
	private String pathObject = "src/tests/res/text/xml/write-xmlobject.xml";
	
	@Test
	public void testWriteConsumerWorks(){	
		XmlCreator creator = new XmlCreator(null); //BufferedWriter not used in this test
		Consumer<XMLStreamWriter> consumer = (out)->{
				try {
					out.writeStartElement("element");
					out.writeCharacters("Value");
					out.writeEndElement();
				} catch (Exception e) {
					e.printStackTrace();
					fail("XmlStreamException");
				}
			};
		
		XMLStreamWriter out = null;
		try{
			out = mockStream(pathConsumer);
			clearRepository(pathConsumer);
			
			creator.write(out, consumer);
			
			verify(out).writeStartDocument();
			verify(out).writeStartElement("element");
			verify(out, times(1)).writeStartElement(Mockito.anyString());
			
			verify(out).writeCharacters("Value");
			verify(out, times(1)).writeCharacters(Mockito.anyString());
			
			verify(out).writeEndElement();
			verify(out).writeEndDocument();
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		} catch (XMLStreamException e) {
			e.printStackTrace();
			fail("XMLStreamException");
		} finally {
			try {
				if(out != null)
					out.close();
			} catch (Exception e) {
				e.printStackTrace();
				fail("Could not close document");
			}
		}
	}
	
	
	@Test
	public void testWriteXmlObjectWorks(){
		XmlCreator creator = new XmlCreator(null); // BufferedWriter not used in this test
		XmlObject xmlObjec = getObject();
		
		XMLStreamWriter out = null;
		try{
			out = mockStream(pathObject);
			clearRepository(pathObject);
		
			creator.write(out, xmlObjec);
			
			verify(out).writeStartDocument();
			
			verify(out).writeStartElement("element");
			verify(out).writeStartElement("first");
			verify(out).writeStartElement("second");
			verify(out).writeStartElement("subelement");
			verify(out, times(4)).writeStartElement(Mockito.anyString());
			
			verify(out).writeAttribute("class", "class1");
			verify(out).writeAttribute("class", "class2");
			verify(out).writeAttribute("target", "blank");
			verify(out, times(3)).writeAttribute(Mockito.anyString(), Mockito.anyString());
			
			verify(out).writeCharacters("Value of element");
			verify(out).writeCharacters("Sub-element");
			verify(out, times(2)).writeCharacters("");
			verify(out, times(4)).writeCharacters(Mockito.anyString());
			
			verify(out, times(4)).writeEndElement();

			verify(out).writeEndDocument();
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		} catch (XMLStreamException e) {
			e.printStackTrace();
			fail("XMLStreamException");
		} finally {
			try {
				if(out != null)
					out.close();
			} catch (Exception e) {
				e.printStackTrace();
				fail("Could not close document");
			}
		}
	}
	
	private XmlObject getObject(){
		Map<String, String> firstAttributes = new HashMap<>();
		firstAttributes.put("class", "class1");
		XmlObject first = new XmlObject("first", "Value of element", firstAttributes, new ArrayList<>());
		
		Map<String, String> secondAttributes = new HashMap<>();
		secondAttributes.put("class", "class2");
		secondAttributes.put("target", "blank");
		XmlObject second = new XmlObject("second", Arrays.asList(new XmlObject("subelement", "Sub-element")));
		second.setAttributes(secondAttributes);
		
		return new XmlObject("element", Arrays.asList(first, second));
	}
	
	private XMLStreamWriter mockStream(String path)
			throws XMLStreamException, IOException{
		return mock(XMLStreamWriter.class);
	}
	
	private void clearRepository(String path) throws IOException{
		File file = new File(path);
		if(file.exists())
			file.delete();
		file.createNewFile();
	}
}
