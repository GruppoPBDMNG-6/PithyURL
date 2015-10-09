package GruppoPBDMNG_6.PithyURL.SparkServer;
 
import java.io.IOException;

import com.google.gson.Gson;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.mongodb.util.JSON;

import GruppoPBDMNG_6.PithyURL.DataAccess.IDAO;
import GruppoPBDMNG_6.PithyURL.SparkServer.Entities.*;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.*;
import GruppoPBDMNG_6.PithyURL.Util.CookiesHandler;
import GruppoPBDMNG_6.PithyURL.Util.JsonUtil.JsonTransformer;
import GruppoPBDMNG_6.PithyURL.Util.GeoIPLocation.GeoLocationByIP;
import static spark.Spark.*;
import static GruppoPBDMNG_6.PithyURL.Util.JsonUtil.JsonUtil.*;
 
public class Resource {
 
    private final IDAO db;
    private boolean test;
 
    public Resource(IDAO db, boolean test) {
        this.db = db;
        this.test = test;
        setupEndpoints();
    }
    
    private String getGeoLocation(String ip){
    	
    	String location = "NaN";
    	
    	try {
			GeoLocationByIP geoLocator = new GeoLocationByIP();
			location = geoLocator.getCountryFromIP(ip);
		} catch (IOException | GeoIp2Exception e) {
			location = "NaN";
		}
    	
    	return location;
    	
    }
 
    private void setupEndpoints() {
   
    	// eseguita all avvio
        get(API.VISIT, "application/json", (request, response)
                -> {

                	if (!request.params(":short").equals("favicon.ico")){
                		
                		System.out.println("R - Richiesta fatta da : "+ request.ip());
                		
                		try{
                			LsUrlServer url = db.visitLsUrl(request.params(":short"), 
                					CookiesHandler.handleVisit(request, response),
                					getGeoLocation(request.ip()));
		                	response.redirect(url.getLongUrl());
	
	                	} catch (ShortUrlNotFoundException e){
	                		response.redirect("404.html");
	                	}
	                	
	                	System.out.println("");
                	}
                	return null;
                	
                });
        
        
        get(API.PAGE_NOT_FOUND, (request, response) -> {
			return null;
		});
        
        //inserire operazioni da fare su richiesta del client post o get
        post(API.API_CONTEXT + API.NEW_URL, "application/json", (request, response) -> {
        	System.out.println("W - Richiesta fatta da : "+ request.ip());
        	LsUrlClient url;
        	try {
					url = db.createNewLsUrl(request.body());
				} catch (UndesirableWordException e) {
					System.out.println(e.getMessage());
					response.status(500);
					url = null;
				} catch ( BadURLFormatException e){
					System.out.println(e.getMessage());
					response.status(502);
					url = null;
				} catch (ShortUrlDuplicatedException e){
					System.out.println(e.getMessage());
					response.status(503);
					url = null;
				}catch(ShortURLMaxLenghtReachedException e){
					System.out.println(e.getMessage());
					response.status(504);
					url = null;
				}
            System.out.println("");
            return url;
        }, new JsonTransformer());
        
        post(API.API_CONTEXT + API.INSPECT_URL, "application/json", (request, response) -> {
        	
        		
    		System.out.println("RS - Richiesta fatta da : "+ request.ip());
    		LsUrlServer sUrl ;
    		
    		try{
    			LsUrlClient cUrl = new Gson().fromJson(request.body(), LsUrlClient.class);
        		sUrl = db.getLsUrl(cUrl.getShortUrl());

        	} catch (ShortUrlNotFoundException e){
        		System.out.println(e.getMessage());
				response.status(501);
				sUrl = null;
        	}
        	
        	System.out.println("");
        	
        	return sUrl;
        	
        }, new JsonTransformer());
        
        //usati da CoockiesHandlerTest.java
        if(test){
        	
	        post(API.TES_VISIT, (request, response) -> {
	        	return JSON.parse("{'currentOutput' : "+CookiesHandler.handleVisit(request,response)+"}");
	        }, json());
	        
	        post(API.TEST_GET_COOKIE, (request, response) -> {
	        	return JSON.parse("{'currentCookieStatus' : '"+request.cookie("pithyurl_visited")+"'}");
	        }, json());
	        
        }
        
    }
 
}