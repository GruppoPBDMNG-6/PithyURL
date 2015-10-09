package GruppoPBDMNG_6.PithyURL.SparkServer;
 
import GruppoPBDMNG_6.PithyURL.DataAccess.IDAO;
import GruppoPBDMNG_6.PithyURL.DataAccess.MongoDBDAO;

import com.mongodb.*;

//import static spark.Spark.setIpAddress;
import static spark.Spark.setPort;
import static spark.SparkBase.staticFileLocation;

/**
*
* Classe principale che si occupa di avviare il server.
*
* @author Gruppo_PBDMNG_6
* 
*/
public class Bootstrap {
    //private static final String IP_ADDRESS = "192.168.1.3";
    private static final int PORT = 8080;
    
    public static void main(String[] args) throws Exception {
        //setIpAddress(IP_ADDRESS);
        setPort(PORT);
        staticFileLocation("/public");
        if(args == null){
        	new Resource((IDAO) new MongoDBDAO(mongo()), false);
        }else{
        	new Resource((IDAO) new MongoDBDAO(mongo()), true);
        }
    }
 
    /**
	 * 
	 * Avvia mongoDB.
	 * 
	 * @return Database di tipo mongoDB.
	 * 
	 * @throws Exception Qualsiasi tipo di eccezione.
	 * 
	 */
    public static DB mongo() throws Exception {

         MongoClient mongoClient = new MongoClient("localhost", 27017);
         return mongoClient.getDB("pithy_url");

    }
    
}