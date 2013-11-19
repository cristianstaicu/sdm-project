package tests.eit.nl.utwente.sdm;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import eit.nl.utwente.sdm.DBUtils;
import eit.nl.utwente.sdm.Insurance;
import eit.nl.utwente.sdm.Patient;

public class TestInsurance {
	
	/**
	 * This method assumes no concurent access
	 */
	@Test
	public void testInsertPatient(){
		long initialNoInsurances = DBUtils.getInsurances().size();
		Insurance test = new Insurance("AEG", "Amsterdam", "Haaksbergerstraat 112");
		try {
			test.persist();
			Assert.assertNotSame(test.getId(), DBUtils.ID_NOT_SET);
			
			long newNoInsurances = DBUtils.getInsurances().size();
			Assert.assertEquals(initialNoInsurances + 1, newNoInsurances);
			test.delete();
			newNoInsurances = DBUtils.getInsurances().size();
			Assert.assertEquals(initialNoInsurances, newNoInsurances);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
