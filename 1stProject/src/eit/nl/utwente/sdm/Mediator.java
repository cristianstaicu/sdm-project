package eit.nl.utwente.sdm;

import it.unisa.dia.gas.jpbc.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eit.nl.utwente.sdm.datastructures.AttributesKey;
import eit.nl.utwente.sdm.datastructures.Ciphertext;
import eit.nl.utwente.sdm.datastructures.PublicKey;

public class Mediator {

	private Map<Long, AttributesKey> keys = new HashMap<Long, AttributesKey>();
	private List<String> revokedAttrs = new ArrayList<String>();
	private List<Long> banedUsers = new ArrayList<Long>();
	private PublicKey publicKey;

	public void storeKey(long userId, AttributesKey mediatorsKey) {
		keys .put(userId, mediatorsKey);
	}
	
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	
	public Element mDecrypt(Ciphertext ciphertext, List<String> attributes, long userId) {
		for (String attribute : attributes)
			if  (revokedAttrs.contains(attribute)) {
				return null;
			}
		if (banedUsers.contains(userId)) {
			return null;
		}
		AttributesKey medKeyForUser = keys.get(userId);
		Element result = publicKey.G1.newOneElement();
		for (String attribute : attributes) {
			if (!medKeyForUser.containsComponent(attribute)) {
				return null;
			}
			Element medComp = medKeyForUser.getKeyComponent(attribute);
			Element pubComp = publicKey.getKeyComponent(attribute);
			Element el = publicKey.bilinearMap.pairing(pubComp, medComp);
			result = result.mul(el);
		}
		return result;
	}
	
	public void revokeAttribute(String attr) {
		revokedAttrs .add(attr);
	}
	
	public void banUser(long userId) {
		banedUsers.add(userId);
	}

}
