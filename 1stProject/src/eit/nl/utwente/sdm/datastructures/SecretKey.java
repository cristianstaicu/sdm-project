package eit.nl.utwente.sdm.datastructures;

import it.unisa.dia.gas.jpbc.Element;

import java.util.List;
import java.util.Map;

public class SecretKey extends AttributesKey {
	
	public final Element d0;

	public SecretKey(Element d0, Map<String, Element> attrList) {
		super(attrList);
		this.d0 = d0;
	}
	
}
