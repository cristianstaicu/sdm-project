package eit.nl.utwente.sdm;
import java.math.BigInteger;

import it.unisa.dia.gas.jpbc.CurveParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.jpbc.CurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

public class TrustedAuthority {
	
	public TrustedAuthority(){
		
	}
	//still working on it
	public static void main(String[] args) {
		TrustedAuthority ta = new TrustedAuthority();
		ta.createGenerator();
	}

	private void setup(){
	}
	
	private void createGenerator(){
		int rbits = 160;
		int qbits = 512;
		CurveGenerator curveGenerator = new TypeACurveGenerator(rbits, qbits);
		CurveParameters params = curveGenerator.generate();
		Pairing pairing = PairingFactory.getPairing(params);
		Field G1 = pairing.getG1();
		Field GT = pairing.getGT();
		Field Zr = pairing.getZr();
		
		/*Suppose you want to compute the following pairing out = e(in1, in2),
		where in1 must be in the group G1, in2 must be in the group G2 and 
		out will lie in the target group GT. */
		
		Element in1 = pairing.getG1().newElement(new BigInteger("3"));
		Element in2 = pairing.getG1().newElement(new BigInteger("8"));
		
		Element out = pairing.pairing(in1, in2);
		System.out.println(out);
//		System.out.println(out.pow(new BigInteger("3")));
		
		Element in3 = pairing.getG1().newElement(new BigInteger("2"));
		Element in4 = pairing.getG1().newElement(new BigInteger("27"));
		
		Element out1 = pairing.pairing(in3, in4);
		System.out.println(out1);
	}
	
}
