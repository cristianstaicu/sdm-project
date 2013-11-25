package tests.eit.nl.utwente.sdm;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;

import java.io.IOException;
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
	public void testElementSerialization() throws IOException {
		Element msgEl = this.G1.newRandomElement();
		byte[] bytes = msgEl.toBytes();
		Element msgEl2 = this.G1.newElement();
		msgEl2.setFromBytes(bytes);
		Assert.assertTrue(msgEl.isEqual(msgEl2));
		for (int i = 0; i < msgEl.toBytes().length; i++) {
			Assert.assertSame(msgEl.toBytes()[i], msgEl2.toBytes()[i]);
		}
		msgEl = this.G0.newRandomElement();
		bytes = msgEl.toBytes();
		String encodedBytes = Base64.encodeBytes(bytes);
		msgEl2 = this.G0.newElement();
		msgEl2.setFromBytes(Base64.decode(encodedBytes));
		Assert.assertTrue(msgEl.isEqual(msgEl2));
	}
	
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
//		attrs.add("a3");
		SecretKey usersKey = ta.generateKey("1", attrs);
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
		Element srand = ta.getPublicKey().Zr.newRandomElement();
		String message = "Abracadabra";
		Ciphertext ct = MCPABEHelper.encrypt(message, policyRoot, ta.getPublicKey(), srand);
		Element c0 = ta.getPublicKey().generator.duplicate();
		c0 = c0.powZn(srand);
		Assert.assertTrue(c0.isEqual(ct.c0));
		BigInteger msgBigInt = new BigInteger(message.getBytes());
		Element msgEl = ta.getPublicKey().G1.newElement(msgBigInt);
		Element c1 = ta.getPublicKey().egg.duplicate();
		c1.powZn(srand);
		c1.powZn(ta.getMasterKey().alpha);
		c1 = c1.mul(msgEl);
		Assert.assertTrue(c1.isEqual(ct.c1));
		Element cj = ta.getPublicKey().generator.duplicate(); 
		cj.powZn(ta.getMasterKey().getKeyComponent("a3"));
		cj.powZn(srand);
		Assert.assertTrue(cj.isEqual(ct.getComponents().get("a3")));
			
		System.out.println("Ciphertext:\n" + ct);
		Element cMed = mediator.mDecrypt(ct, attrs, "1");
		String msg = MCPABEHelper.decrypt(attrs, ct, cMed, usersKey, ta.getPublicKey());
		System.out.println(msg);
		Assert.assertTrue(message.equals(msg));
//		System.out.println(ct.toString());
		@SuppressWarnings("unchecked")
		Ciphertext newCt = new Ciphertext(ct.toString(), ta.getPublicKey().G0, ta.getPublicKey().G1);
		
		Assert.assertTrue(ct.c0.isEqual(newCt.c0));
		Assert.assertTrue(ct.c1.isEqual(newCt.c1));
		Assert.assertTrue(ct.policy.getPolicyAsString().equals(newCt.policy.getPolicyAsString()));
		Assert.assertTrue(ct.getComponents().equals(newCt.getComponents()));
		
	}
	
	
	
}
