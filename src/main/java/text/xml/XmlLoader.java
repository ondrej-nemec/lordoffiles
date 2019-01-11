package text.xml;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import exceptions.FileCouldNotBeClosedException;
import text.BufferedReaderFactory;
import text.xml.structures.XmlObject;

public class XmlLoader extends BufferedReaderFactory{

	public boolean read(final BufferedReader br, Consumer<XMLStreamReader> consumer) throws XMLStreamException, FileCouldNotBeClosedException{
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader in = null;
		try {
			in = factory.createXMLStreamReader(br);
			while(in.hasNext()){
				in.next();
				consumer.accept(in);
			}
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				throw new FileCouldNotBeClosedException();
			}
		}
		return true;
	}	
		
	public XmlObject read(final BufferedReader br) throws FileCouldNotBeClosedException, XMLStreamException{
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader in = null;
		try {
			in = factory.createXMLStreamReader(br);
			in.next();
			return readLevel(in);
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				throw new FileCouldNotBeClosedException();
			}
		}
	}
	
	private XmlObject readLevel(XMLStreamReader in) throws XMLStreamException{
		String name = in.getName().getLocalPart();
		Map<String, String> attributes = readAttributes(in);
		String value = "";
		List<XmlObject> references = new ArrayList<>();
		while(in.hasNext() && in.getEventType()!=XMLStreamConstants.END_ELEMENT){
			in.next();
			if(in.getEventType() == XMLStreamConstants.CHARACTERS){
				value = in.getText();
				Pattern p = Pattern.compile("[\t|\n]*"); //(.*)([\t|\n| ]*)
				Matcher m = p.matcher(value);
				while(m.find()){
					value = value.replace(m.group(), "");
				}
			}else if(in.getEventType() == XMLStreamConstants.START_ELEMENT){
				references=readReferences(in, name);
			}
		}
		return new XmlObject(name, value, attributes, references);
	}
	
	private Map<String, String> readAttributes(XMLStreamReader in){
		if(in.getAttributeCount() == 0)
			return new HashMap<>();
		Map<String, String> aux = new HashMap<>();
		for(int i = 0; i<in.getAttributeCount(); i++){
			aux.put(
					in.getAttributeLocalName(i),
					in.getAttributeValue(i)
					);
		}
		return aux;
	}
	
	private List<XmlObject> readReferences(XMLStreamReader in, String name) throws XMLStreamException {
		List<XmlObject> result = new ArrayList<>();
		while(in.getEventType() != XMLStreamConstants.END_ELEMENT || in.getName().getLocalPart() != name){
			if(in.getEventType() == XMLStreamConstants.START_ELEMENT ){
				result.add(readLevel(in));
			}else /*if(in.hasNext())*/{
				in.next();
			}
		}
		return result;
	}	
}
