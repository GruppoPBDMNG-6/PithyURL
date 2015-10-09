package GruppoPBDMNG_6.PithyURL.DataAccess;

import GruppoPBDMNG_6.PithyURL.SparkServer.Entities.LsUrlClient;
import GruppoPBDMNG_6.PithyURL.SparkServer.Entities.LsUrlServer;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.BadURLFormatException;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.ShortUrlDuplicatedException;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.ShortUrlNotFoundException;
import GruppoPBDMNG_6.PithyURL.SparkServer.Exceptions.UndesirableWordException;

public interface IDAO {
	public LsUrlClient createNewLsUrl(String body) throws BadURLFormatException, UndesirableWordException,ShortUrlDuplicatedException;
	public LsUrlServer visitLsUrl(String shortUrl, boolean visitable, String location) throws ShortUrlNotFoundException;
	public LsUrlServer getLsUrl(String shortUrl) throws ShortUrlNotFoundException;
	public boolean checkLinkGen(String linkgen);
}
