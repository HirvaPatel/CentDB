package com.cs5408.login.viewUser;

import com.cs5408.login.user.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Menu {
	
	public List<User> registration(List<User> userList) throws Exception;
	public List<User> login(List<User> userList) throws Exception;
	public List<User> logOutUser(List<User> userList) throws Exception;
	public List<User> isLoggedIn(List<User> userList) throws FileNotFoundException, IOException;
	
	

}
