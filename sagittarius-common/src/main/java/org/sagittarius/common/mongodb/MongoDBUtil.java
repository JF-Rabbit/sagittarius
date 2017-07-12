package org.sagittarius.common.mongodb;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBUtil {

	private MongoDBConfig config;
	private MongoDBCollection mongoDBCollection;
	private MongoClient mongoClient;

	public MongoDBUtil() {
		super();
	}

	public MongoDBUtil(MongoDBConfig config, MongoDBCollection mongoDBCollection) {
		super();
		this.config = config;
		this.mongoDBCollection = mongoDBCollection;
	}

	public MongoDBConfig getConfig() {
		return config;
	}

	public void setConfig(MongoDBConfig config) {
		this.config = config;
	}

	public MongoDBCollection getMongoDBCollection() {
		return mongoDBCollection;
	}

	public void setMongoDBCollection(MongoDBCollection mongoDBCollection) {
		this.mongoDBCollection = mongoDBCollection;
	}

	public MongoCollection<Document> connect() {
		MongoCredential credential = MongoCredential.createScramSha1Credential(config.getUserName(), config.getDatabaseName(),
				config.getPassword().toCharArray());
		mongoClient = new MongoClient(new ServerAddress(config.getUrl(), config.getPort()), Arrays.asList(credential));
		MongoDatabase database = mongoClient.getDatabase(config.getDatabaseName());
		MongoCollection<Document> collection = database.getCollection(mongoDBCollection.getCollectionName());
		return collection;
	}

	public void close() {
		if (mongoClient != null) {
			mongoClient.close();
		}
	}
}
