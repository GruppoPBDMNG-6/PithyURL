package GruppoPBDMNG_6.PithyURL.Util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import spark.Spark;
import static spark.Spark.*;
import GruppoPBDMNG_6.PithyURL.Util.CookiesHandler;

@Ignore
public class CookiesHandlerTest {

	//Attributi per creare un server di test
    private static final int PORT = 8080;
    private static final String DEFAULT_HOST_URL = "http://0.0.0.0:"+PORT+"";
    private CloseableHttpClient httpClient;
    
    //Attributi per il test
    private String[] shortsVisited = {"xDvB68JK", "BJDHua67", "xDvB68JK", "ahsjKFj6"};
    
    private boolean[] outputsExpected = {true, true, false, true};
    private String[] cookieStatusExpected = {"xDvB68JK", "xDvB68JK-BJDHua67", "xDvB68JK-BJDHua67", "xDvB68JK-BJDHua67-ahsjKFj6"};
    
    private boolean currentOutput;
    private String currentCookieStatus;

    @Before
    public void setUp() throws Exception {
        setPort(PORT);
    	
    	httpClient = HttpClientBuilder.create().build();
        
        post("/:short", (request, response) -> {
        	currentOutput = CookiesHandler.handleVisit(request,response);
            return "";
        });
        
        post("/util/getCookie", (request, response) -> {
        	currentCookieStatus = request.cookie("pithyurl_visited");
            return "";
        });
        
    }

	@After
	public void tearDown() throws Exception {
		Spark.stop();
	}

	@Test
	public void testHandleVisit() {
		
		for(int i = 0 ; i < shortsVisited.length; i++){
			
			request("POST","/"+shortsVisited[i]);
			assertEquals(currentOutput,outputsExpected[i]);
			
			request("POST","/util/getCookie");
			assertEquals(currentCookieStatus,cookieStatusExpected[i]);
			
		}
		
	}
    
	private void request(String method, String path) {
		try {
			URL url = new URL(DEFAULT_HOST_URL + path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			connection.connect();
		} catch (IOException e) {
			e.printStackTrace();
			fail("Sending request failed: " + e.getMessage());
		}
	}

}
