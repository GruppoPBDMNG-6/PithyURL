package GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions;

public class ShortUrlNotFoundException extends RuntimeException {
	
	/**
	 * Generato automaticamente
	 */
	private static final long serialVersionUID = -8154421624266818993L;

	public ShortUrlNotFoundException() {
		
	}
	
	public ShortUrlNotFoundException(String err) {
		super(err);
	}
	
}