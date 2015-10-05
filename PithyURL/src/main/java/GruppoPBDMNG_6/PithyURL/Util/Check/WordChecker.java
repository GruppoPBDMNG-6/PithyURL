package GruppoPBDMNG_6.PithyURL.Util.Check;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordChecker {
	
	public boolean isUndesirable(String s){
		boolean result = false;
		File f = new File("src/main/java/GruppoPBDMNG_6/PithyURL/Util/Check/languages.txt");
		Scanner sc = null;
		try {
			for(sc = new Scanner(f); sc.hasNext() && !result;){
				String line = sc.nextLine();
				result = searchWordInFile("src/main/java/GruppoPBDMNG_6/PithyURL/Util/Check/WordList/" + line, s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		sc.close();
		return result;
	}

	private boolean searchWordInFile(String path, String s){
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
	}
	
}
