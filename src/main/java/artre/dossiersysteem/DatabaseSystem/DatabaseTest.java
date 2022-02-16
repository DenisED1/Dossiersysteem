package artre.dossiersysteem.DatabaseSystem;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DatabaseTest {
	protected final static String username = "DenisED1";
	protected final static String password = "AbCd1996";
	protected final static String clusterName = "AfstudeerstageArtre";
	protected final static String databaseName = "sample_geospatial";
	protected final static String collectionName = "shipwrecks";

	public static void main(String[] args) {
		MongoClient mongoClient = MongoClients.create("mongodb+srv://" + username + ":" + password + "@" + clusterName
				+ ".z00oy.mongodb.net/" + clusterName + "?retryWrites=true&w=majority");

		MongoDatabase database = mongoClient.getDatabase(databaseName);
		MongoCollection<Document> collection = database.getCollection(collectionName);
		Document query = new Document("_id", new ObjectId("578f6fa2df35c7fbdbaed8c4"));
		Document result = collection.find(query).iterator().next();

		System.out.println("Feature_type: " + result.getString("feature_type"));
	}

}
