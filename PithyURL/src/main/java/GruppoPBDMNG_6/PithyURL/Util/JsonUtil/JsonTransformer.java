package GruppoPBDMNG_6.PithyURL.Util.JsonUtil;
 
import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
*
* Classe utilizzata per convertire in json le informazioni mandate dal server al client.
*
* @author Gruppo_PBDMNG_6
* 
*/
public class JsonTransformer implements ResponseTransformer {
 
    private Gson gson = new Gson();
 
    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }
 
}

