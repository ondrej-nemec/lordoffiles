package text.xml.structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XmlObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String value;
	
	private Map<String, String> attributes;
	
	private List<XmlObject> references;

	/*************************************/
	public XmlObject(final String name) {
		this.name = name;
		this.value = "";
		this.attributes = new HashMap<>();
		this.references = new ArrayList<>();
	}
	
	public XmlObject(final String name, final String value) {
		this.name = name;
		this.value = value;
		this.attributes = new HashMap<>();
		this.references = new ArrayList<>();
	}
	
	public XmlObject(final Map<String, String> attributes, final String name) {
		this.name = name;
		this.value = "";
		this.attributes = attributes;
		this.references = new ArrayList<>();
	}
	
	public XmlObject(final String name, final List<XmlObject> references) {
		this.name = name;
		this.value = "";
		this.attributes = new HashMap<>();
		this.references = references;
	}
	
	public XmlObject(
			final String name,
			final Map<String, String> attributes,
			final List<XmlObject> references) {
		this.name = name;
		this.value = "";
		this.attributes = attributes;
		this.references = references;
	}
	
	public XmlObject(
			final String name,
			final String value,
			final Map<String, String> attributes,
			final List<XmlObject> references){
		this.name = name;
		this.value = value;
		this.attributes = attributes;
		this.references = references;
	}
	
	/**************************************/
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value;
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	public List<XmlObject> getReferences() {
		return references;
	}
	/*********************************/
	public void setName(final String name){
		this.name = name;
	}
	
	public void setValue(final String value){
		this.value = value;
	}
	
	public void setAttributes(final Map<String, String> atributes){
		this.attributes = atributes;
	}
	
	public void setReferences(final List<XmlObject> references){
		this.references = references;
	}
	
	/****************************/
	
	@Override
	public String toString() {
		String aux ="";
		aux += name;
		//value
		aux += " [" + value + "]";
		//atributes
		aux += attributes.toString();//references
		for(int i = 0; i<references.size();i++){
			aux +="\n\t" +references.get(i).toString();
		}
		return aux;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof XmlObject))
			return false;
		XmlObject aux = (XmlObject)o;
		if(name != aux.getName())
			return false;
		if(value != aux.getValue())
			return false;
		
		if(!attributes.equals(aux.getAttributes()))
			return false;
		
		if(!references.equals(aux.getReferences()))
			return false;
		return true;
	}
	
}
