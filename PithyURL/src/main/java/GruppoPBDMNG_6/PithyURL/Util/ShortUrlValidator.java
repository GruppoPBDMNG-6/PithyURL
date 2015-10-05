package GruppoPBDMNG_6.PithyURL.Util;

public class ShortUrlValidator {
	
	private static final String URL_REGEX = "[-a-zA-Z0-9]*[-a-zA-Z0-9]";
	
	
	public static boolean validate(String url){
		
		boolean isValid = false;
		
		if(url != null && url.matches(URL_REGEX)){
			
			isValid = true;
			
		}
		
		return isValid;
		
	}
	
}
