package tests.eit.nl.utwente.sdm;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import eit.nl.utwente.sdm.DBUtils;
import eit.nl.utwente.sdm.HealthClub;
import eit.nl.utwente.sdm.HealthRecord;

public class TestHealthData {

	/**
	 * This method assumes no concurent access
	 */
	@Test
	public void testInsertPatient(){
		long initialNoHealthClubs = DBUtils.getHealthRecords().size();
		HealthRecord test = new HealthRecord(1, 1, 1, 3, HealthRecord.USER_INSERTED_HR, "23", "1-1-2001", "21", "a1 and a2");
		try {
			test.persist();
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
