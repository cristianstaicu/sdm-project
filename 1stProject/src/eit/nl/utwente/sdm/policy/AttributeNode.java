package eit.nl.utwente.sdm.policy;

public class AttributeNode extends Node {

	private String attribute;

	public AttributeNode(Node childLeft, Node childRight, String attribute) {
		super(childLeft, childRight);
		this.attribute = attribute;
	}

	@Override
	protected String getLabel() {
		return attribute;
	}

	
}
