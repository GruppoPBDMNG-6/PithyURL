package GruppoPBDMNG_6.PithyURL.DataAccess;
 
import java.util.Date;
import GruppoPBDMNG_6.PithyURL.SparkServer.Entities.LsUrlClient;
import GruppoPBDMNG_6.PithyURL.SparkServer.Entities.LsUrlServer;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.BadURLFormatException;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.ShortURLMaxLenghtReachedException;
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

/** 
* 
* Implemena l'intefaccia IDAO e fornisce servizi per il 
* salvataggio e la lettura di dati dal database mongodb.
* 
* @author Gruppo_PBDMNG_6
* 
*/
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
    
    /**
     * 
     * Inserisce una nuovo PithyURL nel database.
	 * 
	 * @param body Richiesta ricevuta dal client in formato JSON.
	 * @return Oggetto di tipo lsUrlClient.
	 * 
	 * @throws BadURLFormatException		Lanciata in caso il long url non abbia un formato adeguato.
	 * @throws UndesirableWordException		Lanciata in caso lo short url sia una parola non consentita.
	 * @throws ShortUrlDuplicatedException  Lanciata in caso lo short url sia gia presente nel database.
	 * 
	 */
    public LsUrlClient createNewLsUrl(String body) throws BadURLFormatException, UndesirableWordException,
    													  ShortUrlDuplicatedException, ShortURLMaxLenghtReachedException{
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
    
    /**
     * 
     * Visita un PithyURl oggiornando i relativi contatori.
	 * 
	 * @param shortUrl Short url.
	 * @param visitable Booleano che indica se l'url sia stato già visitato dal richiedente.
	 * @param location Acronimo del paese di provenienza della richiesta.
	 * @return Oggetto di tipo LsUrlClient.
	 * 
	 * @throws ShortUrlNotFoundException Lanciata se lo short url non e' presente nel database.
	 * 
	 */
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
    
    /**
     * 
     * Recupera informazioni dal database riguardo lo short url in input.
	 * 
	 * @param shortUrl Short url.
	 * @return Oggetto di tipo LsUrlServer.
	 * 
	 * @throws ShortUrlNotFoundException Lanciata se lo short url non e' presente nel database.
	 * 
	 */
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
    
    /**
     * 
     * Metodo utilizzato da visitLsUrl per aggirnare i contatori relativi alla visita.
	 * 
	 * @param shortUrl Short url.
	 * @param totVisits	Numero di click sullo short url.
	 * @param uniqueVisits Numero di visite uniche dello short url.
	 * @param location Acronimo del paese di provenienza della richiesta.
	 * 
	 */
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
    
    /**
     * 
     * Verifica che un long url sia gia' presente nel database.
	 * 
	 * @param longUrl Long url.
	 * @return Oggetto di tipo LsUrlServer.
	 * 
	 */
	private LsUrlServer checkLongUrl(String longUrl) {

		LsUrlServer url = new LsUrlServer(
				(BasicDBObject) collection.findOne((DBObject) JSON.parse("{'custom':false,'long':'"+longUrl+"'}")));
		if(url.getShortUrl() != null){
			System.out.println("W - Long url esistente");
		}
		return url;

	}

	/**
	 * 
	 * Verifica che uno short url generato casualmente non sia gia' presente nel database.
	 * 
	 * @param shortUrl Short url.
	 * @return Booleano, true se lo short url è presente nel database, false altrimenti.
	 * 
	 */
	public boolean checkLinkGen(String shortUrl) {
		boolean duplicatedLink = false;
		LsUrlServer url = new LsUrlServer(
				(BasicDBObject) collection.findOne(new BasicDBObject("short", shortUrl)));
		if (url.getShortUrl() != null) {
			duplicatedLink = true;
		}

		return duplicatedLink;

	}
	
	/**
	 * 
	 * Inserisce uno short url di test nel database.
	 * 
	 */
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