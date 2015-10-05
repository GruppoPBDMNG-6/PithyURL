package GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions;

public class UndesirableWordException extends Exception{

	private static final long serialVersionUID = -1041492893965097794L;

	public UndesirableWordException() {
		
	}
	
	public UndesirableWordException(String err) {
		super(err);
	}
}
