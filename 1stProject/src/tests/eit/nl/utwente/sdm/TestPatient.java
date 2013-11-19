package tests.eit.nl.utwente.sdm;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import eit.nl.utwente.sdm.DBUtils;
import eit.nl.utwente.sdm.Patient;

public class TestPatient {
	
	/**
	 * This method assumes no concurent access
	 */
	@Test
	public void testInsertPatient(){
		long initialNoPatients = DBUtils.getPatients().size();
		Date birthday = new Date();
		Patient test = new Patient(213, "riccardo", "borto", birthday, "Haaksbergerstraat 82", 42, 32, 451);
		try {
			test.persist();
			Assert.assertNotSame(test.getId(), DBUtils.ID_NOT_SET);
			System.out.println(test.getId());
			long newNoPatients = DBUtils.getPatients().size();
			Assert.assertEquals(initialNoPatients + 1, newNoPatients);
			test.delete();
			newNoPatients = DBUtils.getPatients().size();
			Assert.assertEquals(initialNoPatients, newNoPatients);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
