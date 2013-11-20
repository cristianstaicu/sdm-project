package eit.nl.utwente.sdm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eit.nl.utwente.sdm.datastructures.AttributesKey;
import eit.nl.utwente.sdm.datastructures.MasterKey;
import eit.nl.utwente.sdm.datastructures.PublicKey;
import eit.nl.utwente.sdm.datastructures.SecretKey;
import it.unisa.dia.gas.jpbc.CurveParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.jpbc.CurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

public class TrustedAuthority {

	private Pairing pairing;
	private Field<Element> G0;
	private Field<Element> G1;
	private Field<Element> Zr;
	private Element generator;
	
	private Element alpha;
	private Element ypsilon;
	private MasterKey masterKey;
	private PublicKey publicKey;
	private Mediator mediator;
	
	public TrustedAuthority(Mediator mediator) {
		this.mediator = mediator;
	}
	
	public void setup(List<String> attributes) {
		createGenerator();
		generateMasterKey(attributes);
		generatePublicKey(attributes);
		mediator.setPublicKey(publicKey);
	}
	
	/**
	 * This method will generate and return the private key for 
	 * the given attributes and return them and will send to 
	 * the mediator his part of the key. (for a single user)
	 */
	public SecretKey generateKey(String userId, List<String> attributes) {
		Map<String, Element> mediatorKeyAttrs = new HashMap<String, Element>();
		Map<String, Element> userKeyAttrs = new HashMap<String, Element>();
		//compute the base component of the secret key
		Element uid = Zr.newRandomElement();
		Element q = alpha.duplicate();
		q = q.sub(uid);
		Element d0 = generator.duplicate();
		d0 = d0.powZn(q);
		
		System.out.println("d0 = " + d0);			//debug

		for (String attribute : attributes){
			Element uj = Zr.newRandomElement();
			Element tj = masterKey.getKeyComponent(attribute);  // tj from master key
			
			Element genDup1 = generator.duplicate();	//avoid generator gets modified
			Element exp = uj.duplicate();				//avoid u_j gets modified
			exp = exp.div(tj);
			
			Element dj1 = genDup1.powZn(exp);
			System.out.println("dj_1 = " + dj1);
			mediatorKeyAttrs.put(attribute, dj1);
			
			Element genDup2 = generator.duplicate();	//avoid generator gets modified
			Element subtr = uid.duplicate();			//avoid u_id gets modified
			subtr = subtr.sub(uj);
			Element dj2 = genDup2.powZn(subtr.div(tj));
			System.out.println("dj_2 = " + dj2);
			userKeyAttrs.put(attribute, dj2);
		}
		AttributesKey mediatorsKey = new AttributesKey(mediatorKeyAttrs);
		mediator.storeKey(userId, mediatorsKey);
		SecretKey usersKey = new SecretKey(d0, userKeyAttrs);
		return usersKey;
	}

	private void generateMasterKey(List<String> attributes) {
		System.out.print("MasterKey = (");
		alpha = Zr.newRandomElement();
		System.out.print("alpha = " + alpha);
		int i = 0;
		Map<String, Element> smallTis = new HashMap<String, Element>(); //master key
		for (String attribute : attributes) {
			Element ti = Zr.newRandomElement();
			smallTis.put(attribute, ti);
			System.out.print(", t_" + i++ + " = " + ti);
		}
		System.out.println(")");
		this.masterKey = new MasterKey(alpha, smallTis);
	}
	
	private void generatePublicKey(List<String> attributes) {
		System.out.print("PublicKey = (");
		ypsilon = pairing.pairing(generator, generator);
		ypsilon = ypsilon.powZn(alpha);
		System.out.print("y = " + alpha);
		int i = 0;
		Map<String, Element> bigTis = new HashMap<String, Element>();
		for (String attribute : attributes) {
			Element bigTi = generator.duplicate();
			bigTi = bigTi.powZn(masterKey.getKeyComponent(attribute));
			bigTis.put(attribute, bigTi);
			System.out.print(", T_" + i++ + " = " + bigTi);
		}
		System.out.println(")");
		this.publicKey = new  PublicKey(pairing, G0, G1, Zr, generator, ypsilon, bigTis);
	}

	@SuppressWarnings("unchecked")
	private void createGenerator() {
		int rbits = 160;
		int qbits = 512;

		CurveGenerator curveGenerator = new TypeACurveGenerator(rbits, qbits);
		CurveParameters params = curveGenerator.generate();

		pairing = PairingFactory.getPairing(params);
		G0 = pairing.getG1();
		G1 = pairing.getGT();
		Zr = pairing.getZr();
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
		Element egg;
		do {
			generator = G0.newRandomElement();
			egg = pairing.pairing(generator, generator);
		} while (egg.equals(G1.newOneElement()));

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
		out = egg.duplicate();
		System.out.println("e(g, g) = " + out);
		out.powZn(z);
		System.out.println("e(g, g)^z = " + out);

	}
	public PublicKey getPublicKey() {
		return publicKey;
	}
	public MasterKey getMasterKey() {
		return masterKey;
	}
	
	public void distributeKeys(List<Patient> patients, List<Doctor> doctors,
			List<Employer> employers, List<Insurance> insurances) {
		for (Patient p : patients) {
			List<String> attributes = new ArrayList<String>();
			attributes.add(p.getId() + "");
			SecretKey key = generateKey("P" + p.getId(), attributes);
			p.setKey(key);
		}
		
		for (Doctor d : doctors) {
			List<String> attributes = new ArrayList<String>();
			for (Patient p : patients) {
				if (p.getIdDoc() == d.getId())
					attributes.add(p.getId() + "'s Doc");
			}
			SecretKey key = generateKey("D" + d.getId(), attributes);
			d.setKey(key);
		}
		
		for (Insurance i : insurances) {
			List<String> attributes = new ArrayList<String>();
			for (Patient p : patients) {
				if (p.getIdIns() == i.getId())
					attributes.add(p.getId() + "'s Insurance");
			}
			SecretKey key = generateKey("I" + i.getId(), attributes);
			i.setKey(key);
		}
		
		for (Employer e : employers) {
			List<String> attributes = new ArrayList<String>();
			for (Patient p : patients) {
				if (p.getIdEmpl() == e.getId())
					attributes.add(p.getId() + "'s Employer");
			}
			SecretKey key = generateKey("E" + e.getId(), attributes);
			e.setKey(key);
		}
	}

}
