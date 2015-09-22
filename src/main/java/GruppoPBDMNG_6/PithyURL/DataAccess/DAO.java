package GruppoPBDMNG_6.PithyURL.DataAccess;
 
import java.util.Date;

import GruppoPBDMNG_6.PithyURL.Entities.LsUrlClient;
import GruppoPBDMNG_6.PithyURL.Entities.LsUrlServer;
import GruppoPBDMNG_6.PithyURL.Exceptions.BadURLFormatException;
import GruppoPBDMNG_6.PithyURL.Exceptions.ShortUrlNotFoundException;
import GruppoPBDMNG_6.PithyURL.Util.LongUrlValidator;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
 
public class DAO {
 
    private final DB db;
    private final DBCollection collection;
 
    public DAO(DB db) {
        this.db = db;
        this.collection = db.getCollection("lsurl");
    }
 
    //funzioni di scrittura - lettura db
    
    public LsUrlClient createNewLsUrl(String body) throws BadURLFormatException{
        LsUrlClient url = new Gson().fromJson(body, LsUrlClient.class);
        String shortUrl = url.getShortUrl();
        System.out.println("W - Long url : " + url.getLongUrl());
        LongUrlValidator validator = new LongUrlValidator(url.getLongUrl());
        
        
        if(validator.validate()){
        	System.out.println("W - Long url fixed : " + validator.getFixedUrl());
            System.out.println("W - Short url : " + url.getShortUrl());
        	collection.insert(new BasicDBObject("long", validator.getFixedUrl()).append("short", shortUrl)
        			.append("tot_visits", 0).append("create_date", new Date()));
        } else {
            System.out.println("W - Long url non valido");
        	throw new BadURLFormatException(url.getLongUrl() + "non valido");
        }
    
        return url;
    }
    
    public LsUrlServer visitLsUrl(String shortUrl) throws ShortUrlNotFoundException{
    	LsUrlServer url = new LsUrlServer((BasicDBObject) collection.findOne(new BasicDBObject("short", shortUrl)));
    	int tot_visits = url.getTotVisits() + 1;
    	
    	
    	if (url.getShortUrl() == null) {
    		System.out.println("R - Short url : " + shortUrl);
    		System.out.println("R - Short url non trovata");
    		throw new ShortUrlNotFoundException(url.getShortUrl() + "non trovato");
    	} else {
    		updateTotVisits(shortUrl, tot_visits);
    		url.setTotVisits(tot_visits);
    		System.out.println("R - Creation date : " + url.getCreateDate());
    		System.out.println("R - Long url : " + url.getLongUrl());
            System.out.println("R - Short url : " + url.getShortUrl());
            System.out.println("R - totVisits : " + url.getTotVisits());
    		return url;
    	}
    	
    }
    
    public LsUrlServer getLsUrl(String shortUrl) throws ShortUrlNotFoundException{
    	LsUrlServer url = new LsUrlServer((BasicDBObject) collection.findOne(new BasicDBObject("short", shortUrl)));
    	
    	if (url.getShortUrl() == null) {
    		System.out.println("R - Short url : " + shortUrl);
    		System.out.println("R - Short url non trovata");
    		throw new ShortUrlNotFoundException(url.getShortUrl() + "non trovato");
    	} else {
    		System.out.println("R - Creation date : " + url.getCreateDate());
    		System.out.println("R - Long url : " + url.getLongUrl());
            System.out.println("R - Short url : " + url.getShortUrl());
            System.out.println("R - totVisits : " + url.getTotVisits());
    		return url;
    	}
    	
    }
    
    public void updateTotVisits(String shortUrl, int totVisits) {
    	
    	collection.update(new BasicDBObject("short", shortUrl), (DBObject) JSON.parse("{ '$set' : { 'tot_visits': "+ totVisits +"}}"));
    	
    }
    
}