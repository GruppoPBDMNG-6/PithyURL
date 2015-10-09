package GruppoPBDMNG_6.PithyURL.DataAccess;

import static org.junit.Assert.*;

import java.io.PrintStream;
import java.io.OutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spark.Spark;
import GruppoPBDMNG_6.PithyURL.SparkServer.Bootstrap;
import GruppoPBDMNG_6.PithyURL.SparkServer.Entities.LsUrlClient;
import GruppoPBDMNG_6.PithyURL.SparkServer.Entities.LsUrlServer;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.BadURLFormatException;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.ShortUrlDuplicatedException;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.UndesirableWordException;
import GruppoPBDMNG_6.PithyURL.Util.LongUrlValidator;

import com.mongodb.DB;

public class MongoDBDAOTest {
	
	PrintStream originalStream;
	PrintStream dummyStream;
	
	private DB db;
	private MongoDBDAO mDAO;
	
	//testCreateNewLsUrl
	private String[] longUrls = {"www.google.it","http://www.facebook.com","http://www.gmail.com"
			,"www.facebook.com","()()","www.google.it"};
	private String[] customUrlsTest = {"google","facebook","gmail","facebook","uzzone","porno"};
	
	private String expectedBadURLFormatException = "()()";
	private String expectedShortUrlDuplicatedException = "facebook";
	private String expectedUndesirableWordException = "porno";
	
	private String longUrlForDuplicateTest;
	
	//testVisitUrl
	private String[] locations = {"CZ", "CZ", "CZ", "GR"};
	private boolean[] firstVisit = {true,true,false,true};
	
	private int expectedTotalVisits = 4;
	private int expectedUniqueVisits = 3;
	private int expectedCZVisits = 3;
	private int expectedGRVisits = 1;
	
    @Before
    public void setUp() throws Exception {
    	savePrintContext();
    	disabledPrintState(true);
    	db = Bootstrap.mongo();
    	mDAO = new MongoDBDAO(db);
    }

	@After
	public void tearDown() throws Exception {
		db.dropDatabase();
		disabledPrintState(false);
		Spark.stop();
	}

	//testa anche getLsUrl
	@Test
	public void testCreateNewLsUrl() {
		LsUrlClient url;
		LsUrlServer expectedUrl;
		
		for (int i = 0; i < longUrls.length; i++) {
			String inputRandom = "{'long':'"+longUrls[i]+"','short':'not_set','custom':false}";
			String inputCustom = "{'long':'"+longUrls[i]+"','short':'"+customUrlsTest[i]+"','custom':true}";

			try {
				
				//test random url
				url = mDAO.createNewLsUrl(inputRandom);
				expectedUrl = mDAO.getLsUrl(url.getShortUrl());
				LongUrlValidator validator = new LongUrlValidator(longUrls[i]);
				assertEquals(expectedUrl.getLongUrl(),validator.getFixedUrl());	
				
				//controllo sull inserimento di uno stesso long url nella sezione non custom
				//per stessi long url deve restituire stessi short url quando non custom
				if(i==1){longUrlForDuplicateTest = url.getShortUrl();}
				if(i==3){assertEquals(longUrlForDuplicateTest,url.getShortUrl());}
				
				//test custom url
				url = mDAO.createNewLsUrl(inputCustom);
				expectedUrl = mDAO.getLsUrl(url.getShortUrl());
				validator = new LongUrlValidator(longUrls[i]);
				assertEquals(expectedUrl.getLongUrl(),validator.getFixedUrl());
				
			} catch (BadURLFormatException e) {
				assertEquals(longUrls[i],expectedBadURLFormatException);
			} catch (ShortUrlDuplicatedException e) {
				assertEquals(customUrlsTest[i],expectedShortUrlDuplicatedException);
			} catch (UndesirableWordException e) {
				assertEquals(customUrlsTest[i],expectedUndesirableWordException);
			}

		}
		
	}
	
	@Test
	public void testVisitLsUrl() {
		
		LsUrlClient urlToVisit;
		try {
			
			urlToVisit = mDAO.createNewLsUrl("{'long':'www.google.com','short':'testVisits','custom':true}");
			for (int i = 0; i < locations.length; i++) {
				mDAO.visitLsUrl(urlToVisit.getShortUrl(), firstVisit[i], locations[i]);
			}
			
			LsUrlServer url = mDAO.getLsUrl(urlToVisit.getShortUrl());
			
			assertEquals(expectedTotalVisits,url.getTotVisits());
			assertEquals(expectedUniqueVisits,url.getUniqueVisits());
			assertEquals(expectedCZVisits,url.getCountries().get(0).getVisits());
			assertEquals(expectedGRVisits,url.getCountries().get(1).getVisits());
			
		} catch (BadURLFormatException | ShortUrlDuplicatedException
				| UndesirableWordException e) {
			fail(e.toString());
		}
		
	}

	private void savePrintContext(){
		originalStream = System.out;
    	dummyStream    = new PrintStream(new OutputStream(){
    	    public void write(int b) {
    	        //NO-OP
    	    }
    	});
	}
	
	private void disabledPrintState(boolean enabled){
		
		if(enabled){
			System.setOut(dummyStream);
		}else{
			System.setOut(originalStream);
		}

	}
	
}
