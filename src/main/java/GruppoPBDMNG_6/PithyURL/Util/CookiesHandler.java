package GruppoPBDMNG_6.PithyURL.Util;

import java.util.ArrayList;
import java.util.Arrays;

import spark.Request;
import spark.Response;

public class CookiesHandler {
	public static boolean handleVisit(Request request, Response response){
		boolean visitable = false;
		
		if(request.cookie("pithyurl_visited") == null){
			setVisitCookie(request, response);
			visitable = true;
		}else{
    		ArrayList<String> shortVisited = new ArrayList<String>(Arrays.asList(request.cookie("pithyurl_visited").split("-")));
    		if(!shortVisited.contains(request.params(":short"))){
    			setVisitCookie(request, response);
    			visitable = true;
    		}
		}
		return visitable;
	}
	
	private static void setVisitCookie(Request request, Response response){
		String cookie = request.cookie("pithyurl_visited");
		cookie += "-" + request.params(":short");
		response.cookie("pithyurl_visited", cookie);
	}
}
