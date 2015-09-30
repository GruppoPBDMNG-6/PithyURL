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
	
	public static void main(String[] args) throws IOException, GeoIp2Exception {
		GeoLocationByIP p = new GeoLocationByIP();
		String[] Ips = {"31.44.79.255", "5.198.159.255", "5.53.255.255"};
		for(int i = 0; i<Ips.length; i++){
		System.out.println(p.getCountryFromIP(Ips[i]));
		}
	}
}