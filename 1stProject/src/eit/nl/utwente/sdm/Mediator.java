package eit.nl.utwente.sdm;

import java.util.HashMap;
import java.util.Map;

import eit.nl.utwente.sdm.datastructures.AttributesKey;

public class Mediator {

	private Map<Long, AttributesKey> keys = new HashMap<Long, AttributesKey>();

	public void storeKey(long userId, AttributesKey mediatorsKey) {
		keys .put(userId, mediatorsKey);
	}

}
