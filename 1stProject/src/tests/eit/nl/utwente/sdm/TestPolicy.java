package tests.eit.nl.utwente.sdm;

import org.junit.Assert;
import org.junit.Test;

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
	
}
