package GruppoPBDMNG_6.PithyURL.Util;

import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.ShortURLMaxLenghtReachedException;

public class ShortUrlValidator {

	private static final String URL_REGEX = "[-a-zA-Z0-9]*[-a-zA-Z0-9]";
	private static final int URL_MAX_LENGHT = 32;
	public static boolean validate(String url) {
		boolean isValid = false;
		
		if(url.length() > URL_MAX_LENGHT){
			throw new ShortURLMaxLenghtReachedException();
		}else if (url != null && url.matches(URL_REGEX)) {
			isValid = true;
		}
		return isValid;
	}

}
