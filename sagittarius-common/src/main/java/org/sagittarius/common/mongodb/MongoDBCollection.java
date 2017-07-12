package org.sagittarius.common.mongodb;

/**
 * MongoDBCollection Collection 枚举
 * 
 * @author jasonzhang 2017年6月5日 上午10:53:41
 *
 */
public class MongoDBCollection {

	private String collectionName;

	public MongoDBCollection() {
		super();
	}

	public MongoDBCollection(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getCollectionName() {
		return collectionName;
	}

}