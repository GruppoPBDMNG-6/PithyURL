package GruppoPBDMNG_6.PithyURL;
 
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import GruppoPBDMNG_6.PithyURL.DataAccess.DAO;
import GruppoPBDMNG_6.PithyURL.Entities.*;
import GruppoPBDMNG_6.PithyURL.Exceptions.*;
import GruppoPBDMNG_6.PithyURL.Util.CookiesHandler;
import GruppoPBDMNG_6.PithyURL.Util.JsonTransformer;
import static spark.Spark.*;
 
public class Resource {
 
    private static final String API_CONTEXT = "/api/v1";
 
    private final DAO db;
 
    public Resource(DAO db) {
        this.db = db;
        setupEndpoints();
    }
 
    private void setupEndpoints() {
   
    	// eseguita all avvio
        get("/:short", "application/json", (request, response)
                -> {

                	if (!request.params(":short").equals("favicon.ico")){
                		
                		System.out.println("R - Richiesta fatta da : "+ request.ip());
                		
                		try{
                			LsUrlServer url = db.visitLsUrl(request.params(":short"), 
                					CookiesHandler.handleVisit(request, response));
		                	response.redirect(url.getLongUrl());
	
	                	} catch (ShortUrlNotFoundException e){
	                		response.redirect("404.html");
	                	}
	                	
	                	System.out.println("");
                	}
                	return null;
                	
                });
        
        
        get("/404.html", (request, response) -> {
			return null;
		});
        
        //inserire operazioni da fare su richiesta del client post o get
        post(API_CONTEXT + "/lsurl", "application/json", (request, response) -> {
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
				}
            System.out.println("");
            return url;
        }, new JsonTransformer());
        
        post(API_CONTEXT + "/inspectUrl", "application/json", (request, response) -> {
        	
        		
    		System.out.println("RS - Richiesta fatta da : "+ request.ip());
    		LsUrlServer sUrl ;
    		
    		try{
    			LsUrlClient cUrl = new Gson().fromJson(request.body(), LsUrlClient.class);
        		sUrl = db.getLsUrl(cUrl.getShortUrl());

        	} catch (ShortUrlNotFoundException e){
        		System.out.println("url non trovato");
        		sUrl=null;
        	}
        	
        	System.out.println("");
        	
        	return sUrl;
        	
        }, new JsonTransformer());
        

    }
 
}