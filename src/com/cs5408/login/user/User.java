package com.cs5408.login.user;

import java.io.Serializable;

public class User implements Serializable {
	
	private final String userName;
	private final String password;
	private boolean _LoggedIn=false;

	public User(String userName,String password)
	{
		this.userName=userName;
		this.password=password;
	}

	public boolean _isLoggedIn() {
		return _LoggedIn;
	}
	public void set_isLoggedIn(boolean _isLoggedIn) {
		this._LoggedIn = _isLoggedIn;
	}

	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
}
