package text.xml;

import java.io.BufferedReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import exceptions.FileCouldNotBeClosedException;
import text.xml.structures.XmlObject;

public class XmlLoadeAsync {
	
	private ExecutorService executor;
	
	private XmlLoader loader;

	public XmlLoadeAsync(ExecutorService executor) {
		this.executor = executor;
		this.loader = new XmlLoader();
	}

	public XmlLoadeAsync(ExecutorService executor, XmlLoader loader) {
		this.executor = executor;
		this.loader = loader;
	}
	
	public Future<Boolean> read(final BufferedReader br, Consumer<XMLStreamReader> consumer) 
			throws XMLStreamException, FileCouldNotBeClosedException{
		return executor.submit(()->{
			return loader.read(br, consumer);
		});
	}
	
	public Future<XmlObject> read(final BufferedReader br) 
			throws FileCouldNotBeClosedException, XMLStreamException{
		return executor.submit(()->{
			return loader.read(br);
		});
	}
}
