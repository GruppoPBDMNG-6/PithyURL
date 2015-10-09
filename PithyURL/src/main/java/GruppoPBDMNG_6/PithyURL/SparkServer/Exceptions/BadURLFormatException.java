package GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions;

/** 
*
* Eccezione che indica che un long url nan ha un formato corretto.
*
* @author Gruppo_PBDMNG_6
* 
*/
public class BadURLFormatException extends RuntimeException {
	
	/**
	 * Generato automaticamente
	 */
	private static final long serialVersionUID = -6362149966625772535L;

	public BadURLFormatException() {
		
	}
	
	public BadURLFormatException(String err) {
		super(err);
	}
	

}
