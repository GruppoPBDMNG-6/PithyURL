package GruppoPBDMNG_6.PithyURL.Entities;
 
import GruppoPBDMNG_6.PithyURL.Util.ShortLinkGenerator;

import com.google.gson.annotations.SerializedName;
import com.mongodb.BasicDBObject;

import org.bson.types.ObjectId;
 
public class LsUrlClient {
    
    @SerializedName("short")
    private String shortUrl;
    
    @SerializedName("long")
    private String longUrl;
    
    @SerializedName("custom")
    private boolean custom;
 
    public LsUrlClient() {}
    
    public String getLongUrl() { 
        return longUrl;
    }
 
    public String getShortUrl() {
    	
    	if (shortUrl.equals("not_set")){
    		
    		ShortLinkGenerator linkGen;
        
	        if (custom){
	        	linkGen = new ShortLinkGenerator(shortUrl);
	        	this.shortUrl = linkGen.generaLink();
	        } else {
	        	linkGen = new ShortLinkGenerator();
	        	this.shortUrl = linkGen.generaLink();
	        }
	        
    	}
    	
        return shortUrl;
    }

	public boolean isCustom() {
		return custom;
	}
	
	public void setShortUrl(String shortUrl){
		this.shortUrl = shortUrl;
	}
 
}