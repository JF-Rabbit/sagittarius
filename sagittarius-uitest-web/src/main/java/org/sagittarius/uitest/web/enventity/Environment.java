package org.sagittarius.uitest.web.enventity;

public class Environment {

	private String url;
	private String username;
	private String password;

	public Environment() {
		super();
	}

	public Environment(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Environment [url=" + url + ", username=" + username + ", password=" + password + "]";
	}

}
