package tests.eit.nl.utwente.sdm;

import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import eit.nl.utwente.sdm.MCPABEHelper;
import eit.nl.utwente.sdm.Mediator;
import eit.nl.utwente.sdm.TrustedAuthority;
import eit.nl.utwente.sdm.datastructures.Ciphertext;
import eit.nl.utwente.sdm.datastructures.PublicKey;
import eit.nl.utwente.sdm.datastructures.SecretKey;
import eit.nl.utwente.sdm.policy.AndNode;
import eit.nl.utwente.sdm.policy.AttributeNode;
import eit.nl.utwente.sdm.policy.Node;
import eit.nl.utwente.sdm.policy.OrNode;

public class TestEncryptionDec extends AbstractBilinearMapTest {

	@Test
	public void testConversionStringElement() {
		String message = "Abracadabra";
		BigInteger bi = new BigInteger(message.getBytes());
		Element msgEl = this.G1.newElement(bi);
		System.out.println(msgEl);
		BigInteger newBi = msgEl.toBigInteger();
		String newMsg = new String(newBi.toByteArray());
		Assert.assertTrue(newMsg.equals(message));
	}
	
	@Test
	public void testSimpleScenario() {
		Mediator mediator = new Mediator();
		TrustedAuthority ta = new TrustedAuthority(mediator);
		List<String> attrs = new ArrayList<String>();
		attrs.add("a1");
		attrs.add("a2");
		attrs.add("a3");
		attrs.add("a4");
		attrs.add("a5");
		ta.setup(attrs);
		attrs = new ArrayList<String>();
		attrs.add("a1");
		attrs.add("a2");
		SecretKey usersKey = ta.generateKey(1, attrs);
		Node a1 = new AttributeNode(null, null, "a1");
		Node a2 = new AttributeNode(null, null, "a2");
		Node a3 = new AttributeNode(null, null, "a3");
		Node a4 = new AttributeNode(null, null, "a4");
		Node a5 = new AttributeNode(null, null, "a5");
		
		Node anda12 = new AndNode(a1, a2);
		Assert.assertNotNull(a1.getParent());
		Node ora123 = new OrNode(anda12, a3);
		Node and45 = new OrNode(a4, a5);
		Node policyRoot = new OrNode(ora123, and45);
		Ciphertext ct = MCPABEHelper.encrypt("Abracadabra", policyRoot, ta.getPublicKey());
		System.out.println("Ciphertext:\n" + ct);
		Element cMed = mediator.mDecrypt(ct, attrs, 1);
		String msg = MCPABEHelper.decrypt(attrs, ct, cMed, usersKey, ta.getPublicKey());
		System.out.println(msg);
		Assert.assertTrue("Abracadabra".equals(msg));
	}
	
}
