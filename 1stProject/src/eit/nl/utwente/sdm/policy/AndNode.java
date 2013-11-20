package eit.nl.utwente.sdm.policy;

public class AndNode extends Node {

	public AndNode(Node childLeft, Node childRight) {
		super(childLeft, childRight);
	}

	@Override
	protected String getLabel() {
		return "AND";
	}

}
