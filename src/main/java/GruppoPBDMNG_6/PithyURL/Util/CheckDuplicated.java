package GruppoPBDMNG_6.PithyURL.Util;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import GruppoPBDMNG_6.PithyURL.Entities.LsUrlServer;

public  class CheckDuplicated {
	
	public static DBCollection collection;
	
	
	
	
	 public static LsUrlServer checkLongUrl(String longUrl) {
	    	
	    	LsUrlServer url = new LsUrlServer((BasicDBObject) collection.findOne(new BasicDBObject("long", longUrl)));
	    	System.out.println("W - LongUrl già presente " +  url.getLongUrl());
	        return url;
	    	
	    	
	    }
	 
	 public static boolean checkLinkGen(String linkgen){
		 boolean duplicatedLink = false;
	    	LsUrlServer url = new LsUrlServer((BasicDBObject) collection.findOne(new BasicDBObject("short", linkgen)));
	    	if(url != null) {duplicatedLink = true;}
	    	
	    	return  duplicatedLink;
	    	
	    }

}
