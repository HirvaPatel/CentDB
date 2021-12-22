package com.cs5408.command_line_runner;

import com.cs5408.login.user.User;
import com.cs5408.login.viewUser.DisplayLoginUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class LoginCommandLineRunner {

    public static User user;

    public User getUser(){
        return this.user;
    }
    public User loginRunner() throws Exception {
        List<User> userList = new ArrayList<>();
        while (true) {
            DisplayLoginUser.userUI();
            Stream.generate(() -> "=").limit(52).forEach(System.out::print);
            System.out.println("\n");
            System.out.println("-> Enter your choice:");
            Scanner sc = new Scanner(System.in);
            String userInput = sc.nextLine();
            int option = Integer.parseInt(userInput);
            Stream.generate(() -> "=").limit(52).forEach(System.out::print);

            userList = userChoice(option, userList);
            if (userInput.equals("exit")) {
                System.out.println("> System breaks:");
                break;
            }
            if(userList.get(0)._isLoggedIn()){
                this.user = userList.get(0);
                break;
            }
        }
        return userList.get(0);
    }

    public List<User> userChoice(int choice, List<User> userList) throws Exception {
        DisplayLoginUser dlu = new DisplayLoginUser();
        switch (choice) {
            case 1:
                Stream.generate(() -> "=").limit(52).forEach(System.out::print);
                System.out.println("\n");
                System.out.println("> Registering User............................................");
                userList = dlu.registration(userList);
                break;
            case 2:
                Stream.generate(() -> "=").limit(52).forEach(System.out::print);
                System.out.println("\n");
                System.out.println("> Login credentials entered:");
                userList = dlu.login(userList);
                break;
            case 3:
                Stream.generate(() -> "=").limit(52).forEach(System.out::print);
                System.out.println("\n");
                System.out.println("> LogOut Processing:");
                userList = dlu.logOutUser(userList);
                break;
            default:
                System.out.println("> ENTER VALID INPUT OR COMMAND");
                break;
        }
        return userList;
    }
}