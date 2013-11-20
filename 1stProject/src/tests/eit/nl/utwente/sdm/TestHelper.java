package tests.eit.nl.utwente.sdm;

import java.util.Map;

import it.unisa.dia.gas.jpbc.CurveGenerator;
import it.unisa.dia.gas.jpbc.CurveParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

import org.junit.Assert;
import org.junit.Test;

import eit.nl.utwente.sdm.MCPABEHelper;
import eit.nl.utwente.sdm.policy.AndNode;
import eit.nl.utwente.sdm.policy.AttributeNode;
import eit.nl.utwente.sdm.policy.Node;
import eit.nl.utwente.sdm.policy.OrNode;

public class TestHelper {

	@Test
	public void testHelper() {
		Node a1 = new AttributeNode(null, null, "a1");
		Node a2 = new AttributeNode(null, null, "a2");
		Node a3 = new AttributeNode(null, null, "a3");
		Node a4 = new AttributeNode(null, null, "a4");
		Node a5 = new AttributeNode(null, null, "a5");
		
		Node anda12 = new AndNode(a1, a2);
		Assert.assertNotNull(a1.getParent());
		Node ora123 = new OrNode(anda12, a3);
		Node and45 = new OrNode(a4, a5);
		Node root = new OrNode(ora123, and45);
		
		int rbits = 160;
		int qbits = 512;

		CurveGenerator curveGenerator = new TypeACurveGenerator(rbits, qbits);
		CurveParameters params = curveGenerator.generate();

		Pairing pairing = PairingFactory.getPairing(params);
		Field G1 = pairing.getG1();
		Field GT = pairing.getGT();
		Field Zr = pairing.getZr();

		Element rand = Zr.newRandomElement();
		Map<String, Element> attrRand = MCPABEHelper.generateRandomForTree(root, Zr, rand);
		System.out.println(attrRand);
		Assert.assertTrue(attrRand.containsKey("a1"));
		Assert.assertTrue(attrRand.containsKey("a2"));
		Assert.assertTrue(attrRand.containsKey("a3"));
		Assert.assertTrue(attrRand.containsKey("a4"));
		Assert.assertTrue(attrRand.containsKey("a5"));
		Element sa1 = attrRand.get("a1");
		Element sa2 = attrRand.get("a2");
		Element sa3 = attrRand.get("a3");
		Element sa4 = attrRand.get("a4");
		Element sa5 = attrRand.get("a5");
		sa1 = sa1.add(sa2);
		
		Assert.assertTrue(sa1.isEqual(sa3));
		Assert.assertTrue(sa4.isEqual(sa5));
		Assert.assertTrue(sa1.isEqual(sa5));
	}
	
}
