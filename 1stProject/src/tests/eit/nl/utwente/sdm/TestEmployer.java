package tests.eit.nl.utwente.sdm;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import eit.nl.utwente.sdm.DBUtils;
import eit.nl.utwente.sdm.Employer;
import eit.nl.utwente.sdm.HealthClub;

public class TestEmployer {
	
	/**
	 * This method assumes no concurent access
	 */
	@Test
	public void testInsertPatient(){
		long initialNoEmployers = DBUtils.getEmployers().size();
		Employer test = new Employer("Cristian", "Staicu");
		try {
			test.persist();
			Assert.assertNotSame(test.getId(), DBUtils.ID_NOT_SET);
			
			long newNoEmployers = DBUtils.getEmployers().size();
			Assert.assertEquals(initialNoEmployers + 1, newNoEmployers);
			test.delete();
			newNoEmployers = DBUtils.getEmployers().size();
			Assert.assertEquals(initialNoEmployers, newNoEmployers);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
