package GruppoPBDMNG_6.PithyURL.Util;

import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.ShortURLMaxLenghtReachedException;

/**
* 
* Classe per la verifica che uno short url sia nel formato stabilito.
*
* @author Gruppo_PBDMNG_6
* 
*/
public class ShortUrlValidator {

	private static final String URL_REGEX = "[-a-zA-Z0-9]*[-a-zA-Z0-9]";
	private static final int URL_MAX_LENGHT = 32;
	
	/**
	 * 
	 * Valida uno short url.
	 * 
	 * @param url Short url da validare.
	 * @return Booleano, true se lo short url e' valido, false altrimenti.
	 * 
	 */
	public static boolean validate(String url) throws ShortURLMaxLenghtReachedException{
		boolean isValid = false;
		
		if(url.length() > URL_MAX_LENGHT){
			throw new ShortURLMaxLenghtReachedException();
		}else if (url != null && url.matches(URL_REGEX)) {
			isValid = true;
		}
		return isValid;
	}

}
