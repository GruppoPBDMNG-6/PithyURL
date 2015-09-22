package GruppoPBDMNG_6.PithyURL.Util;

import java.util.Random;

public class ShortLinkGenerator {
		
	private static final String _CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RANDOM_STR_LENGTH = 8;
    
    private boolean isCustom;
    private String customLink;
    
    public ShortLinkGenerator(){
    	isCustom = false;
    }
    
    public ShortLinkGenerator(String customLink){
    	isCustom = true;
    	this.customLink = customLink;
    }

    public String generaLink(){
    	
    	String output;
    	if(isCustom){
    		//inutile ma manutenibile
    		output = customLink;
    	} else {
	        char[] chars = _CHAR.toCharArray();
	        StringBuilder sb = new StringBuilder();
	        Random random = new Random();
	        for (int i = 0; i < RANDOM_STR_LENGTH; i++) {
	            char c = chars[random.nextInt(chars.length)];
	            sb.append(c);
	        }
	        output = sb.toString();
    	}
    	
        return output;
        
    }

}
