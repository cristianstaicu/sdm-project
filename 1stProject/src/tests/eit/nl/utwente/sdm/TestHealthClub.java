package tests.eit.nl.utwente.sdm;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import eit.nl.utwente.sdm.DBUtils;
import eit.nl.utwente.sdm.HealthClub;

public class TestHealthClub {
	
	/**
	 * This method assumes no concurent access
	 */
	@Test
	public void testInsertPatient(){
		long initialNoHealthClubs = DBUtils.getHealthClubs().size();
		HealthClub test = new HealthClub("Premium Gym", "Enschede", "Haaksbergerstraat 30");
		try {
			test.persist();
			Assert.assertNotEquals(test.getId(), DBUtils.ID_NOT_SET);
			
			long newNoHealthClub = DBUtils.getHealthClubs().size();
			Assert.assertEquals(initialNoHealthClubs + 1, newNoHealthClub);
			test.delete();
			newNoHealthClub = DBUtils.getHealthClubs().size();
			Assert.assertEquals(initialNoHealthClubs, newNoHealthClub);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
