package GruppoPBDMNG_6.PithyURL.Util;
 
import com.google.gson.Gson;
import spark.Response;
import spark.ResponseTransformer;
 
import java.util.HashMap;
 
public class JsonTransformer implements ResponseTransformer {
 
    private Gson gson = new Gson();
 
    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }
 
}