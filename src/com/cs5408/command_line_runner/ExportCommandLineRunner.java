package com.cs5408.command_line_runner;

import java.io.IOException;
import java.util.Scanner;

import com.cs5408.export_structure_values.ExportModel;
import com.cs5408.login.user.User;

public class ExportCommandLineRunner {
    public void exportCommandLineRunner(Scanner scanner) throws IOException {
        while (true) {
            System.out.println("[1] With Values \n[2] Without Values \n[3] Back");
            System.out.print(">> ");

            ExportModel exportModel = new ExportModel();
            switch (scanner.nextLine()) {
                case "1":
                    System.out.println("\nWrite the database name!");
                    System.out.print(">> ");
                    exportModel.ExportWithValues(scanner.nextLine().toLowerCase());
                    break;
                case "2":
                    System.out.println("\nWrite the database name!");
                    System.out.print(">> ");
                    exportModel.ExportWithoutValues(scanner.nextLine().toLowerCase());
                    break;
                case "3":
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

