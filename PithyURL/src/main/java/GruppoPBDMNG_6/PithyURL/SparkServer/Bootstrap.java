package GruppoPBDMNG_6.PithyURL.SparkServer;
 
import GruppoPBDMNG_6.PithyURL.DataAccess.IDAO;
import GruppoPBDMNG_6.PithyURL.DataAccess.MongoDBDAO;

import com.mongodb.*;

import static spark.Spark.setIpAddress;
import static spark.Spark.setPort;
import static spark.SparkBase.staticFileLocation;
 
public class Bootstrap {
    //private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 8080;
 
    public static void main(String[] args) throws Exception {
        //setIpAddress(IP_ADDRESS);
        setPort(PORT);
        staticFileLocation("/public");
        new Resource((IDAO) new MongoDBDAO(mongo()));
    }
 
    public static DB mongo() throws Exception {

         MongoClient mongoClient = new MongoClient("localhost", 27017);
         return mongoClient.getDB("pithy_url");

    }
    
}