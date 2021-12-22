package com.cs5408.login.printer;

import com.cs5408.model.Constants;
import com.cs5408.login.user.User;
import com.cs5408.login.viewUser.DisplayLoginUser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Printer {

	public static boolean contains(String userName) throws IOException {
		String line = "";
		BufferedReader br = new BufferedReader(new FileReader(Constants.loginPath + "/Registered.txt"));
		while ((line = br.readLine()) != null) {
			String inputs[] = line.split("\\$");
			String username = inputs[0];

			if (username.equals(userName)) {
				return true;
			}

		}

		return false;
	}

	public static List<User> registerUserMap(User user, List<User> userList) throws Exception {
		DisplayLoginUser dlu = new DisplayLoginUser();
		user.set_isLoggedIn(true);
		userList = dlu.logOutUser(userList);
		userList.add(user);
		return userList;
	}

	public static void addListOfUsersIntoTheRegisterFile(List<User> userList) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.loginPath + "/Registered.txt", true));
		for (User user : userList) {
			writer.write(user.getUserName() + "$" + user.getPassword() + "\n");
		}
		writer.close();

	}

	public static void addListOfUsersIntoTheUserFile(List<User> userList) throws Exception {
		BufferedWriter writer1 = new BufferedWriter(new FileWriter(Constants.loginPath + "/Loggedin.txt"));
		for (User user : userList) {
			writer1.write(user.getUserName() + "$" + user.getPassword() + "\n");
		}
		writer1.close();

	}
	/*
	 * FileOutputStream fo=new FileOutputStream("User.txt"); ObjectOutputStream
	 * stream=new ObjectOutputStream (fo); stream.writeObject(userList);
	 * stream.flush(); stream.close();
	 */

}
