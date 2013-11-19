package tests.eit.nl.utwente.sdm;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import eit.nl.utwente.sdm.DBUtils;
import eit.nl.utwente.sdm.Doctor;
import eit.nl.utwente.sdm.Employer;
import eit.nl.utwente.sdm.HealthClub;

public class TestDoctor {
	
	/**
	 * This method assumes no concurent access
	 */
	@Test
	public void testInsertPatient(){
		long initialNoDoctors = DBUtils.getDoctors().size();
		Doctor test = new Doctor("Cristian", "Staicu", "EIT", 1, 2);
		try {
			test.persist();
			Assert.assertNotSame(test.getId(), DBUtils.ID_NOT_SET);
			
			long newNoDoctors = DBUtils.getDoctors().size();
			Assert.assertEquals(initialNoDoctors + 1, newNoDoctors);
			test.delete();
			newNoDoctors = DBUtils.getDoctors().size();
			Assert.assertEquals(initialNoDoctors, newNoDoctors);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
