package org.sagittarius.uitest.web.test.testenum;

import org.sagittarius.common.mongodb.MongoDBCollection;

/**
 * TestMongoDBCollection Collection 枚举
 * 
 * @author jasonzhang 2017年6月5日 上午10:53:41
 *
 */
public enum TestMongoDBCollection {

	COLLECTION("Collection name") {
		@Override
		public MongoDBCollection createMongoDBCollection() {
			return setMongoDBCollection(COLLECTION);
		}
	};

	String collectionName;

	private TestMongoDBCollection(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getCollectionName() {
		return collectionName;
	}

	MongoDBCollection setMongoDBCollection(TestMongoDBCollection collection) {
		MongoDBCollection mongoDBCollection = new MongoDBCollection(collection.getCollectionName());
		return mongoDBCollection;
	}

	public abstract MongoDBCollection createMongoDBCollection();

}