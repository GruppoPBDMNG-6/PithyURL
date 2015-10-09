package GruppoPBDMNG_6.PithyURL.Util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
* 
* Classe per la verifica che un URL sia nel formato standard (Che effettivamente sia un URL).
*
* @author Gruppo_PBDMNG_6
* 
*/
public class LongUrlValidator {
	
	private String url;
	private static final String URL_REGEX = "^(http?|https|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	
	public LongUrlValidator(String url){
		
		this.url = fix(url);
		
	}
	
	/**
	 * 
	 * Valida un URL.
	 * 
	 * @return Booleano, true se l'URL e' valido, false altrimenti.
	 * 
	 */
	public boolean validate(){
		
		boolean isValid = false;
		
		if(url != null && url.matches(URL_REGEX)){
			
			isValid = true;
			
		}
		
		return isValid;
		
	}
	
	/**
	 * 
	 * Completa con il proxy un URL se manca.
	 * 
	 * @param url Url da aggiustare.
	 * @return Url aggiustato.
	 * 
	 */
	private String fix(String url){
		
		String fixedURL = url;
		
		String URLProtocol = url.substring(0,Math.min(url.length(), url.indexOf("/") + 2));
		String protocols[] = {"http://", "https://", "ftp://", "file://"};
		
		Set<String> protocolsSet = new HashSet<String>();
		Collections.addAll(protocolsSet, protocols);
		
		if(!protocolsSet.contains(URLProtocol.toLowerCase())){
			fixedURL = "http://" + url;
		}
		
		return fixedURL.toLowerCase();
		
	}
	
	public String getFixedUrl(){return this.url;}

}
