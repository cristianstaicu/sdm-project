package tests.eit.nl.utwente.sdm;

import org.junit.Assert;
import org.junit.Test;

import eit.nl.utwente.sdm.GlobalProperties;

public class TestPropertiesFile {

	@Test
	public void testBasic() {
		Assert.assertNotNull("Properties file does not contain db info!",
				GlobalProperties.getInstance().getProperty("DB_CONNECTION"));
	}

}
