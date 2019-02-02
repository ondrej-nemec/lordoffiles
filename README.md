# Lord of files

[![](https://jitpack.io/v/ondrej-nemec/lordoffiles.svg)](https://jitpack.io/#ondrej-nemec/lordoffiles)
[![MIT License](http://img.shields.io/badge/license-MIT-green.svg) ](https://github.com/ondrej-nemec/lordoffiles/blob/master/LICENSE)

* [Description](#description)
* [Get library](#how-to-install)
* [Usage](#usage)
	* [Multimedia](#multimedia)
	* [Parsers](#parser)
	* [Texts](#text)
	

## Description
Package provide simply way how to read from file or write to file.

## How to install
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

#### Whole project 

```xml
	<dependency>
	    <groupId>com.github.ondrej-nemec.lordoffiles</groupId>
	    <artifactId>lordoffiles</artifactId>
	    <version>v3.1</version>
	</dependency>
```

#### Module Text

```xml
	<dependency>
	    <groupId>com.github.ondrej-nemec.lordoffiles</groupId>
	    <artifactId>lof-text</artifactId>
	    <version>v3.1</version>
	</dependency>
```

#### Module Parsers 

```xml
	<dependency>
	    <groupId>com.github.ondrej-nemec.lordoffiles</groupId>
	    <artifactId>lof-parsers</artifactId>
	    <version>v3.1</version>
	</dependency>
```

#### Module Multimedia

```xml
	<dependency>
	    <groupId>com.github.ondrej-nemec.lordoffiles</groupId>
	    <artifactId>lof-multimedia</artifactId>
	    <version>v3.1</version>
	</dependency>
```


## Usage
### Multimedia
### Multimedia.audio
For work with audio binary files. Allow save binary data or load data.
#### Saving 
With `AudioCreator`:
```java
// constructor
public AudioCreator(final OutputStream destination);
// save data
public void save(final AudioFormat format, final AudioFileFormat.Type type, final ByteArrayOutputStream data) throws IOException;
```
#### Loading
With `AudioLoader`:
```java
// constructor
public AudioLoader(final AudioInputStream stream);
// load data - first way
public void load(final Consumer<byte[]> consumer) throws IOException;
// load data - second way
public ByteArrayInputStream load() throws IOException;
```
For `AudioInputStream` you can use `AudioInputStreamFactory` and `getStream(...)` method.

### Multimedia.sound
Way how to capture or play audio binary files. Classes in this package use `DataLine`. It could be getted by `DataLineFactory` and `getClip(...)`, `getSourceLine(...)` and `getTargetLine(...)` methods.
#### Capturing
It provides `Microphone` class:
```java
// constructor
public Microphone(final TargetDataLine line);
// capture - first way
public void capture(final AudioFormat format, final Consumer<byte[]> consumer) throws LineUnavailableException;
// capture - second way
public ByteArrayInputStream capture(final AudioFormat format) throws LineUnavailableException;
```
Be careful, `Microphone.capture(...)` is blocking. Playing in thread you must manage yourself. If you want to stop capturing, set public attribute `boolean Microphone.capture` to false.
#### Playing
You could use two ways. `Playback` and `Reproductor` class.

`Playback` class:
```java
// constructor
public Playback(final AudioInputStream stream, final Clip clip);
// start playing or continue in playing if pause() was called
public void play() throws LineUnavailableException, IOException;
// pause playing 
public void pause();
// stop playing, set position on start
public void stop();
// set loop, if count < 0 then loop is infinity, zero count stop looping
public void setLoop(final int count);
// set playing position on (actual + microSecond) or on end
public void foward(final long microSeconds);
// set playing position on (actual - microSecond) or on start
public void back(final long microSeconds);
// get duration of sound
public long getDuration();
// get actual position
public long getMicroSecondPosition();
```
This solution run in its own threads.

`Reproductor` class:
```java
// constructor
public Reproductor(final SourceDataLine line);
// playing
public void play(final AudioFormat format, final ByteArrayOutputStream data) throws IOException, LineUnavailableException;
```
Be careful, `Reproductor.play(...)` is blocking. Playing in thread you must manage yourself. If you want to stop playing, set public attribute `boolean Reproductor.play` to false.

### Parser
LOF provide some parsers. Work with each parser is similar to work with SAX.
**NOTE for reading:** Class, which provide reading - usually *Format*InputStream, is data object, too. So, after creating new instance of InputStream, you call in while cyklus `next()` method (return false if you are on end of file). Then in InputStream instance are saved data until next `next()` method  is called.

### Parser.csv
#### Saving
Use `CsvOutputStream`
```java
// first constructor
public CsvOutputStream(final OutputStream stream, final char separator);
// second constructor, default separator is `,`
public CsvOutputStream(final OutputStream stream);
// write one 'cell'
public void writeValue(final String value) throws IOException;
// finish writing line and start new line
public void writeNewLine() throws IOException;
```
#### Loading
Use `CsvInputStream`
```java
// first constructor
public CsvInputStream(final InputStream stream, final char separator);
//second constructor - default separator is ','
public CsvInputStream(final InputStream stream);
// return value between two separators
public String getValue();
// return number of line
public int getLine();
```

### Parser.env
#### Saving
Implemented by `EnvOutputStream`
```java
// constructor
public EnvOutputStream(final OutputStream stream);
// write one line, "key=value" will be written (of course without "")
public void writeTwins(final String key, final String value) throws IOException;
```
#### Loading
Implemented by `EnvInputStream`
```java
// constructor
public EnvInputStream(final InputStream stream);
// return value before '='
public String getKey();
// return value after '='
public String getValue();
```

### Text
For easy work with classes below, LOF provide `BufferReaderFactory`, `BufferWriterFactory` and `FileStreamFactory`.

### Text.binary files
Useful for writing or reading arrays of bytes.
#### Saving
With `BinaryCreator`
```java
// constructor
public BinaryCreator(final OutputStream stream);
// writing array of bytes
public void write(final byte[] data) throws IOException;
```
#### Loading
With `BinaryLoader`
```java
// constructor
public BinaryLoader(final InputStream stream);
// reading full array of bytes
public byte[] read() throws IOException;
// or you can use
public void read(final Consumer<byte[]> consumer);
```

### Text.plaintext files
#### Saving
`PlainTextCreator` class
```java
// constructor
public PlainTextCreator(final BufferedWriter bw);
// write string
public void write(final String data) throws IOException;
// write list of string - one item of list, one line
public void write(final List<String> data) throws IOException;
// write grid (simply csv), first lists are rows, lists in lists are columns
public void write(final List<List<String>> data, final String split) throws IOException;
```
#### Loading
`PlainTextLoader` class
```java
// constructor
public PlainTextLoader(final BufferedReader br);
// for reading long files, read line, apply consumer and crash line
public void read(final Consumer<String> consumer) throws IOException
// read whole content as one string
public String readAsOneString() throws IOException;
// read content as list of lines
public List<String> read() throws IOException;
// read content as grid, lists are rows, lists in lists are columns
public Collection<List<String>> read(final String split) throws IOException;
```

### Text.XML files
**XmlObject**
`XmlObject` serve for read or write whole xml. So this object is data-object. Has four attributes: `String name` which is required and represent name of element (default value is empty String), `String value` which is string value between start element (default value is empty Map) and end element, `Map<String, String> attributes` that are 'classes' in start element and finally `List<XmlObject> references` - all sub-elements (default value is empty List).
#### Saving
Use `XmlCreator`
```java
// constructor
public XmlCreator(final BufferedWriter bw);
// call writeStartDocument and after consumer, writeEndElement
public void write(final Consumer<XMLStreamWriter> consumer) throws XMLStreamException, StreamCouldNotBeClosedException;
// write XmlObject
public void write(final XmlObject object) throws XMLStreamException, StreamCouldNotBeClosedException;
```
#### Loading
Use `XmlLoader`
```java
// constructor
public XmlLoader(final BufferedReader br);
// in while cyklus is called consumer.accept(...)
public void read(final Consumer<XMLStreamReader> consumer) throws XMLStreamException, StreamCouldNotBeClosedException;
// read whole xml file to XmlObject
public XmlObject read() throws StreamCouldNotBeClosedException, XMLStreamException;
```