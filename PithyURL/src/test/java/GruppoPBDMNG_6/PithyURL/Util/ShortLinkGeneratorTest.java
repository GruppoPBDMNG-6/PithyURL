package GruppoPBDMNG_6.PithyURL.Util;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spark.Spark;
import GruppoPBDMNG_6.PithyURL.DataAccess.MongoDBDAO;
import GruppoPBDMNG_6.PithyURL.SparkServer.Bootstrap;
import GruppoPBDMNG_6.PithyURL.Util.ShortLinkGenerator;

public class ShortLinkGeneratorTest {
	
	ShortLinkGenerator test;
	private static final String TEST_REGEX = "[-a-zA-Z0-9]*[-a-zA-Z0-9]";
	
	@Before
	public void setUp() throws Exception {
		test = new ShortLinkGenerator();
		ShortLinkGenerator.db = new MongoDBDAO(Bootstrap.mongo());
	}

	@After
	public void tearDown() throws Exception {
		test = null;
		Spark.stop();
	}

	@Test
	public void testGeneraLink() {
		for (int i = 0; i<100; i++){
			assertTrue("Caso di test " + i, test.generaLink().matches(TEST_REGEX));
		}
	}
	
}
