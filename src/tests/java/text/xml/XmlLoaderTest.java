package text.xml;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import text.xml.structures.XmlObject;

@RunWith(Parameterized.class)
public class XmlLoaderTest {
	
	private String path;
	private XmlObject xmlObject;
	private String consumerExpected;
	private String consumerActual = "";
	
	public XmlLoaderTest(String path, XmlObject data, String consumerData) {
		super();
		this.path = path;
		this.xmlObject = data;
		this.consumerExpected = consumerData;
	}

	@Test
	public void testReadConsumerWorks(){
		try(BufferedReader br = new BufferedReader(
				new FileReader(path))){
			XmlLoader loader = new XmlLoader(br);
			Consumer<XMLStreamReader> consumer = (in)->{
				if(in.getEventType() == XMLStreamConstants.START_ELEMENT){
					for(int i = 0; i<in.getAttributeCount(); i++){
						setConsumerActual(in.getAttributeLocalName(i) + ": " +in.getAttributeValue(i));
					}
				}
			};
			loader.read(consumer);
			assertEquals(consumerExpected, consumerActual);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("FileNotFoundException");
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		} catch (XMLStreamException e) {
			e.printStackTrace();
			fail("XMLStreamException");
		}
	}

	@Test
	public void testReadXmlObjectWorks(){
		try(BufferedReader br = new BufferedReader(
				new FileReader(path))){
			XmlLoader loader = new XmlLoader(br);
			assertEquals(xmlObject, loader.read());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("FileNotFoundException");
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException");
		} catch (XMLStreamException e) {
			e.printStackTrace();
			fail("XMLStreamException");
		}
	}
	
	@Parameters
	public static Collection<Object[]> dataSet(){
		Map<String, String> firstAttributes = new HashMap<>();
		firstAttributes.put("class", "class1");
		XmlObject first = new XmlObject("first", "Value of element", firstAttributes, new ArrayList<>());
		
		Map<String, String> secondAttributes = new HashMap<>();
		secondAttributes.put("class", "class2");
		secondAttributes.put("target", "blank");
		XmlObject second = new XmlObject("second", Arrays.asList(new XmlObject("subelement", "Sub-element")));
		second.setAttributes(secondAttributes);
		
		XmlObject xmlObjec1 = new XmlObject("element", Arrays.asList(first, second));
		
		return Arrays.asList(
				new Object[]{
						"src/tests/res/text/xml/read-xmlobject.xml",
						xmlObjec1,
						"class: class1\n"
						+ "class: class2\n"
						+ "target: blank\n"},
				new Object[]{
						"src/tests/res/text/xml/read-special.xml",
						new XmlObject("element", 
								Arrays.asList(new XmlObject("element", "Value")
										)),
						""}
				);
	}
	
	private void setConsumerActual(String add){
		consumerActual += add + "\n";
	}
	
}
