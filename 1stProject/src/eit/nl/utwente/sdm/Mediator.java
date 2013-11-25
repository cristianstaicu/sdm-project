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

	private Map<String, AttributesKey> keys = new HashMap<String, AttributesKey>();
	private List<String> revokedAttrs = new ArrayList<String>();
	private List<String> banedUsers = new ArrayList<String>();
	private PublicKey publicKey;

	public void storeKey(String userId, AttributesKey mediatorsKey) {
		keys .put(userId, mediatorsKey);
	}
	
	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}
	
	public Element mDecrypt(Ciphertext ciphertext, List<String> attributes, String userId) {
		for (String attribute : attributes)
			if  (revokedAttrs.contains(attribute)) {
				return null;
			}
		if (banedUsers.contains(userId)) {
			return null;
		}
		if (!keys.containsKey(userId)) {
			throw new RuntimeException("Mediator does not have key for user " + userId + "!" );
		}
		AttributesKey medKeyForUser = keys.get(userId);
		Element result = publicKey.G1.newOneElement();
		for (String attribute : attributes) {
			if (!medKeyForUser.containsComponent(attribute)) {
				return null;
			}
			Element medComp = medKeyForUser.getKeyComponent(attribute);
			if (medComp == null){
				throw new RuntimeException("Mediator does not have key component for attribute" + attribute + "!" );
			}
			Element ctComp = ciphertext.getComponents().get(attribute);
			if (ctComp == null){
				throw new RuntimeException("Ciphertext does not contain component for attribute " + attribute + "!" );
			}
			Element el = publicKey.bilinearMap.pairing(ctComp, medComp);
			result = result.mul(el);
		}
		return result;
	}
	
	public void revokeAttribute(String attr) {
		revokedAttrs .add(attr);
	}
	
	public void banUser(String userId) {
		banedUsers.add(userId);
	}

}
