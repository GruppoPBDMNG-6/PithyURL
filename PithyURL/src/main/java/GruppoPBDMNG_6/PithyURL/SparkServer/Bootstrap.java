package GruppoPBDMNG_6.PithyURL.SparkServer;
 
import GruppoPBDMNG_6.PithyURL.DataAccess.DAO;

import com.mongodb.*;

import static spark.Spark.setIpAddress;
import static spark.Spark.setPort;
import static spark.SparkBase.staticFileLocation;
 
public class Bootstrap {
    private static final String IP_ADDRESS = "172.26.6.229";
    private static final int PORT = 8080;
 
    public static void main(String[] args) throws Exception {
        setIpAddress(IP_ADDRESS);
        setPort(PORT);
        staticFileLocation("/public");
        new Resource(new DAO(mongo()));
    }
 
    private static DB mongo() throws Exception {

         MongoClient mongoClient = new MongoClient("localhost", 27017);
         return mongoClient.getDB("pithy_url");

    }
    
}