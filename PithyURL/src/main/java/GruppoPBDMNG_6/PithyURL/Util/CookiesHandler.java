package GruppoPBDMNG_6.PithyURL.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import spark.Request;
import spark.Response;

public class CookiesHandler {
	public static boolean handleVisit(Request request, Response response){
		boolean visitable = false;
		
		if(request.cookie("pithyurl_visited") == null){
			setVisitCookie(request, response, false);
			visitable = true;
		}else{
    		ArrayList<String> shortVisited = new ArrayList<String>(Arrays.asList(request.cookie("pithyurl_visited").split("-")));
    		if(!shortVisited.contains(request.params(":short"))){
    			setVisitCookie(request, response, true);
    			visitable = true;
    		}
		}
		return visitable;
	}
	
	private static void setVisitCookie(Request request, Response response, boolean esistente){
		
		String cookie;
		
		if(esistente){
			cookie = request.cookie("pithyurl_visited");
			cookie += "-" + request.params(":short");
		} else {
			cookie = request.params(":short");
		}
		
		Date date = new Date();   
		response.cookie("pithyurl_visited", cookie, ((int) date.getTime() % 1000) + 3600000*24*30);
	}
}
