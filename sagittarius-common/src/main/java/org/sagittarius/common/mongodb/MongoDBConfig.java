package org.sagittarius.common.mongodb;

/**
 * MongoDBConfig MongoDB配置枚举
 * 
 * @author jasonzhang 2017年6月5日 上午10:54:01
 *
 */
public class MongoDBConfig {

	private String name;
	private String userName;
	private String databaseName;
	private String password;
	private String url;
	private int port;

	public MongoDBConfig() {
		super();
	}

	public MongoDBConfig(String name, String databaseName, String url, int port) {
		this.name = name;
		this.databaseName = databaseName;
		this.url = url;
		this.port = port;
	}

	public MongoDBConfig(String name, String userName, String databaseName, String password, String url, int port) {
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

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "MongoDBConfig [name=" + name + ", userName=" + userName + ", databaseName=" + databaseName + ", password=" + password
				+ ", url=" + url + ", port=" + port + "]";
	}

}
