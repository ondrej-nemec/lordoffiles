# Lord of files
**Newest version:** 1.1

* [Description](#description)
* [Get library](#how-to-install)
* [Usage](#usage)
	* [Text files](#text-files)
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
Each text-reading-class extends `InputTextBuffer` which is factory for `BufferReader`. Use `buffer` method (is not static):
```java
public BufferedReader buffer(final String path) throws FileNotFoundException;	
	
public BufferedReader buffer(final String path, final String charset) throws UnsupportedEncodingException, FileNotFoundException;
	
public BufferedReader buffer(final InputStream inputStream);
	
public BufferedReader buffer(final InputStream inputStream, final String charset) throws UnsupportedEncodingException;
	
public BufferedReader buffer(final URL url) throws IOException;
	
public BufferedReader buffer(final URL url, final String charset);
```
Similar text-writing-classes extends `OutputTextBuffer`, which is factory for `BufferWriter`. Call `buffer`:
```java
public BufferedWriter buffer(final String path, boolean append) throws IOException;
	
public BufferedWriter buffer(final String path, final String charset, boolean append) throws UnsupportedEncodingException, FileNotFoundException;
	
public BufferedWriter buffer(final OutputStream outputStream);
	
public BufferedWriter buffer(final OutputStream outputStream, final String charset) throws UnsupportedEncodingException;
```
** Remember: each Buffer need to be closed after finishing work. Best practice is call buffer method in try-with-resources block **
#### Plain text
##### Reading
`PlainTextLoader` class is for reading plain text from file:
```java
//read content of file by lines
//always read line, apply consumer, discart line and read new line
//at the end return true
public boolean read(final BufferedReader br, final Consumer<String> consumer) throws IOException

//read content of file as one string		
public String readAsOneString(final BufferedReader br) throws IOException

//read content of file as list of lines
public List<String> read(final BufferedReader br) throws IOException

//read content of file as list of lines and split each line with given 'split'
//data.get(r).get(c) are r x c matrix, r - row, c - column
public List<List<String>> read(final BufferedReader br, final String split) throws IOException;
```
##### Writing
For writing plain text is `PlainTextCreator` class. Has three method:
```java
//write or append string to file and write new line
public boolean write(final BufferedWriter bw, final String data) throws IOException;
	
//write or append string by string from list to file and write new line
public boolean write(final BufferedWriter bw, final List<String> data) throws IOException;

//write or append grid to file and write new line
//data.get(r).get(c) are r x c matrix, r - row, c - column
//strings in row are splited with 'split' string
public boolean write(final BufferedWriter bw, final List<List<String>> data, final String split) throws IOException;
```
Theses methods return true after finishing writing.
#### XML
##### Reading
To read xml file use `XmlLoader` (unexpectedly). You can use two ways. First allow you to manage reading, return true at the finish:
```java
//this method starts reading, calls next on stream and closes stream
public boolean read(final BufferedReader br, Consumer<XMLStreamReader> consumer) 
			throws XMLStreamException, FileCouldNotBeClosedException;
```
The second method reads whole xml to XmlObject. [More about XmlObject](#xmlobject)
```java
public XmlObject read(final BufferedReader br) 
			throws FileCouldNotBeClosedException, XMLStreamException;
```
##### Writing
`XmlCreator` allow write data to xml file. There are two ways too. Both return true after writing is finished. First:
```java
//this method starts reading, calls next on stream and closes stream
public boolean write(final BufferedWriter bw, Consumer<XMLStreamWriter> consumer)
			throws XMLStreamException, FileCouldNotBeClosedException;
```
And second write given XmlObject:
```java
public boolean write(final BufferedWriter bw, final XmlObject object)
			throws XMLStreamException, FileCouldNotBeClosedException
```
##### XmlObject


