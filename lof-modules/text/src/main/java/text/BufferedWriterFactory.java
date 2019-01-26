package text;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class BufferedWriterFactory {

	public static BufferedWriter buffer(final String path, boolean append) throws IOException{
		return new BufferedWriter(
				new FileWriter(path, append)
			);
	}
	
	public static BufferedWriter buffer(final String path, final String charset, boolean append)
			throws UnsupportedEncodingException, FileNotFoundException{
		return new BufferedWriter(
				new OutputStreamWriter(
						new FileOutputStream(path, append), charset
					)
				);
	}
	
	public static BufferedWriter buffer(final OutputStream outputStream){
		return new BufferedWriter(
				new OutputStreamWriter(outputStream)
			);
	}
	
	public static BufferedWriter buffer(final OutputStream outputStream, final String charset)
			throws UnsupportedEncodingException{
		return new BufferedWriter(
				new OutputStreamWriter(
						outputStream, charset
					)
			);
	}

}
