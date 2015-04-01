package sdid.ws.impl;

public class User {
	
	private String userId;
	private String emailAddress;
	private String password;
	private String token;
	
	public User(String userId, String emailAddress, String password) {
		this.userId = userId;
		this.emailAddress = emailAddress;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	
	
	
	

}
