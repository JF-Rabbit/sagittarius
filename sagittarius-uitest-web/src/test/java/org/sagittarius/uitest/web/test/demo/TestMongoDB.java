package org.sagittarius.uitest.web.test.demo;

import org.bson.Document;
import org.junit.Test;
import org.sagittarius.common.mongodb.MongoDBUtil;
import org.sagittarius.uitest.web.test.testenum.TestMongoDBCollection;
import org.sagittarius.uitest.web.test.testenum.TestMongoDBConfig;

import com.mongodb.client.MongoCollection;

public class TestMongoDB {

	@Test
	public void test() {
		
		MongoDBUtil mongoDBUtil = new MongoDBUtil(TestMongoDBConfig.CONFIG.createMongoDBConfig(), TestMongoDBCollection.COLLECTION.createMongoDBCollection());
		MongoCollection<Document> collection = mongoDBUtil.connect();
		System.out.println(collection.count());
		mongoDBUtil.close();
	}
}