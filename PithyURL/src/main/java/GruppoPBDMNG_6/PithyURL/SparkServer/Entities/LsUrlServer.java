package GruppoPBDMNG_6.PithyURL.SparkServer.Entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import org.bson.types.ObjectId;
import com.google.gson.annotations.SerializedName;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class LsUrlServer {
	
	 	private String id;
	    
	    @SerializedName("short")
	    private String shortUrl;
	    
	    @SerializedName("long")
	    private String longUrl;
	    
	    @SerializedName("response")
	    private String response;
	    
	    @SerializedName("tot_visits")
	    private int totalVisits;
	    
	    @SerializedName("unique_visits")
	    private int uniqueVisits;
	    
	    @SerializedName("create_date")
	    private Date createDate;
	    
	    @SerializedName("countries")
	    private ArrayList<Country> countries;
	 
	    public LsUrlServer(BasicDBObject dbObject) {
	    	
	    	if(dbObject != null) {
		        this.id = ((ObjectId) dbObject.get("_id")).toString();
		        this.longUrl = dbObject.getString("long");
		        this.shortUrl = dbObject.getString("short");
		        this.totalVisits = dbObject.getInt("tot_visits");
		        this.uniqueVisits = dbObject.getInt("unique_visits");
		        this.createDate = dbObject.getDate("create_date");
		        
		        countries = new ArrayList<Country>();
		        
		        if(dbObject.get("countries")!= null){
		        
		        for (  Object object : (BasicDBList) dbObject.get("countries")) {
		        	
		        	BasicDBObject embedded =  (BasicDBObject) object;
		        	countries.add(new Country(embedded.getString("name"), embedded.getInt("visits")));
		            
		        }
		        
		        Collections.sort(countries);
		        }
	    	} 
	    	
	    }
	    
	 
	    public String getLongUrl() {return longUrl;}
	 
	    public String getShortUrl() {return shortUrl;}
	    
	    public int getTotVisits() {return totalVisits;}
	    
	    public int getUniqueVisits() {return uniqueVisits;}
	    
	    public Date getCreateDate() {return createDate;}
	    
	    public void setTotVisits(int newTotVisits) {this.totalVisits = newTotVisits;}
	    
	    public void setUniqueVisits(int newUniqueVisits) {this.uniqueVisits = newUniqueVisits;}
	    
	    public void setResponse(String response){this.response = response;}
	    
	    public String getResponse(){return this.response;}
	    
	    public void setCountries(ArrayList<Country> countries){this.countries = countries;}
	    
	    public ArrayList<Country> getCountries(){return this.countries;}
	    
}
