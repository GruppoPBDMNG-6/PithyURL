package GruppoPBDMNG_6.PithyURL;
 
import com.google.gson.Gson;

import GruppoPBDMNG_6.PithyURL.DataAccess.DAO;
import GruppoPBDMNG_6.PithyURL.DataAccess.ServerResponses;
import GruppoPBDMNG_6.PithyURL.Entities.*;
import GruppoPBDMNG_6.PithyURL.Exceptions.*;
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
		            		LsUrlServer url = db.visitLsUrl(request.params(":short"));
		                	response.redirect(url.getLongUrl());
	
	                	} catch (ShortUrlNotFoundException e){
	                		response.redirect("404.html");
	                	}
	                	
	                	System.out.println("");
                	}
                	return null;
                	
                });
        
        get("/preview/:short", "application/json", (request, response)
                -> {
                	System.out.println("R - Richiesta preview fatta da : "+ request.ip());
                	LsUrlServer url;
                	
                	try{
	            		url = db.getLsUrl(request.params(":short"));
                	} catch (ShortUrlNotFoundException e){
                		response.redirect("404.html");
                		url = null;
                	}
              
                	System.out.println("");
                	return url;
                	
                	//manderà la richiesta alla pagina non ancora fatta 
                	//e all ok verra chiamato il get di sopra
                	
                }, new JsonTransformer());
        
        get("/#/:serverpage", "application/json", (request, response)
                -> {
                	System.out.println("PR - Richiesta pagina : "+ request.params(":serverpage"));
                	System.out.println("");
            		return null;
                });
        
        get("/404.html", (request, response) -> {
			return null;
		});
        
        //inserire operazioni da fare su richiesta del client post o get
        post(API_CONTEXT + "/lsurl", "application/json", (request, response) -> {
        	System.out.println("W - Richiesta fatta da : "+ request.ip());
        	LsUrlClient url;
        	try{
        		url = db.createNewLsUrl(request.body());
        	}catch (BadURLFormatException e){
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