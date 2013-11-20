package tests.eit.nl.utwente.sdm;

import it.unisa.dia.gas.jpbc.CurveGenerator;
import it.unisa.dia.gas.jpbc.CurveParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

import org.junit.Test;

public class TestDivision {
	
	@Test
	public void testDivision(){
		int rbits = 160;
		int qbits = 512;

		CurveGenerator curveGenerator = new TypeACurveGenerator(rbits, qbits);
		CurveParameters params = curveGenerator.generate();

		Pairing pairing = PairingFactory.getPairing(params);
		Field G1 = pairing.getG1();
		Field GT = pairing.getGT();
		Field Zr = pairing.getZr();
		
		Element generator, egg;
		do {
			generator = G1.newRandomElement();
			egg = pairing.pairing(generator, generator);
		} while (egg.equals(GT.newOneElement()));
//		System.out.println("g = " + generator);
		
		Element a = Zr.newRandomElement();
		Element b = Zr.newRandomElement();
		Element z = generator.duplicate();
		z = z.powZn(a);
		Element q = a.duplicate();
		q = q.div(b);
		Element c = generator.duplicate();
		c = c.powZn(q);
		System.out.println("z ="+z);
		System.out.println("c ="+c);
		
		c.powZn(b);
		
		System.out.println("c ="+c);

	}
	
}
