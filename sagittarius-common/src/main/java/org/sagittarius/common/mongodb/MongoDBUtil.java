package org.sagittarius.common.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.sagittarius.common.FunctionUtil;

import java.util.Arrays;

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
        MongoCredential credential = null;
        if (FunctionUtil.NOT_EMPTY_STR.test(config.getUserName()) && FunctionUtil.NOT_EMPTY_STR.test(config.getPassword())) {
            credential = MongoCredential.createScramSha1Credential(config.getUserName(), config.getDatabaseName(),
                    config.getPassword().toCharArray());
        }
        if (credential != null) {
            mongoClient = new MongoClient(new ServerAddress(config.getUrl(), config.getPort()), Arrays.asList(credential));
        } else {
            mongoClient = new MongoClient(new ServerAddress(config.getUrl(), config.getPort()));
        }

        MongoDatabase database = mongoClient.getDatabase(config.getDatabaseName());
        return database.getCollection(mongoDBCollection.getCollectionName());
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
