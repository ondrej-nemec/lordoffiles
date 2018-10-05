# Lord of files

[![](https://jitpack.io/v/ondrej-nemec/lordoffiles.svg)](https://jitpack.io/#ondrej-nemec/lordoffiles)
[![MIT License](http://img.shields.io/badge/license-MIT-green.svg) ](https://github.com/ondrej-nemec/lordoffiles/LICENSE)

* [Description](#description)
* [Get library](#how-to-install)
* [Usage](#usage)
	* [Text files](#text-files)
		* [Plain text](#plain-text)
		* [XML](#xml)
		* [Binary files](#binary-files)

## Description
Package provide simply way how to read from file or write to file.

## How to install
### Download:

<a href="https://ondrej-nemec.github.io/download/lof-1.2.1.jar" target=_blank>Download jar</a>

### Maven:
After `build` tag:
```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
And to `dependencies`:
```xml
<dependency>
  <groupId>com.github.ondrej-nemec</groupId>
  <artifactId>lordoffiles</artifactId>
  <version>v1.2.1-alpha</version>
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
**Remember: each Buffer need to be closed after finishing work. Best practice is call buffer method in try-with-resources block**
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
`XmlObject` is data object.
```java
public String getName();
	
public String getValue();
	
public Map<String, String> getAttributes();
	
public List<XmlObject> getReferences();

public void setName(final String name);
	
public void setValue(final String value);
	
public void setAttributes(final Map<String, String> atributes);
	
public void setReferences(final List<XmlObject> references);
```
As you can see from getters and setters, this object has four attributes. First is 'name' which is name of tag. 'Value' is value between start and end tag. 'Attributes' is hash map where key is class name and value is class value. And 'references' are subelements. See example:
```xml
<name className="classValue" class2Name="class2Name">
	Value
	<sublementName>...
	</subelementName>
</name>
```
If some of attributes don't exists in xml or tag is empty `<emptyTag />`, every attributes of `XmlObject` have default values **not null** except 'name' which is required.

#### Binary files
Work with binary files is very simply.
##### Reading
For reading use `BinaryCreator` class. This class allow set byte array size, which is used in reading with consumer 
```java
public int getDefaultBufferSize();

public void setDefaultBufferSize(int defaultBufferSize);
```
Methods:
```java
// InputStream factory
public InputStream stream(String name) throws FileNotFoundException;

// read byte array with default size and for each array apply consumer
public void read(final InputStream stream, final Consumer<byte[]> consumer) throws IOException;

// read byte array with given size and for each array apply consumer
public void read(final InputStream stream, final Consumer<byte[]> consumer, final int bufferSize) throws IOException;

// read whole content of stream
public byte[] read(final InputStream stream) throws IOException;
```
##### Writing
For wring is `BinaryLoader` class. 
```java
// OutputStream factory
public OutputStream stream(String name) throws FileNotFoundException;

// write given data to stream
public boolean write(final OutputStream stream, final byte[] data) throws IOException
```

### Other structured formats
<a href="https://www.baeldung.com/java-snake-yaml" target=_blank>YAML jar</a>

<a href="https://github.com/stleary/JSON-java" target=_blank>JSON</a>