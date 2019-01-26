package text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStreamFactory {
	
	public static InputStream input(final String name) throws FileNotFoundException {
		return new FileInputStream(name);
	}

	public static OutputStream output(final String name) throws FileNotFoundException {
		return new FileOutputStream(name);
	}
	
}
