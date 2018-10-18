package text.xml;

import java.io.BufferedWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import exceptions.FileCouldNotBeClosedException;
import text.xml.structures.XmlObject;

public class XmlCreatorAsync {
	
	private ExecutorService executor;
	
	private XmlCreator creator;

	public XmlCreatorAsync(ExecutorService executor) {
		this.executor = executor;
		this.creator = new XmlCreator();
	}
	
	public XmlCreatorAsync(ExecutorService executor, XmlCreator creator) {
		this.executor = executor;
		this.creator = creator;
	}
	
	public Future<Boolean> write(final BufferedWriter bw, Consumer<XMLStreamWriter> consumer)
			throws XMLStreamException, FileCouldNotBeClosedException{
		return executor.submit(()->{
			return creator.write(bw, consumer);
		});
	}
	
	public Future<Boolean> write(final BufferedWriter bw, final XmlObject object)
			throws XMLStreamException, FileCouldNotBeClosedException{
		return executor.submit(()->{
			return creator.write(bw, object);
		});
	}
}
