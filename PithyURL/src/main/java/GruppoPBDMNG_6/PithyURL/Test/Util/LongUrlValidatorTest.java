package GruppoPBDMNG_6.PithyURL.Test.Util;

import static org.junit.Assert.assertTrue;

import GruppoPBDMNG_6.PithyURL.Util.LongUrlValidator;
import GruppoPBDMNG_6.PithyURL.Util.ShortUrlValidator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LongUrlValidatorTest {



	
	private String[] s = {"http://www.prova1.com", "www.prova2.com", "prova3.com", "prova4", " "};
	private boolean[] result = {true, true, true, true, false};
	LongUrlValidator [] test = new LongUrlValidator[s.length];
	
	@Before
	public void setUp() throws Exception {
		for(int i = 0; i<s.length; i++){
			
			test[i] = new LongUrlValidator(s[i]);
			
		}
	}

	@After
	public void tearDown() throws Exception {
		for(int i = 0; i<s.length; i++){
			
			test[i] = null;
			
		}
		test = null;
	}

	@SuppressWarnings("static-access")
	@Test
	public void testValidate() throws Exception{
		for(int i = 0; i<s.length; i++){
			
			assertTrue("Caso di test: " + i, test[i].validate()==result[i]);
			
		}
	}
	
}
