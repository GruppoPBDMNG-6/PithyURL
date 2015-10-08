package GruppoPBDMNG_6.PithyURL.Util.Check;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordChecker {

	/**
	 * Verifica se una parola sia desiderabile o meno.
	 * @param s stringa in input.
	 * @return true se la stringa non e' desiderabile, false altrimenti.
	 */
	/*public boolean isUndesirable(String s){
		boolean result = false;
		File f = new File(System.getProperty("user.dir")+"/src/main/java/GruppoPBDMNG_6/PithyURL/Util/Check/languages.txt");
		Scanner sc = null;
		try {
			for(sc = new Scanner(f); sc.hasNext() && !result;){
				String line = sc.nextLine();
				result = searchWordInFile(System.getProperty("user.dir")+"/src/main/java/GruppoPBDMNG_6/PithyURL/Util/Check/WordList/" + line, s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sc.close();
		return result;
	}*/

	/**
	 * Verifica se una parola sia presente in un file.
	 * @param path percorso del file di testo da analizzare.
	 * @param s stringa di cui verificare la presenza.
	 * @return true se la parola sia stata trovata, false altrimenti.
	 */
	/*private boolean searchWordInFile(String path, String s){
		File f = new File(path);
		boolean result = false;
		Scanner sc = null;
		try {
			for (sc = new Scanner(f); sc.hasNext() && !result;) {
				String line = sc.nextLine();
				if (line.equalsIgnoreCase(s)) result = true;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sc.close();
		return result;
	}*/
	
	/////////////////////////////
	
	private List<String> languageList = new ArrayList<String>();
	
	public WordChecker(){
		File langList = new File(System.getProperty("user.dir")+
				"/src/main/java/GruppoPBDMNG_6/PithyURL/Util/Check/languages.txt");		
	
		BufferedReader rLang;	

		try {			
			rLang = new BufferedReader(new FileReader(langList));			
			String lang = null;
			while ((lang = rLang.readLine()) != null) {
				
		        languageList.add(lang);
		        }		
			}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}    
	}
	
	public boolean isBadWord(String word){
		boolean dirty = false;
		
		for(int i = 0; i < languageList.size(); i++){
			File file = new File(System.getProperty("user.dir")
					+"/src/main/java/GruppoPBDMNG_6/PithyURL/Util/Check/WordList/"+languageList.get(i));
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(file));
				String text = null;
				while ((text = reader.readLine()) != null) {
			        if(word.equalsIgnoreCase(text)){
			        	dirty = true;
			        	break;
			        }}
				}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				} 
			if(dirty == true){
				break;
			}			
		}
		return dirty;
	}	
	
}
