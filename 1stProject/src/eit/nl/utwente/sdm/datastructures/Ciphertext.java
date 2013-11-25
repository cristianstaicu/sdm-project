package eit.nl.utwente.sdm.datastructures;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveElement;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveField;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;
import eit.nl.utwente.sdm.policy.Node;

public class Ciphertext {

	public final Node policy;
	public final Element c0;
	public final Element c1;
	private Map<String, Element> cComponents;

	public Ciphertext(Node policy, Element c0, Element c1,
			Map<String, Element> cjs) {
		this.policy = policy;
		this.c0 = c0;
		this.c1 = c1;
		this.cComponents = cjs;
	}

	public Ciphertext(String ctAsString, Field<Element> field, Field field2) {
		try {
			String[] components = ctAsString.split("\n");
			String encodedString = components[0].replaceAll("c0 = ", "").trim();
			c0 = field.newElement();
//			System.out.println(new String(Base64.decode(encodedString)));
			c0.setFromBytes(Base64.decode(encodedString));

			encodedString = components[1].replaceAll("c1 = ", "");
			c1 = field2.newElement();
			c1.setFromBytes(Base64.decode(encodedString));
			policy = Node.deserializeOrPolicy(components[2].replaceAll(
					"policy = ", ""));
			components[3] = components[3].replaceAll("ci = \\[", "").replaceAll(
					";\\]", "");
			String[] comps = components[3].split(";");
			cComponents = new HashMap<String, Element>();
			for (String compString : comps) {
				String[] mapString = compString.split(",");
				encodedString = mapString[1];
				Element compAsElement = field.newElement();
				compAsElement.setFromBytes(Base64.decode(encodedString));
				cComponents.put(mapString[0],
						compAsElement);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error decoding the ciphertext");
		}
	}

	public Map<String, Element> getComponents() {
		return cComponents;
	}

	@Override
	public String toString() {
		
		String result = "";
//		System.out.println(new String(c0.toBytes()));
		String encodeBytes = Base64.encodeBytes(c0.toBytes());

		result += "c0 = " + encodeBytes + "\n";
		encodeBytes = Base64.encodeBytes(c1.toBytes());
		result += "c1 = " + encodeBytes + "\n";
		result += "policy = " + policy.getPolicyAsString() + "\n";
		result += "ci = [";
		for (String comp : cComponents.keySet()) {
			encodeBytes = Base64.encodeBytes(cComponents.get(comp).toBytes());
			result += comp + "," + encodeBytes + ";";
		}
		result += "]\n";
		return result;
	}

}
