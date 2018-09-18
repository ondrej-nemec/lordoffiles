# Lord of files
**Newest version:** 1.1

* [Description](#description)
* [Get library](#how-to-install)
* [Usage](#usage)
	*[Text files](#text-files)
		* [Plain text](#plain-text)
		* [XML](#xml)

## Description
Package provide simply way how to read from file or write to file.

## How to install
### Download:

<a href="https://ondrej-nemec.github.io/download/lof-1.1.jar" target=_blank>Download jar</a>

### Maven:

```xml
<dependency>
  <groupId>io.github.ondrej-nemec.lordoffiles</groupId>
  <artifactId>lof</artifactId>
  <version>1.1</version>
</dependency>
```

## Usage
### Text files
Each text-reading-class extends `InputTextBuffer` which is factory for `BufferReader`. Use `buffer` method (is not static) and give what you have (URL, InputStream, Path, name or charset).
```java
public BufferedReader buffer(final String path) throws FileNotFoundException;	
	
public BufferedReader buffer(final String path, final String charset) throws UnsupportedEncodingException, FileNotFoundException;
	
public BufferedReader buffer(final InputStream inputStream);
	
public BufferedReader buffer(final InputStream inputStream, final String charset) throws UnsupportedEncodingException;
	
public BufferedReader buffer(final URL url) throws IOException;
	
public BufferedReader buffer(final URL url, final String charset);
```
Similar text-writing-classes extends `OutputTextBuffer`, which is factory for `BufferWriter`. Call `buffer`, you can give: path, charset, OutputStream or append.
```java
public BufferedWriter buffer(final String path, boolean append) throws IOException;
	
public BufferedWriter buffer(final String path, final String charset, boolean append) throws UnsupportedEncodingException, FileNotFoundException;
	
public BufferedWriter buffer(final OutputStream outputStream);
	
public BufferedWriter buffer(final OutputStream outputStream, final String charset) throws UnsupportedEncodingException;
```
** **
#### Plain text

#### XML