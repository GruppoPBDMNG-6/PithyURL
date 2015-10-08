package GruppoPBDMNG_6.PithyURL.Util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.google.gson.Gson;
import spark.Spark;
import spark.utils.IOUtils;
import GruppoPBDMNG_6.PithyURL.SparkServer.Bootstrap;

public class CookiesHandlerTest {

	//Attributi per creare un server di test
    private static final String DEFAULT_HOST_URL = "http://0.0.0.0:8080";
    CookieManager cookieManager;
    
    //Attributi per il test
    private String[] shortsVisited = {"xDvB68JK", "BJDHua67", "xDvB68JK", "ahsjKFj6"};
    
    private boolean[] outputsExpected = {true, true, false, true};
    private String[] cookieStatusExpected = {"xDvB68JK", "xDvB68JK-BJDHua67", "xDvB68JK-BJDHua67", "xDvB68JK-BJDHua67-ahsjKFj6"};

    @Before
    public void setUp() throws Exception {
    	Bootstrap.main(null);
    	cookieManager = new CookieManager();
    	CookieHandler.setDefault(cookieManager);
    }

	@After
	public void tearDown() throws Exception {
		Spark.stop();
	}

	@Test
	public void testHandleVisit() {
		
		for(int i = 0 ; i < shortsVisited.length; i++){
			
			TestResponse currentOutput = request("POST","/test/"+shortsVisited[i]);
			assertEquals(outputsExpected[i],currentOutput.json().get("currentOutput"));
			
			TestResponse currentCookieStatus = request("POST","/test/util/getCookie");
			assertEquals(cookieStatusExpected[i],currentCookieStatus.json().get("currentCookieStatus"));
			
		}
		
	}
    
	private TestResponse request(String method, String path) {
		try {
			URL url = new URL(DEFAULT_HOST_URL + path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			connection.connect();
			String body = IOUtils.toString(connection.getInputStream());
			return new TestResponse(connection.getResponseCode(), body);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Sending request failed: " + e.getMessage());
			return null;
		}
	}
	
	private static class TestResponse {

		public final String body;

		public TestResponse(int status, String body) {
			this.body = body;
		}

		@SuppressWarnings("unchecked")
		public Map<String,String> json() {
			return new Gson().fromJson(body, HashMap.class);
		}
	}

}
