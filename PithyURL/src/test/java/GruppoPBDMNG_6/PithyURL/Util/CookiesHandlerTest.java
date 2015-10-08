package GruppoPBDMNG_6.PithyURL.Util;

import static org.junit.Assert.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spark.Spark;
import static spark.Spark.*;
import GruppoPBDMNG_6.PithyURL.Util.CookiesHandler;

public class CookiesHandlerTest {

	//Attributi per creare un server di test
    private static final int PORT = 8080;
    private static final String DEFAULT_HOST_URL = "http://127.0.0.1:"+PORT+"";
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
			
			httpPost("/"+shortsVisited[i]);
			assertEquals(currentOutput,outputsExpected[i]);
			
			httpPost("/util/getCookie");
			assertEquals(currentCookieStatus,cookieStatusExpected[i]);
			
		}
		
	}
	
    private void httpPost(String relativePath) {
        HttpPost request = new HttpPost(DEFAULT_HOST_URL + relativePath);
        try {
        	HttpResponse response = httpClient.execute(request);
        	EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            fail(e.toString());
        }
    }

}
