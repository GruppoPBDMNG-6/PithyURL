package GruppoPBDMNG_6.PithyURL.Util.GeoIPLocation;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

public class GeoLocationByIP {
	
	private File database;
	private DatabaseReader reader;
	
	public GeoLocationByIP() throws IOException{
		// A File object pointing to your GeoIP2 or GeoLite2 database0
		database = new File("src/main/java/GruppoPBDMNG_6/PithyURL/Util/GeoIPLocation/GeoLite2-Country.mmdb");
		// This creates the DatabaseReader object, which should be reused across lookups.
		reader = new DatabaseReader.Builder(database).build();
	}
	
	/*
	 * Dato un IP, ritorna la sigla del paese di provenienza
	 */
	public String getCountryFromIP(String IP) throws IOException, GeoIp2Exception{
		CountryResponse response = reader.country(InetAddress.getByName(IP));
		Country country = response.getCountry();
		return country.getIsoCode();
	}
	
	public static void main(String[] args){
		try {
			GeoLocationByIP b = new GeoLocationByIP();
			System.out.println(b.getCountryFromIP("31.22.48.0"));
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (GeoIp2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}