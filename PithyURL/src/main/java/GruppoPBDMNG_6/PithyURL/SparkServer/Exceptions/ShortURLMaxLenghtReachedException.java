package GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions;

/** 
*
* Eccezione che indica che uno short url supera il limite stabilito.
*
* @author Gruppo_PBDMNG_6
* 
*/
public class ShortURLMaxLenghtReachedException extends RuntimeException {
	
	/**
	 * Generato automaticamente
	 */
	private static final long serialVersionUID = -6362149966625772535L;

	public ShortURLMaxLenghtReachedException() {
		
	}
	
	public ShortURLMaxLenghtReachedException(String err) {
		super(err);
	}
	

}
