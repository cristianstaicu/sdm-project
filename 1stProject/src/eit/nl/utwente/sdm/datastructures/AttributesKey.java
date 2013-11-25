package eit.nl.utwente.sdm.datastructures;

import it.unisa.dia.gas.jpbc.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	@Override
	public String toString() {
		return attributesComponents.toString();
	}
	
	public String getComponentsAsString() {
		String result = "";
		String prev = "";	
		for (String attr : attributesComponents.keySet()) {
			if (!prev.equals("")) {
				result += prev + ", ";
			}
			prev = attr;
		}
		result += prev;
		return result;
	}
	
	public List<String> getComponents() {
		List<String> result = new ArrayList<String>();
		for (String comp : attributesComponents.keySet())
			result.add(comp);
		return result;
	}
	
}
