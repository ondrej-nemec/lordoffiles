package textoutput;

import java.io.BufferedWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter;

import exceptions.FileCouldNotBeClosedException;
import textinput.structures.XmlObject;

public class XmlCreator {

	/**
	 * write whole step by step
	 * @param bw
	 * @param object
	 * @return
	 * @throws XMLStreamException
	 * @throws FileCouldNotBeClosedException
	 */
	public void write(final BufferedWriter bw, Consumer<XMLStreamWriter> consumer)
			throws XMLStreamException, FileCouldNotBeClosedException{
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter out= null;
		try {
			out = new IndentingXMLStreamWriter( 
					factory.createXMLStreamWriter(bw)
				);
			
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
		XMLStreamWriter out= null;
		try {
			out = new IndentingXMLStreamWriter( 
					factory.createXMLStreamWriter(bw)
				);
			
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
	private void writeValue(XMLStreamWriter out, Optional<String> value) throws XMLStreamException{
		if(!value.equals(Optional.empty()))
			out.writeCharacters(value.get());
	}
	
	/**
	 * write attributes if exist
	 * @param out
	 * @param attributes
	 * @throws XMLStreamException
	 */
	private void writeAtribute(
			XMLStreamWriter out,
			final Optional<Map<String, String>> attributes) throws XMLStreamException{
		if(!attributes.equals(Optional.empty())){
			Set<String> set = attributes.get().keySet();
			for (String key : set) {
				out.writeAttribute(key, attributes.get().get(key));
			}
		}
	}
	
	/**
	 * write subelements if exist
	 * @param out
	 * @param references
	 * @throws XMLStreamException
	 */
	private void writeReferences(XMLStreamWriter out, Optional<List<XmlObject>> references) throws XMLStreamException{
		if(!references.equals(Optional.empty()))
			for(int i = 0; i<references.get().size();i++){
				writeLevel(out, references.get().get(i));
			}
	}
	
}
