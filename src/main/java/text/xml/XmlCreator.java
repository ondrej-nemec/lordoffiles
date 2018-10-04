package text.xml;

import java.io.BufferedWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


import exceptions.FileCouldNotBeClosedException;
import text.xml.structures.XmlObject;

public class XmlCreator {
	//TODO some factory for stream, maybe

	/**
	 * write whole step by step
	 * @param bw
	 * @param object
	 * @return
	 * @throws XMLStreamException
	 * @throws FileCouldNotBeClosedException
	 */
	public boolean write(final BufferedWriter bw, Consumer<XMLStreamWriter> consumer)
			throws XMLStreamException, FileCouldNotBeClosedException{
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		return write(factory.createXMLStreamWriter(bw), consumer);
	}
	
	protected boolean write(final XMLStreamWriter out, Consumer<XMLStreamWriter> consumer)
			throws XMLStreamException, FileCouldNotBeClosedException{
		try {
			out.writeStartDocument();
			consumer.accept(out);
			out.writeEndDocument();
			out.flush();
		} finally {
			try {
				if(out != null)
					out.close();
			} catch (Exception e) {
				throw new FileCouldNotBeClosedException();
			}
		}
		return true;
	}

	/**
	 * write whole xml object
	 * @param bw
	 * @param object
	 * @return
	 * @throws XMLStreamException
	 * @throws FileCouldNotBeClosedException
	 */
	public boolean write(final BufferedWriter bw, final XmlObject object)
			throws XMLStreamException, FileCouldNotBeClosedException{
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		return write(factory.createXMLStreamWriter(bw), object);
	}
	
	protected boolean write(final XMLStreamWriter out, final XmlObject object)
			throws XMLStreamException, FileCouldNotBeClosedException{
		try {
			out.writeStartDocument();
			writeLevel(out, object);
			out.writeEndDocument();
			out.flush();
		} finally {
			try {
				if(out != null)
					out.close();
			} catch (Exception e) {
				throw new FileCouldNotBeClosedException();
			}
		}
		return true;
	}
	
	/**
	 * write element
	 * @param out
	 * @param object
	 * @throws XMLStreamException
	 */
	private void writeLevel(XMLStreamWriter out, final XmlObject object) throws XMLStreamException{
		out.writeStartElement(object.getName());
		writeAtribute(out, object.getAttributes());
		writeValue(out, object.getValue());
		writeReferences(out, object.getReferences());
		out.writeEndElement();
	}
	
	/**
	 * write value, if exist
	 * @param out
	 * @param value
	 * @throws XMLStreamException
	 */
	private void writeValue(XMLStreamWriter out, String value) throws XMLStreamException{
		if(value != null)
			out.writeCharacters(value);
	}
	
	/**
	 * write attributes if exist
	 * @param out
	 * @param attributes
	 * @throws XMLStreamException
	 */
	private void writeAtribute(
			XMLStreamWriter out,
			final Map<String, String> attributes) throws XMLStreamException{
		if(attributes != null){
			Set<String> set = attributes.keySet();
			for (String key : set) {
				out.writeAttribute(key, attributes.get(key));
			}
		}
	}
	
	/**
	 * write subelements if exist
	 * @param out
	 * @param references
	 * @throws XMLStreamException
	 */
	private void writeReferences(XMLStreamWriter out, List<XmlObject> references) throws XMLStreamException{
		if(references != null)
			for(int i = 0; i<references.size();i++){
				writeLevel(out, references.get(i));
			}
	}
	
}
