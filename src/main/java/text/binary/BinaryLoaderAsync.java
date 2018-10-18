package text.binary;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class BinaryLoaderAsync {

	private ExecutorService executor;
	private BinaryLoader loader;

	public BinaryLoaderAsync(ExecutorService executor) {
		this.executor = executor;
		this.loader = new BinaryLoader();
	}
	
	public BinaryLoaderAsync(ExecutorService executor, BinaryLoader loader) {
		this.executor = executor;
		this.loader = loader;
	}
	
	public Future<Boolean> read(final InputStream stream, final Consumer<byte[]> consumer) throws IOException {
		return executor.submit(()->{
			return loader.read(stream, consumer);
		});	
	}

	public Future<Boolean> read(final InputStream stream, final Consumer<byte[]> consumer, final int bufferSize) throws IOException {
		return executor.submit(()->{
			return loader.read(stream, consumer, bufferSize);
		});
	}
	
	public Future<byte[]> read(final InputStream stream) throws IOException {
		return executor.submit(()->{
			return loader.read(stream);
		});
	}
}
