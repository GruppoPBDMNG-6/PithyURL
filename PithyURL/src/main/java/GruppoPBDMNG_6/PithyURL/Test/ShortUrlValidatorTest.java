package GruppoPBDMNG_6.PithyURL.Test;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GruppoPBDMNG_6.PithyURL.Util.ShortUrlValidator;

public class ShortUrlValidatorTest {
	
	ShortUrlValidator test;
	
	private String[] s = {"1234324", "ABqdBFbS", "134jdje4A", "%sjddh124", " "};
	private boolean[] result = {true, true, true, false, false};
	
	@Before
	public void setUp() throws Exception {
		test = new ShortUrlValidator();
	}

	@After
	public void tearDown() throws Exception {
		test = null;
	}

	@SuppressWarnings("static-access")
	@Test
	public void testValidate(){
		for(int i = 0; i<s.length; i++){
			assertTrue("Caso di test: " + i, test.validate(s[i])==result[i]);
		}
	}
}
