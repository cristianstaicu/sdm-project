package eit.nl.utwente.sdm;
import it.unisa.dia.gas.jpbc.CurveParameters;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.jpbc.CurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;

public class TrustedAuthority {
	
	public TrustedAuthority(){
		
	}
	//still working on it
	private void setup(){
		Pairing pairing = PairingFactory.getPairing("curve.properties");
	}
	
}
