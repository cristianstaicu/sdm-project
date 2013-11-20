package eit.nl.utwente.sdm.datastructures;

import java.util.Map;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;

public class PublicKey extends AttributesKey {

	public final Element generator;
	public final Element ypsilon;
	public final Field<Element> G0;
	public final Field<Element> G1;
	public final Field<Element> Zr;
	public final Pairing bilinearMap;
	public final Element egg;

	public PublicKey(Pairing bilinearMap, Field<Element> G0, Field<Element> G1,
			Field<Element> Zr, Element g, Element y,
			Map<String, Element> attrList) {
		super(attrList);
		this.bilinearMap = bilinearMap;
		this.G0 = G0;
		this.G1 = G1;
		this.Zr = Zr;
		this.generator = g;
		this.ypsilon = y;
		egg = bilinearMap.pairing(generator, generator);
	}

}
