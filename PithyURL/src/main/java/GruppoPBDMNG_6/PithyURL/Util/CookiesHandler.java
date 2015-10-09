package GruppoPBDMNG_6.PithyURL.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import spark.Request;
import spark.Response;

/**
* 
* Classe per la gestione dei cookie.
*
* @author Gruppo_PBDMNG_6
* 
*/
public class CookiesHandler {
	
	/**
	 * 
	 * Verifica che uno short appena visitato lo sia per la prima volta
	 * 
	 * @param request Richiesta del client.
	 * @param response Risposta del server.
	 * @return Booleano, true se e' la prima visita, false altrimenti.
	 * 
	 */
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
	
	/**
	 * 
	 * Aggiunge lo short url ai cookie se lo si visita per la prima volta.
	 * 
	 * @param request
	 * @param response
	 * @param esistente Indica se i cookie sono stati gia' impostati in precedenza.
	 * 
	 */
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
