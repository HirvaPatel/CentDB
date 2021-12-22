package com.cs5408.command_line_runner;

import java.io.IOException;
import java.util.Scanner;

import com.cs5408.data_modelling.DataModel;
import com.cs5408.login.user.User;

public class DataModelCommandLineRunner {
    public void dataModelCommandLineRunner(Scanner scanner) throws IOException {
        while (true) {
            System.out.println("[1] Generate ERD \n[2] Back");
            System.out.print(">> ");

            DataModel dataModel = new DataModel();
            switch (scanner.nextLine()) {
                case "1":
                    System.out.println("\nWrite the database name!");
                    System.out.print(">> ");
                    dataModel.createERD(scanner.nextLine().toLowerCase());
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
