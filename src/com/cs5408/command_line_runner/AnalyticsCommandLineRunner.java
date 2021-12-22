package com.cs5408.command_line_runner;

import com.cs5408.login.user.User;
import com.cs5408.parser.LogParser;

import java.util.Scanner;

public class AnalyticsCommandLineRunner {

    public void analyticsCommandLineRunner(Scanner scanner) throws Exception {
        while (true) {
            System.out.println("[1] Analysis Query \n[2] Back");
            System.out.print(">> ");

            LogParser logParser = new LogParser();
            switch (scanner.nextLine()) {
                case "1":
                    System.out.println("\nWrite the analysis query!");
                    System.out.print(">> ");
                    logParser.parse(scanner.nextLine());
                    break;
                case "2":
                    CommandLineRunner commandLineRunner = new CommandLineRunner();
                    try {
                        commandLineRunner.commandLineRunner();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                default:
                    System.out.println("\nUnknown Input. Please try again!\n");
                    break;
            }
        }
    }
}
