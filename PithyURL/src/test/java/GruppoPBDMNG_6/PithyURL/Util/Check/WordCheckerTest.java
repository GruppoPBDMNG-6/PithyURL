package GruppoPBDMNG_6.PithyURL.Util.Check;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GruppoPBDMNG_6.PithyURL.Util.Check.WordChecker;

public class WordCheckerTest {

	WordChecker test;
	private String[] Words = { 
			"reudig", // tedesco
			"fuck", // inglese
			"puta", // spagnolo
			"paneskella", // finlandese
			"pouffiasse", // francese
			"chutiya", // indiano
			"faszfejeket", // ungherese
			"caciocappella", // italiano
			"gadverdamme", // olandese
			"xochota", // portoghese
			"discofitta", // svedese
			"computer", // parola accettata
	};

	private boolean[] result = { true, true, true, true, true, true, true,
			true, true, true, true, false };

	@Before
	public void setUp() throws Exception {
		test = new WordChecker();
	}

	@After
	public void tearDown() throws Exception {
		test = null;
	}

	@Test
	public void testIsUndesiderable() {
			for(int i = 0; i<Words.length; i++){
				assertTrue("Caso di test " + i, test.isUndesirable(Words[i]) == result[i]);
			}
	}

}
