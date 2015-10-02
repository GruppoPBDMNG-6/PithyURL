package GruppoPBDMNG_6.PithyURL.DataAccess;
 
import java.util.Date;

import GruppoPBDMNG_6.PithyURL.Entities.LsUrlClient;
import GruppoPBDMNG_6.PithyURL.Entities.LsUrlServer;
import GruppoPBDMNG_6.PithyURL.Exceptions.BadURLFormatException;
import GruppoPBDMNG_6.PithyURL.Exceptions.ShortUrlDuplicatedException;
import GruppoPBDMNG_6.PithyURL.Exceptions.ShortUrlNotFoundException;
import GruppoPBDMNG_6.PithyURL.Exceptions.UndesirableWordException;
import GruppoPBDMNG_6.PithyURL.Util.CheckDuplicated;
import GruppoPBDMNG_6.PithyURL.Util.LongUrlValidator;
import GruppoPBDMNG_6.PithyURL.Util.ShortUrlValidator;
import GruppoPBDMNG_6.PithyURL.Util.Check.WordChecker;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
 
public class DAO {
 
    @SuppressWarnings("unused")
	private final DB db;
    private final DBCollection collection;
    private  String  duplicato = "Duplicate";
   
    public DAO(DB db) {
        this.db = db;
        this.collection = db.getCollection("lsurl");
        CheckDuplicated.collection = collection; // fix checkDuplicated
    }
 
    //funzioni di scrittura - lettura db
    
    public LsUrlClient createNewLsUrl(String body) throws BadURLFormatException, UndesirableWordException,ShortUrlDuplicatedException{
        LsUrlClient url = new Gson().fromJson(body, LsUrlClient.class);
        
        if(url.getLongUrl() == null){
        	throw new BadURLFormatException("W - Long : "+ url.getLongUrl() + "non valido");
        }
        
        System.out.println("W - Long url : " + url.getLongUrl());
        
        LongUrlValidator validator = new LongUrlValidator(url.getLongUrl());
        String shortUrl = url.getShortUrl();
        
        
        if(validator.validate()){
        	
        	if(url.isCustom()){
            	
            	url.generateShort();
            	System.out.println("W - Short url : " + url.getShortUrl());
            	
            	WordChecker wc = new WordChecker();
            	if(wc.isUndesirable(shortUrl) || !ShortUrlValidator.validate(url.getShortUrl())){
    				throw new UndesirableWordException("W - Parola non accettabile : " + url.getShortUrl());
            	} 
            	
            	if(url.getShortUrl() == duplicato) throw new ShortUrlDuplicatedException("W - Short url esistente : " + url.getShortUrl());
            	
            	collection.insert(new BasicDBObject("long", validator.getFixedUrl()).append("short", shortUrl)
             			.append("tot_visits", 0).append("unique_visits", 0).append("create_date", new Date()));
        		
        	}else{
        		
        		url.generateShort();
        		
        		LsUrlServer tempurl =  CheckDuplicated.checkLongUrl(validator.getFixedUrl());   // fix checkDuplicated
            	System.out.println("W - Long url fixed : " + validator.getFixedUrl());
                
                if(tempurl.getLongUrl() != null ){
                	url.setShortUrl(tempurl.getShortUrl());
                } else {
                	collection.insert(new BasicDBObject("long", validator.getFixedUrl()).append("short", shortUrl)
                 			.append("tot_visits", 0).append("unique_visits", 0).append("create_date", new Date()));
                }
                
                System.out.println("W - Short url : " + url.getShortUrl());
                
        	}
            
        } else {
        	throw new BadURLFormatException("W - Long : "+ url.getLongUrl() + " non valido");
        }
		       
        return url;
    }
    
    public LsUrlServer visitLsUrl(String shortUrl, boolean visitable) throws ShortUrlNotFoundException{
    	LsUrlServer url = new LsUrlServer((BasicDBObject) collection.findOne(new BasicDBObject("short", shortUrl)));
    	int tot_visits = url.getTotVisits();
    	int unique_visits = url.getUniqueVisits();
    	tot_visits++;
    	if(visitable){
    		unique_visits++;
    	}
    	
    	if (url.getShortUrl() == null) {
    		System.out.println("R - Short url : " + shortUrl);
    		System.out.println("R - Short url non trovata");
    		throw new ShortUrlNotFoundException(url.getShortUrl() + "non trovato");
    	} else {
    		updateVisits(shortUrl, tot_visits, unique_visits);
    		url.setTotVisits(tot_visits);
    		url.setUniqueVisits(unique_visits);
    		System.out.println("R - Creation date : " + url.getCreateDate());
    		System.out.println("R - Long url : " + url.getLongUrl());
            System.out.println("R - Short url : " + url.getShortUrl());
            System.out.println("R - totVisits : " + url.getTotVisits());
            System.out.println("R - uniqueVisits : " + url.getUniqueVisits());
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
            System.out.println("R - uniqueVisits : " + url.getUniqueVisits());
    		return url;
    	}
    	
    }
    
    public void updateVisits(String shortUrl, int totVisits, int uniqueVisits) {
    	
    	collection.update(new BasicDBObject("short", shortUrl), (DBObject) JSON.parse("{ '$set' : { 'tot_visits': "+ totVisits + 
    			", 'unique_visits': "+ uniqueVisits + "}}"));
    	
    }
    
    private void InitializeDb(){
    	
    }
   
    
}