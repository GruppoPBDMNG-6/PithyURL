package GruppoPBDMNG_6.PithyURL.SparkServer;

/** 
*
* Elenco dei tipi di richieste post che è possibile mandare al server
*
* @author Gruppo_PBDMNG_6
* 
*/
public class API {
	
	public static final String API_CONTEXT = "/api/v1";
	
	public static final String VISIT = "/:short";
	public static final String NEW_URL = "/lsurl";
	public static final String INSPECT_URL = "/inspectUrl";
	public static final String PAGE_NOT_FOUND = "/404.html";
	
	//test api
	public static final String TES_VISIT = "/test/:short";
	public static final String TEST_GET_COOKIE = "/test/util/getCookie";
}
