package eit.nl.utwente.sdm;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eit.nl.utwente.sdm.datastructures.Ciphertext;
import eit.nl.utwente.sdm.datastructures.PublicKey;
import eit.nl.utwente.sdm.policy.AttributeNode;
import eit.nl.utwente.sdm.policy.Node;
import eit.nl.utwente.sdm.policy.OrNode;

public class MCPABEHelper {

	/**
	 * The method assumes there are no duplicated attributes in the tree
	 * @param srand 
	 * 
	 */
	public static Map<String, Element> generateRandomForTree(Node tree, Field<Element> field, Element srand) {
		Map<String, Element> result = new HashMap<String, Element>();
		computeRandom(result, tree, field, srand);
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
	
	public static Ciphertext encrypt(String message, Node policy, PublicKey pk) {
		Element srand = pk.G0.newRandomElement();
		Map<String, Element> attrRand = generateRandomForTree(policy, pk.G0, srand);
		Element c0 = pk.generator.duplicate();
		c0.powZn(srand);
		Element c1 = pk.ypsilon.duplicate();
		c1.powZn(srand);
		//TODO multiply with message
		Map<String, Element> cjs = new HashMap<String,Element>();
		for (String attribute : attrRand.keySet()) {
			Element bigTj = pk.getKeyComponent(attribute);
			Element sj = attrRand.get(attribute);
			Element cj = bigTj.duplicate();
			cj.powZn(sj);
			cjs.put(attribute, cj);
		}
		return new Ciphertext(policy, c0, c1, cjs);
	}
	
}
