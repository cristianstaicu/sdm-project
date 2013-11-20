package eit.nl.utwente.sdm.policy;

public class AndNode extends Node {

	public AndNode(Node childLeft, Node childRight) {
		super(childLeft, childRight);
	}

	@Override
	public String getLabel() {
		return "AND";
	}

}
