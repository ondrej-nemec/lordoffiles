package text;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class BufferedReaderFactory {

	public static BufferedReader buffer(final File file) throws FileNotFoundException{
		return new BufferedReader(
				new FileReader(file)
				);
	}
	
	public static BufferedReader buffer(final String path) throws FileNotFoundException{
		return new BufferedReader(
				new FileReader(path)
				);
	}
	
	
	public static BufferedReader buffer(final File file, final String charset) 
			throws UnsupportedEncodingException, FileNotFoundException{
		return new BufferedReader(
				new InputStreamReader(
						new FileInputStream(file), charset
				)
			);
	}
	
	public static BufferedReader buffer(final String path, final String charset) 
			throws UnsupportedEncodingException, FileNotFoundException{
		return new BufferedReader(
				new InputStreamReader(
						new FileInputStream(path), charset
				)
			);
	}
	
	public static BufferedReader buffer(final InputStream inputStream){
		return new BufferedReader(
				new InputStreamReader(
						inputStream
				)
			);
	}
	
	public static BufferedReader buffer(final InputStream inputStream, final String charset) 
			throws UnsupportedEncodingException{
		return new BufferedReader(
				new InputStreamReader(
						inputStream, charset
				)
			);
	}
	
	public static BufferedReader buffer(final URL url) throws IOException{
		return buffer(url.openStream());
	}
	
	public static BufferedReader buffer(final URL url, final String charset)
			throws UnsupportedEncodingException, IOException{
		return buffer(url.openStream(), charset);
	}

}
