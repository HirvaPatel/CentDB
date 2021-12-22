package com.cs5408.login.viewUser;

import com.cs5408.login.factoryMethods.InputHandling;
import com.cs5408.login.printer.Printer;
import com.cs5408.login.user.User;
import com.cs5408.model.Constants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class DisplayLoginUser implements Menu {

    public static void userUI() {
        Stream.generate(() -> "=").limit(52).forEach(System.out::print);
        System.out.println("\n");
        System.out.println("Welcome to the Login Portal");

        Stream.generate(() -> "=").limit(52).forEach(System.out::print);
        System.out.println("\n");
        System.out.println("->" + "[1] Registration");
        System.out.println("->" + "[2] Login");
        System.out.println("->" + "[3] Exit");
        Stream.generate(() -> "=").limit(52).forEach(System.out::print);
    }

    @Override
    public List<User> registration(List<User> userList) throws Exception {

        User user = InputHandling.inputFromConsole();// make new class like Factory.

//		if (Printer.contains(user.getUserName())) {
//			System.out.println("> username already exist");
//
//			//registration(userList);
//			userUI();
//			System.exit(0);
//		}
        if (Printer.contains(user.getUserName())) {
            System.out.println("> username already exist");
            userList.add(user);
            userList = Printer.registerUserMap(user, userList);

            System.out.println("> Login Again");
            return userList;
        }
        Stream.generate(() -> "=").limit(52).forEach(System.out::print);
        System.out.println("\n");
        System.out.println("User:-" + user.getUserName() + "-:registered");
        try {
            userList.add(user);
            userList = Printer.registerUserMap(user, userList);
            Printer.addListOfUsersIntoTheRegisterFile(userList);
            System.out.println("Login Again");
//			userUI();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return userList;

    }

    @Override
    public List<User> login(List<User> userList) throws Exception {

        Stream.generate(() -> "=").limit(52).forEach(System.out::print);
        System.out.println("\n");
        Scanner sc = new Scanner(System.in);
        System.out.println("> Enter Username Here:");
        String userInput = sc.nextLine();
        String userName = userInput;

        Stream.generate(() -> "=").limit(52).forEach(System.out::print);
        System.out.println("\n");
        Scanner sc1 = new Scanner(System.in);
        System.out.println("> Enter Password Here:");
        String userInput1 = sc1.nextLine();
        String passWord = userInput1;

        boolean isUserPresent = InputHandling.login(userName, passWord);

        if (isUserPresent) {
            System.out.println("Login Successful");
            User user = InputHandling.createUserProfile(userName, passWord);// create method in Factory
            userList = isLoggedIn(userList);
            //userList.add(user);

            userList = Printer.registerUserMap(user, userList);
            Printer.addListOfUsersIntoTheUserFile(userList);

            Stream.generate(() -> "=").limit(52).forEach(System.out::print);
            System.out.println("\n");
            System.out.println("User:-" + user.getUserName() + "-:Logged IN");

        } else {
            System.out.println("Login failed.");
            System.out.println(">Incorrect Input");
            login(userList);
        }
        return userList;
    }

    @Override
    public List<User> logOutUser(List<User> userList) throws Exception {
        List<User> newListOfUser = new ArrayList<>();
        for (User user : userList) {
            if (user._isLoggedIn()) {
                user.set_isLoggedIn(false);
                Stream.generate(() -> "=").limit(52).forEach(System.out::print);
                System.out.println("");
            } else {
                newListOfUser.add(user);
            }
        }
        Printer.addListOfUsersIntoTheUserFile(newListOfUser);
        return newListOfUser;

    }

    @Override
    public List<User> isLoggedIn(List<User> userList) throws IOException {

        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(Constants.loginPath + "/Registered.txt"));
        while ((line = br.readLine()) != null) {
            String inputs[] = line.split("\\$");
            String username = inputs[0];
            String password = inputs[1];
            for (User u : userList) {
                if (username.equals(u.getUserName()) && (password.equals(u.getPassword()))) {
                    u.set_isLoggedIn(true);
                }
            }

        }
        return userList;
    }

}
