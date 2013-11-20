package eit.nl.utwente.sdm.policy;

public class OrNode extends Node {

	public OrNode(Node childLeft, Node childRight) {
		super(childLeft, childRight);
	}

	@Override
	protected String getLabel() {
		return "OR";
	}

}
