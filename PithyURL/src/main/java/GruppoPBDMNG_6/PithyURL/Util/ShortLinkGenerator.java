package GruppoPBDMNG_6.PithyURL.Util;

import java.util.Random;

import GruppoPBDMNG_6.PithyURL.DataAccess.IDAO;

public class ShortLinkGenerator {
		
	private static final String _CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RANDOM_STR_LENGTH = 8;
    public static IDAO db;
    
    private boolean isCustom;
    private String customLink;
    private  String  duplicato = "Duplicate";
    
    public ShortLinkGenerator(){
    	isCustom = false;
    }
    
    public ShortLinkGenerator(String customLink){
    	isCustom = true;
    	this.customLink = customLink;
    }

    public String generaLink(){
    	boolean check = true;
    	String output = "";
    	if(isCustom){
    		output = customLink;
    		check = db.checkLinkGen(output);
    		if(check == true){output = duplicato;}
    	} else {
	        char[] chars = _CHAR.toCharArray();
	        StringBuilder sb = new StringBuilder();
	        Random random = new Random();
	        while(check == true){
		        for (int i = 0; i < RANDOM_STR_LENGTH; i++) {
		            char c = chars[random.nextInt(chars.length)];
		            sb.append(c);
		        }
		        output = sb.toString();
		        check = db.checkLinkGen(output);
	        }
    	}
    	
        return output;
        
    }
    
    

}
