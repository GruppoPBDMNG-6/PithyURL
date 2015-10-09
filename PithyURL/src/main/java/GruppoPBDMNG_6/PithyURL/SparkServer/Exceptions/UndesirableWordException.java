package GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions;

/** 
*
* Eccezione che indica che uno short e' una parola non consentita.
*
* @author Gruppo_PBDMNG_6
* 
*/
public class UndesirableWordException extends Exception{

	/**
	 * Generato automaticamente
	 */
	private static final long serialVersionUID = -1041492893965097794L;

	public UndesirableWordException() {
		
	}
	
	public UndesirableWordException(String err) {
		super(err);
	}
}
