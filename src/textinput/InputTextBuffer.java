package textinput;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class InputTextBuffer {

	protected BufferedReader buffer(final String path) throws FileNotFoundException{
		return new BufferedReader(
				new FileReader(path)
				);
	}
	
	
	protected BufferedReader buffer(final String path, final String charset) 
			throws UnsupportedEncodingException, FileNotFoundException{
		return new BufferedReader(
				new InputStreamReader(
						new FileInputStream(path), charset
				)
			);
	}
	
	protected BufferedReader buffer(final InputStream inputStream){
		return new BufferedReader(
				new InputStreamReader(
						inputStream
				)
			);
	}
	
	protected BufferedReader buffer(final InputStream inputStream, final String charset) 
			throws UnsupportedEncodingException{
		return new BufferedReader(
				new InputStreamReader(
						inputStream, charset
				)
			);
	}
	
	protected BufferedReader buffer(final URL url) throws IOException{
		return buffer(url.openStream());
	}
	
	protected BufferedReader buffer(final URL url, final String charset)
			throws UnsupportedEncodingException, IOException{
		return buffer(url.openStream(), charset);
	}
	
}
