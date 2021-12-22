package com.cs5408.login.factoryMethods;

import com.cs5408.model.Constants;
import com.cs5408.login.user.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class InputHandling {

	public static User inputFromConsole() {

		Scanner sc = new Scanner(System.in);
		System.out.println("> Enter Username Here:");
		String userInput = sc.nextLine();
		String userName = userInput;

		System.out.println("> Enter password Here:");
		userInput = sc.nextLine();
		String passWord = userInput;
		return createUserProfile(userName, passWord);
	}

	public static User createUserProfile(String userName, String passWord) {
		return new User(userName, passWord);
	}
	
	public static boolean login(String username, String password) throws IOException {
		String line;
		BufferedReader reader = new BufferedReader(new FileReader(Constants.loginPath + "/Registered.txt"));

		while ((line = reader.readLine()) != null) {
			String[] inputs = line.split("\\$");

			String userName = inputs[0];

			String pass = inputs[1];

			if (username.equals(userName) && password.equals(pass)) {

				return true;
			}
		}
		

		return false;
	}
}
