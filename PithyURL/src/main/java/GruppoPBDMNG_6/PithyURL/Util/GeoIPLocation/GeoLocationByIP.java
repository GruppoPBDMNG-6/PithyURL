package GruppoPBDMNG_6.PithyURL.Util.GeoIPLocation;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

/**
* 
* Classe per la geolocalizzazione di un IP.
* 
* @author Gruppo_PBDMNG_6
* 
*/
public class GeoLocationByIP {
	
	private File database;
	private DatabaseReader reader;
	
	public GeoLocationByIP() throws IOException{
		// A File object pointing to your GeoIP2 or GeoLite2 database0
		database = new File("src/main/java/GruppoPBDMNG_6/PithyURL/Util/GeoIPLocation/GeoLite2-Country.mmdb");
		// This creates the DatabaseReader object, which should be reused across lookups.
		reader = new DatabaseReader.Builder(database).build();
	}
	
	/**
	 * 
	 * Recupera l'acronimo del paese associato ad un IP.
	 * 
	 * @param IP IP in formato stringa.
	 * @return Acrnimo del paese.
	 * 
	 * @throws IOException Eccezione di tipo IO.
	 * @throws GeoIp2Exception Eccezione di GeoIP2.
	 * 
	 */
	public String getCountryFromIP(String IP) throws IOException, GeoIp2Exception{
		CountryResponse response = reader.country(InetAddress.getByName(IP));
		Country country = response.getCountry();
		return country.getIsoCode();
	}
	
}