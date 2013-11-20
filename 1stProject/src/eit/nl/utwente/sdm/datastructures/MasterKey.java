package eit.nl.utwente.sdm.datastructures;

import it.unisa.dia.gas.jpbc.Element;

import java.util.Map;

public class MasterKey extends AttributesKey {

	public final Element alpha;
	
	public MasterKey(Element alpha, Map<String, Element> attrList) {
		super(attrList);
		this.alpha = alpha;
	}
	
}
