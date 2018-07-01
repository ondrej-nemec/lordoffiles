package text.xml.structures;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class XmlObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private Optional<String> value;
	
	private Optional<Map<String, String>> attributes;
	
	private Optional<List<XmlObject>> references;

	/*************************************/
	public XmlObject(final String name) {
		this.name = name;
		this.value = Optional.empty();
		this.attributes = Optional.empty();
		this.references = Optional.empty();
	}
	
	public XmlObject(final String name, final String value) {
		this.name = name;
		this.value = Optional.of(value);
		this.attributes = Optional.empty();
		this.references = Optional.empty();
	}
	
	public XmlObject(final Map<String, String> attributes, final String name) {
		this.name = name;
		this.value = Optional.empty();
		this.attributes = Optional.of(attributes);
		this.references = Optional.empty();
	}
	
	public XmlObject(final String name, final List<XmlObject> references) {
		this.name = name;
		this.value = Optional.empty();
		this.attributes = Optional.empty();
		this.references = Optional.of(references);
	}
	
	public XmlObject(
			final String name,
			final Map<String, String> attributes,
			final List<XmlObject> references) {
		this.name = name;
		this.value = Optional.empty();
		this.attributes = Optional.of(attributes);
		this.references = Optional.of(references);
	}
	
	public XmlObject(
			final String name,
			final Optional<String> value,
			final Optional<Map<String, String>> attributes,
			final Optional<List<XmlObject>> references){
		this.name = name;
		this.value = value;
		this.attributes = attributes;
		this.references = references;
	}
	
	/**************************************/
	public String getName() {
		return name;
	}
	
	public Optional<String> getValue() {
		return value;
	}
	
	public Optional<Map<String, String>> getAttributes() {
		return attributes;
	}
	
	public Optional<List<XmlObject>> getReferences() {
		return references;
	}
	/*********************************/
	public void setName(final String name){
		this.name = name;
	}
	
	public void setValue(final String value){
		this.value = Optional.of(value);
	}
	
	public void setAttributes(final Map<String, String> atributes){
		this.attributes = Optional.of(atributes);
	}
	
	public void setReferences(final List<XmlObject> references){
		this.references = Optional.of(references);
	}
	
	/****************************/
	
	@Override
	public String toString() {
		String aux ="";
		aux += name;
		//value
		aux += " [";
		if(!value.equals(Optional.empty()))
			aux +=value.get();
		aux += "]";
		//atributes
		if(!attributes.equals(Optional.empty()))
			aux += attributes.get().toString();
		else
		 aux += "{}";
		//references
		if(!references.equals(Optional.empty()))
			for(int i = 0; i<references.get().size();i++){
				aux +="\n\t" +references.get().get(i).toString();
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
		if(value.orElse("") != aux.getValue().orElse(""))
			return false;
		
		if(attributes.equals(Optional.empty()) != aux.getAttributes().equals(Optional.empty()))
			return false;
		if(!attributes.equals(Optional.empty()) && !attributes.get().equals(aux.getAttributes().get()))
			return false;
		
		if(references.equals(Optional.empty()) != aux.getReferences().equals(Optional.empty()))
			return false;
		if(!references.equals(Optional.empty()) && !references.get().equals(aux.getReferences().get()))
			return false;
		return true;
	}
	
}
