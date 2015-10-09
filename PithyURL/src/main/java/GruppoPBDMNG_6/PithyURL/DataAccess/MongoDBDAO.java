package GruppoPBDMNG_6.PithyURL.DataAccess;
 
import java.util.Date;

import GruppoPBDMNG_6.PithyURL.SparkServer.Entities.LsUrlClient;
import GruppoPBDMNG_6.PithyURL.SparkServer.Entities.LsUrlServer;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.BadURLFormatException;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.ShortUrlDuplicatedException;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.ShortUrlNotFoundException;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.UndesirableWordException;
import GruppoPBDMNG_6.PithyURL.Util.LongUrlValidator;
import GruppoPBDMNG_6.PithyURL.Util.ShortLinkGenerator;
import GruppoPBDMNG_6.PithyURL.Util.ShortUrlValidator;
import GruppoPBDMNG_6.PithyURL.Util.Check.WordChecker;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
 
public class MongoDBDAO implements IDAO{
 
    @SuppressWarnings("unused")
	private final DB db;
    private final DBCollection collection;
    private  String  duplicato = "Duplicate";
   
    public MongoDBDAO(DB db) {
        this.db = db;
        this.collection = db.getCollection("lsurl");
        ShortLinkGenerator.db = this; 
        initilizeDB();
    }
    
    public LsUrlClient createNewLsUrl(String body) throws BadURLFormatException, UndesirableWordException,ShortUrlDuplicatedException{
        LsUrlClient url = new Gson().fromJson(body, LsUrlClient.class);
        
        if(url.getLongUrl() == null){
        	throw new BadURLFormatException("W - Long url: '"+ url.getLongUrl() + "' non valido");
        }
        
        System.out.println("W - Long url : " + url.getLongUrl());
        
        LongUrlValidator validator = new LongUrlValidator(url.getLongUrl());
        String shortUrl;
        
        
        if(validator.validate()){
        	
        	if(url.isCustom()){
            	
        		url.generateShort();
            	shortUrl = url.getShortUrl();
            	
            	System.out.println("W - Short url : " + url.getShortUrl());
            	
            	WordChecker wc = new WordChecker();
            	if(wc.isBadWord(shortUrl) || !ShortUrlValidator.validate(url.getShortUrl())){
    				throw new UndesirableWordException("W - Parola non accettabile : " + url.getShortUrl());
            	} 
            	
            	if(url.getShortUrl() == duplicato) throw new ShortUrlDuplicatedException("W - Short url esistente : " + url.getShortUrl());
            	
            	collection.insert(new BasicDBObject("long", validator.getFixedUrl()).append("short", shortUrl).append("custom", true)
             			.append("tot_visits", 0).append("unique_visits", 0).append("create_date", new Date()).append("countries", new BasicDBList()));
        		
        	}else{
        		
        		url.generateShort();
        		shortUrl = url.getShortUrl();
        		
        		LsUrlServer tempurl = checkLongUrl(validator.getFixedUrl());
            	System.out.println("W - Long url fixed : " + validator.getFixedUrl());
                
                if(tempurl.getLongUrl() != null ){
                	url.setShortUrl(tempurl.getShortUrl());
                } else {
                	collection.insert(new BasicDBObject("long", validator.getFixedUrl()).append("short", shortUrl).append("custom", false)
                 			.append("tot_visits", 0).append("unique_visits", 0).append("create_date", new Date()).append("countries", new BasicDBList()));
                }
                
                System.out.println("W - Short url : " + url.getShortUrl());
                
        	}
            
        } else {
        	throw new BadURLFormatException("W - Long : "+ url.getLongUrl() + " non valido");
        }
		       
        return url;
    }
    
    public LsUrlServer visitLsUrl(String shortUrl, boolean visitable, String location) throws ShortUrlNotFoundException{
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
    		throw new ShortUrlNotFoundException();
    	} else {
    		updateVisits(shortUrl, tot_visits, unique_visits, location);
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
    		throw new ShortUrlNotFoundException("R - Short url non trovata");
    	} else {
    		System.out.println("R - Creation date : " + url.getCreateDate());
    		System.out.println("R - Long url : " + url.getLongUrl());
            System.out.println("R - Short url : " + url.getShortUrl());
            System.out.println("R - totVisits : " + url.getTotVisits());
            System.out.println("R - uniqueVisits : " + url.getUniqueVisits());
    		return url;
    	}
    	
    }
    
    private void updateVisits(String shortUrl, int totVisits, int uniqueVisits, String location) {
    	
    	LsUrlServer urlCeckLocation = new LsUrlServer(
    			(BasicDBObject) collection.findOne((DBObject) JSON.parse("{'short':'"+shortUrl+"', 'countries.name': '"+location+"'}")));
    	
    	if (urlCeckLocation.getShortUrl() == null){
    		collection.update((DBObject) JSON.parse("{'short':'"+shortUrl+"'}")
    				,(DBObject) JSON.parse("{'$push': {'countries' : {'name':'"+location+"', 'visits' :1}}}"));
    	} else {
    		collection.update((DBObject) JSON.parse("{'short':'"+shortUrl+"', 'countries.name': '"+location+"'}")
    				,(DBObject) JSON.parse("{'$inc': {'countries.$.visits' : 1}}"));
    	}
    	
    	collection.update((DBObject) JSON.parse("{'short':'"+shortUrl+"'}")
				,(DBObject) JSON.parse("{'$set':{'tot_visits': "+totVisits+", 'unique_visits' :  "+uniqueVisits+"}}"));

    }
    
	private LsUrlServer checkLongUrl(String longUrl) {

		LsUrlServer url = new LsUrlServer(
				(BasicDBObject) collection.findOne((DBObject) JSON.parse("{'custom':false,'long':'"+longUrl+"'}")));
		if(url.getShortUrl() != null){
			System.out.println("W - Long url esistente");
		}
		return url;

	}

	public boolean checkLinkGen(String shortUrl) {
		boolean duplicatedLink = false;
		LsUrlServer url = new LsUrlServer(
				(BasicDBObject) collection.findOne(new BasicDBObject("short", shortUrl)));
		if (url.getShortUrl() != null) {
			duplicatedLink = true;
		}

		return duplicatedLink;

	}
	
	private void initilizeDB(){
		
		if(!checkLinkGen("test")){
			try {
				System.out.println("\nW - Insrimento URL di test");
				createNewLsUrl("{'long':'http://www.google.it','custom':true,'short':'test','custom':'true'}");
				collection.update((DBObject) JSON.parse("{'short':'test'}")
						,(DBObject) JSON.parse("{ $push: { countries: { $each: [{'name' : 'RU','visits' : 1},"
						+" {'name' : 'A2','visits' : 120},{'name' : 'US','visits' : 120},{'name' : 'IT','visits' : 500},{'name' : 'GB','visits' : 700},"
						+ "{'name' : 'CH','visits' : 1300},{'name' : 'RS','visits' : 6000},{'name' : 'AT','visits' : 12000},{'name' : 'CA','visits' : 111000},"
						+ "{'name' : 'BE','visits' : 1111000},{'name' : 'GH','visits' : 2111000},{'name' : 'MX','visits' : 5111000},"
						+ "{'name' : 'NaN','visits' : 2}]}}}"));
				collection.update((DBObject) JSON.parse("{'short':'test'}")
						,(DBObject) JSON.parse("{'$set':{'tot_visits': 7567523, 'unique_visits' :  5456234}}"));
				System.out.println();
			} catch (BadURLFormatException | ShortUrlDuplicatedException | UndesirableWordException e) {
				//Sicuramente non eseguito
			}

		}
		
	}
   
    
}