package GruppoPBDMNG_6.PithyURL.Entities;

import java.util.Date;

import org.bson.types.ObjectId;

import GruppoPBDMNG_6.PithyURL.Exceptions.ShortUrlNotFoundException;

import com.google.gson.annotations.SerializedName;
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
	 
	    public LsUrlServer(BasicDBObject dbObject) {
	    	
	    	if(dbObject != null) {
		        this.id = ((ObjectId) dbObject.get("_id")).toString();
		        this.longUrl = dbObject.getString("long");
		        this.shortUrl = dbObject.getString("short");
		        this.totalVisits = dbObject.getInt("tot_visits");
		        this.uniqueVisits = dbObject.getInt("unique_visits");
		        this.createDate = dbObject.getDate("create_date");
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
	    
}
