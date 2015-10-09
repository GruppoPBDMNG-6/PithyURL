package GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions;

/** 
*
* Eccezione che indica che uno short url e' gia' presente nel database.
*
* @author Gruppo_PBDMNG_6
* 
*/
public class ShortUrlDuplicatedException extends RuntimeException {
	/**
	 * Generato automaticamente
	 */
	private static final long serialVersionUID = -8154421624266818993L;

	public ShortUrlDuplicatedException() {
		
	}
	
	public ShortUrlDuplicatedException(String err) {
		super(err);
	}
	
}
