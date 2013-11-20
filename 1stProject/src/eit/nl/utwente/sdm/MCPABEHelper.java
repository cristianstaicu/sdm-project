package eit.nl.utwente.sdm;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eit.nl.utwente.sdm.policy.AttributeNode;
import eit.nl.utwente.sdm.policy.Node;
import eit.nl.utwente.sdm.policy.OrNode;

public class MCPABEHelper {

	/**
	 * The method assumes there are no duplicated attributes in the tree
	 * 
	 */
	public static Map<String, Element> generateRandomForTree(Node tree, Field<Element> field) {
		Map<String, Element> result = new HashMap<String, Element>();
		Element newRandomElement = field.newRandomElement();
		computeRandom(result, tree, field, newRandomElement);
		return result;
	}

	private static void computeRandom(Map<String, Element> attributesRandom, Node tree, Field field,
			Element s) {
		if (tree instanceof AttributeNode) {
			attributesRandom.put(tree.getLabel(), s);
		} else if (tree instanceof OrNode) {
			computeRandom(attributesRandom, tree.childLeft, field, s);
			computeRandom(attributesRandom, tree.childRight, field, s);
		} else {
			Element s1 = field.newRandomElement();
			Element sminuss1 = s.duplicate();
			sminuss1 = sminuss1.sub(s1);
			computeRandom(attributesRandom, tree.childLeft, field, s1);
			computeRandom(attributesRandom, tree.childRight, field, sminuss1);
		}
	}
	
}
