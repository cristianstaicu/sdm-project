package eit.nl.utwente.sdm.datastructures;

import java.util.Map;

import it.unisa.dia.gas.jpbc.Element;
import eit.nl.utwente.sdm.policy.Node;

public class Ciphertext {

	public final Node policy;
	public final Element c0;
	public final Element c1;
	private Map<String, Element> cComponents;
	
	public Ciphertext(Node policy, Element c0, Element c1, Map<String, Element> cjs) {
		this.policy = policy;
		this.c0 = c0;
		this.c1 = c1;
		this.cComponents = cjs;
	}
	
	public Map<String, Element> getComponents() {
		return cComponents;
	}
	
}
