package org.sagittarius.uitest.web.test.testenum;

import org.sagittarius.common.mongodb.MongoDBConfig;

/**
 * TestMongoDBConfig MongoDB配置枚举
 * 
 * @author jasonzhang 2017年6月5日 上午10:54:01
 *
 */
public enum TestMongoDBConfig {

	CONFIG("Config name", "userName", "databaseName", "password", "xxx.xxx.xxx.xxx", 8080) {
		@Override
		public MongoDBConfig createMongoDBConfig() {
			return setsetMongoDBConfig(CONFIG);
		}

	};

	String name;
	String userName;
	String databaseName;
	String password;
	String url;
	int port;

	private TestMongoDBConfig(String name, String userName, String databaseName, String password, String url, int port) {
		this.name = name;
		this.userName = userName;
		this.databaseName = databaseName;
		this.password = password;
		this.url = url;
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public String getUserName() {
		return userName;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}

	public int getPort() {
		return port;
	}

	MongoDBConfig setsetMongoDBConfig(TestMongoDBConfig config) {
		MongoDBConfig mongoDBConfig = new MongoDBConfig(config.getName(), config.getUserName(), config.getDatabaseName(),
				config.getPassword(), config.getUrl(), config.getPort());
		return mongoDBConfig;
	}

	public abstract MongoDBConfig createMongoDBConfig();

}
