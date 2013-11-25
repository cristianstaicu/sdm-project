package tests.eit.nl.utwente.sdm;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import eit.nl.utwente.sdm.Patient;
import eit.nl.utwente.sdm.policy.AndNode;
import eit.nl.utwente.sdm.policy.AttributeNode;
import eit.nl.utwente.sdm.policy.Node;
import eit.nl.utwente.sdm.policy.OrNode;

public class TestPolicy {

	@Test
	public void testPrintPolicy() {
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
		System.out.println(root);
		Assert.assertSame(getNoLines(root.toString()), 4);
	}

	private int getNoLines(String rootAsString) {
		return rootAsString.split("\n").length;
	}
	
	@Test
	public void testMinimalAttrsIdentification() {
		Node a1 = new AttributeNode(null, null, "a1");
		Node a2 = new AttributeNode(null, null, "a2");
		Node a3 = new AttributeNode(null, null, "a3");
		Node a4 = new AttributeNode(null, null, "a4");
		Node a5 = new AttributeNode(null, null, "a5");
		
		Node orNode = new OrNode(a1, a2);
		List<String> attrs = new ArrayList<String>();
		attrs.add("a1");
		attrs.add("a3");
		Set<String> minimalAttrSet = orNode.getMinimalAttrSet(attrs);
		Assert.assertSame(minimalAttrSet.size(), 1);
		Assert.assertTrue(minimalAttrSet.contains("a1"));
		Node andNode = new AndNode(orNode, a3);
		minimalAttrSet = andNode.getMinimalAttrSet(attrs);
		Assert.assertSame(minimalAttrSet.size(), 2);
		Assert.assertTrue(minimalAttrSet.contains("a1"));
		Assert.assertTrue(minimalAttrSet.contains("a3"));
		attrs = new ArrayList<String>();
		attrs.add("a3");
		minimalAttrSet = andNode.getMinimalAttrSet(attrs);
		Assert.assertTrue(minimalAttrSet == null);
		Node or2Node = new OrNode(a4, a5);
		Node and2Node = new AndNode(andNode, or2Node);
		attrs = new ArrayList<String>();
		attrs.add("a1");
		attrs.add("a2");
		attrs.add("a3");
		attrs.add("a4");
		attrs.add("a5");
		System.out.println(and2Node);
		minimalAttrSet = and2Node.getMinimalAttrSet(attrs);
	}
	
	@Test
	public void testSerialization() {
		Node policy = Patient.getPolicy(1, true, false, true);
		String serializedPolicy = policy.getPolicyAsString();
		Assert.assertTrue(serializedPolicy.split("OR").length == 3);
		Node newPolicy = Node.deserializeOrPolicy(serializedPolicy);
		Assert.assertTrue(serializedPolicy.equals(newPolicy.getPolicyAsString()));
		Assert.assertTrue(policy.toString().equals(newPolicy.toString()));
	}
	
}
