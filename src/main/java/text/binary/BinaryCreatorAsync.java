package text.binary;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class BinaryCreatorAsync {

	private ExecutorService executor;
	private BinaryCreator creator;
		
	public BinaryCreatorAsync(ExecutorService executor) {
		this.executor = executor;
		this.creator = new BinaryCreator();
	}
	
	public BinaryCreatorAsync(ExecutorService executor, BinaryCreator creator) {
		this.executor = executor;
		this.creator = creator;
	}
	
	public Future<Boolean> write(final OutputStream stream, final byte[] data) throws IOException {
		return executor.submit(()->{
			return creator.write(stream, data);
		});
	}	
}
