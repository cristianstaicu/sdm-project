package tests.eit.nl.utwente.sdm;


import org.junit.Assert;
import org.junit.Test;

import it.unisa.dia.gas.jpbc.CurveGenerator;
import it.unisa.dia.gas.jpbc.CurveParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

public class TestBilinearMap {

	@SuppressWarnings("unchecked")
	@Test
	public void testDHAssumption() {
		int rbits = 160;
		int qbits = 512;

		CurveGenerator curveGenerator = new TypeACurveGenerator(rbits, qbits);
		CurveParameters params = curveGenerator.generate();

		Pairing pairing = PairingFactory.getPairing(params);
		Field<Element> G1 = pairing.getG1();
		Field<Element> GT = pairing.getGT();
		Field<Element> Zr = pairing.getZr();
		/*
		 * Suppose you want to compute the following pairing out = e(in1, in2),
		 * where in1 must be in the group G1, in2 must be in the group G2 and
		 * out will lie in the target group GT.
		 */

		/*
		 * Since G0 = G1 = F_q for some prime q = 3 mod 4, every element of the
		 * group is a generator. The only constraint to be ensured is e(g, g) !=
		 * 1
		 */
		Element generator, egg;
		do {
			generator = G1.newRandomElement();
			egg = pairing.pairing(generator, generator);
		} while (egg.equals(GT.newOneElement()));
		System.out.println("g = " + generator);

		/*
		 * Pick a, b two elements from Zr and compute z = a * b. Careful that
		 * all the operations are modifying the object on which they are
		 * called!!! ... that's why duplication is required
		 */
		Element a = Zr.newRandomElement();
		Element b = Zr.newRandomElement();
		Element z = a.duplicate();
		z.mul(b);
		System.out.println("a = " + a);
		System.out.println("b = " + b);
		System.out.println("z = " + z);

		/*
		 * Compute g^a, g^b in F_q and apply bilinear mapping
		 */
		Element first = generator.duplicate();
		first.powZn(a);
		Element second = generator.duplicate();
		first.powZn(b);
		Element out = pairing.pairing(first, second);
		System.out.println("e(g^a, g^b) = " + out);

		/*
		 * Raise e(g,g) to power z and check if == e(g^a, g^b)
		 */
		Element out2 = egg.duplicate();
		System.out.println("e(g, g) = " + out2);
		out2.powZn(z);
		System.out.println("e(g, g)^z = " + out2);
		Assert.assertEquals(out, out2);
	}

}
