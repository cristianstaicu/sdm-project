package tests.eit.nl.utwente.sdm;

import it.unisa.dia.gas.jpbc.Element;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import eit.nl.utwente.sdm.DBUtils;
import eit.nl.utwente.sdm.HealthClub;
import eit.nl.utwente.sdm.HealthRecord;
import eit.nl.utwente.sdm.Mediator;
import eit.nl.utwente.sdm.Patient;
import eit.nl.utwente.sdm.TrustedAuthority;
import eit.nl.utwente.sdm.datastructures.Ciphertext;
import eit.nl.utwente.sdm.guis.Conductor;

public class TestHealthData {

	/**
	 * This method assumes no concurent access
	 */
	@Test
	public void testInsertPatient(){
		Mediator m = new Mediator();
		TrustedAuthority ta = new TrustedAuthority(m);
		List<Patient> patients = DBUtils.getPatients();
		List<String> attributes = Conductor.getAttributes(patients);
		ta.setup(attributes);

		long initialNoHealthClubs = DBUtils.getHealthRecords().size();
		HealthRecord test = new HealthRecord(1, 1, 1, 3, HealthRecord.USER_INSERTED_HR, "23", "1-1-2001", "21", "patient1");
		try {
			test.persist(ta.getPublicKey());
			Assert.assertNotSame(test.getId(), DBUtils.ID_NOT_SET);
			
			long newNoHealthClub = DBUtils.getHealthRecords().size();
			Assert.assertEquals(initialNoHealthClubs + 1, newNoHealthClub);
			test.delete();
			newNoHealthClub = DBUtils.getHealthRecords().size();
			Assert.assertEquals(initialNoHealthClubs, newNoHealthClub);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
