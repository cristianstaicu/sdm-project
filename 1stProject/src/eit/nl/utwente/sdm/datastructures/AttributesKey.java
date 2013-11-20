package eit.nl.utwente.sdm.datastructures;

import it.unisa.dia.gas.jpbc.Element;

import java.util.HashMap;
import java.util.Map;

public class AttributesKey {

	private Map<String, Element> attributesComponents;
	
	public AttributesKey(Map<String, Element> list) {
		this.attributesComponents = list;
	}
	
	public AttributesKey() {
		attributesComponents = new HashMap<String, Element>();
	}
	
	public Element getKeyComponent(String attribute) {
		return attributesComponents.get(attribute);
	}
	
	public void addKeyComponent(String attribute, Element el) {
		attributesComponents.put(attribute, el);
	}

	public boolean containsComponent(String attribute) {
		return attributesComponents.containsKey(attribute);
	}
	
}
