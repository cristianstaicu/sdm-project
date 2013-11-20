package tests.eit.nl.utwente.sdm;

import it.unisa.dia.gas.jpbc.CurveGenerator;
import it.unisa.dia.gas.jpbc.CurveParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

public class AbstractBilinearMapTest {

	protected Element generator;
	protected Element egg;
	protected Pairing pairing;
	protected Field<Element> G0;
	protected Field<Element> G1;
	protected Field<Element> Zr;

	@SuppressWarnings("unchecked")
	public AbstractBilinearMapTest() {
		int rbits = 160;
		int qbits = 512;

		CurveGenerator curveGenerator = new TypeACurveGenerator(rbits, qbits);
		CurveParameters params = curveGenerator.generate();

		pairing = PairingFactory.getPairing(params);
		G0 = pairing.getG1();
		G1 = pairing.getGT();
		Zr = pairing.getZr();

		do {
			generator = G0.newRandomElement();
			egg = pairing.pairing(generator, generator);
		} while (egg.equals(G1.newOneElement()));

	}
	
}
