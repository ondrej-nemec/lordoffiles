# Lord of files

[![](https://jitpack.io/v/ondrej-nemec/lordoffiles.svg)](https://jitpack.io/#ondrej-nemec/lordoffiles)
[![MIT License](http://img.shields.io/badge/license-MIT-green.svg) ](https://github.com/ondrej-nemec/lordoffiles/blob/master/LICENSE)

* [Description](#description)
* [Get library](#how-to-install)
* [Usage](#usage)
	

## Description
Package provide simply way how to read from file or write to file.

## How to install
### Download:

<a href="https://ondrej-nemec.github.io/download/lof-2.0.jar" target=_blank>Download jar</a>

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
  <version>v2.0-alpha</version>
</dependency>
```

## Usage

### Multimedia.audio
For work with audio binary files. Allow save binary data or load data.
**Saving** with `AudioCreator`:
```java
// constructor
AudioCreator(final OutputStream destination);
// save data
public void save(AudioFormat format, AudioFileFormat.Type type, ByteArrayOutputStream data) throws IOException;
```
**Loading** with `AudioLoader`:
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
**Capturing** provides `Microphone` class:
```java
// constructor
Microphone(final TargetDataLine line);
// capture - first way
public void capture(final AudioFormat format, final Consumer<byte[]> consumer) throws LineUnavailableException;
// capture - second way
public ByteArrayInputStream capture(final AudioFormat format) throws LineUnavailableException;
```
Be careful, `Microphone.capture(...)` is blocking. Playing in thread you must manage yourself. If you want to stop capturing, set public attribute `boolean Microphone.capture` to false.
For **Playing** you could use two ways. `Playback` and `Reproductor` class.
**Playback** class:
```java
// constructor
Playback(final AudioInputStream stream, final Clip clip);
// TODO
```
This solution run in its own threads.
**Reproductor** class:
```java
// constructor
Reproductor(final SourceDataLine line)
// playing
public void play(final AudioFormat format, final ByteArrayOutputStream data) throws IOException, LineUnavailableException;
```
Be careful, `Reproductor.play(...)` is blocking. Playing in thread you must manage yourself. If you want to stop playing, set public attribute `boolean Reproductor.play` to false.
### Parser
LOF provide some parsers. 
### Parser.csv
### Parser.env
### Text.binary files
### Text.plaintext files
### Text.XML files