package textinput;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import exceptions.FileCouldNotBeClosedException;
import textinput.structures.XmlObject;


public class XmlLoader extends InputTextBuffer{

	
	/**
	 * read xml step by step
	 * @param br
	 * @param consumer
	 * @throws XMLStreamException
	 * @throws FileCouldNotBeClosedException
	 */
	public void read(final BufferedReader br, Consumer<XMLStreamReader> consumer) 
			throws XMLStreamException, FileCouldNotBeClosedException{
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
	}
	
		
	/**
	 * load whole xml file to java object
	 * @param br
	 * @return object reprezentation of file
	 * @throws FileCouldNotBeClosedException
	 * @throws XMLStreamException
	 */
	public XmlObject read(final BufferedReader br) 
			throws FileCouldNotBeClosedException, XMLStreamException{
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
	
	/**
	 * read one level of file
	 * @param in
	 * @return
	 * @throws XMLStreamException
	 */
	private XmlObject readLevel(XMLStreamReader in) throws XMLStreamException{
		String name = in.getName().getLocalPart();
		Optional<Map<String, String>> attributes = readAttributes(in);
		Optional<String> value = Optional.empty();
		Optional<List<XmlObject>> references = Optional.empty();
		while(in.hasNext() && in.getEventType()!=XMLStreamConstants.END_ELEMENT){
			in.next();
			if(in.getEventType() == XMLStreamConstants.CHARACTERS){
				value = Optional.of(in.getText());
			}else if(in.getEventType() == XMLStreamConstants.START_ELEMENT){
				references=readReferences(in, name);
			}
		}
		return new XmlObject(name, value, attributes, references);
	}
	
	
	/**
	 * read all attributes of element
	 * @param in
	 * @return
	 */
	private Optional<Map<String, String>> readAttributes(XMLStreamReader in){
		if(in.getAttributeCount() == 0)
			return Optional.empty();
		Map<String, String> aux = new HashMap<>();
		for(int i = 0; i<in.getAttributeCount(); i++){
			aux.put(
					in.getAttributeLocalName(i),
					in.getAttributeValue(i)
					);
		}
		
		return Optional.of(aux);
	}
	
	/**
	 * read all subelements
	 * @param in
	 * @param name
	 * @return
	 * @throws XMLStreamException 
	 */
	private Optional<List<XmlObject>> readReferences(XMLStreamReader in, String name) throws XMLStreamException {
		List<XmlObject> result = new ArrayList<>();
		while(in.getEventType() != XMLStreamConstants.END_ELEMENT && in.getName().getLocalPart() != name){
			if(in.getEventType() == XMLStreamConstants.START_ELEMENT ){
				result.add(readLevel(in));
			}else /*if(in.hasNext())*/{
				in.next();
			}
		}
		return Optional.of(result);
	}

	
}
