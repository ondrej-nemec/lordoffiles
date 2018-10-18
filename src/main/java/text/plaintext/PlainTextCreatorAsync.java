package text.plaintext;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class PlainTextCreatorAsync {

	private ExecutorService executor;
	private PlainTextCreator creator;
	
	public PlainTextCreatorAsync(ExecutorService executorService) {
		this.executor = executorService;
		this.creator = new PlainTextCreator();
	}
	
	public PlainTextCreatorAsync(ExecutorService executorService, PlainTextCreator creator) {
		this.executor = executorService;
		this.creator = creator;
	}
	
	public Future<Boolean> write(final BufferedWriter bw, final String data) throws IOException{
		return executor.submit(()->{
			return creator.write(bw, data);
		});
	}
	
	public Future<Boolean> write(final BufferedWriter bw, final List<String> data) throws IOException{
		return executor.submit(()->{
			return creator.write(bw, data);
		});
	}
	
	public Future<Boolean> write(final BufferedWriter bw, final List<List<String>> data, final String split) throws IOException{
		return executor.submit(()->{
			return creator.write(bw, data, split);
		});
	}
}
