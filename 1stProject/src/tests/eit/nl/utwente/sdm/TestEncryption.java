package tests.eit.nl.utwente.sdm;

import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

public class TestEncryption extends AbstractBilinearMapTest {

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
	
}
