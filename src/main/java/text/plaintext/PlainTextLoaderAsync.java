package text.plaintext;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class PlainTextLoaderAsync {
	
	private ExecutorService executor;
	
	private PlainTextLoader loader;
	
	public PlainTextLoaderAsync(ExecutorService executor) {
		this.executor = executor;
		this.loader = new PlainTextLoader();
	}
	
	public PlainTextLoaderAsync(ExecutorService executor, PlainTextLoader loader) {
		this.executor = executor;
		this.loader = loader;
	}
	

	public Future<Boolean> read(final BufferedReader br, final Consumer<String> consumer) throws IOException{
		return executor.submit(()->{
			return loader.read(br, consumer);
		});	
	}	
	
	public Future<String> readAsOneString(final BufferedReader br) throws IOException{
		return executor.submit(()->{
			return loader.readAsOneString(br);
		});	
	}
	
	public Future<List<String>> read(final BufferedReader br) throws IOException{
		return executor.submit(()->{
			return loader.read(br);
		});	
	}
	
	public Future<Collection<List<String>>> read(final BufferedReader br, final String split) throws IOException{
		return executor.submit(()->{
			return loader.read(br, split);
		});	
	}
}
