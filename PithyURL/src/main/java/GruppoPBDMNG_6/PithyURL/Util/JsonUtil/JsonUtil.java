package GruppoPBDMNG_6.PithyURL.Util.JsonUtil;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
*
* Classe utilizzata per convertire in json le informazioni mandate dal server al client durante i test.
*
* @author Gruppo_PBDMNG_6
* 
*/
public class JsonUtil {

	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	public static ResponseTransformer json() {
		return JsonUtil::toJson;
	}
}