package com.cs5408.command_line_runner;

import com.cs5408.logging.Logging;
import com.cs5408.login.user.User;

import java.util.Scanner;

public class CommandLineRunner {
	public void commandLineRunner() throws Exception {
		while (true) {
			Logging log=new Logging();
			Scanner scanner = new Scanner(System.in);
			System.out.println("Select an option (Enter the option number) : ");
			System.out.println("[1] Write Queries \n[2] Export \n[3] Data Model \n[4] Analytics \n[5] Exit");
			System.out.print(">> ");
			
			switch (scanner.nextLine()) {
			case "1":
				log.log("event","Write Queries option chosen");
				QueryCommandLineRunner queryCommandLinerRunner = new QueryCommandLineRunner();
				queryCommandLinerRunner.queryCommandLineRunner(scanner);
				break;
			case "2":
				log.log("event","Export Structure and Values option chosen");
				ExportCommandLineRunner exportCommandLineRunner = new ExportCommandLineRunner();
				exportCommandLineRunner.exportCommandLineRunner(scanner);
				break;
			case "3":
				log.log("event","Data Model option chosen");
				DataModelCommandLineRunner dataModelCommandLineRunner = new DataModelCommandLineRunner();
				dataModelCommandLineRunner.dataModelCommandLineRunner(scanner);
				break;
			case "4":
				log.log("event","Analytics option chosen");
				AnalyticsCommandLineRunner analyticsCommandLineRunner = new AnalyticsCommandLineRunner();
				analyticsCommandLineRunner.analyticsCommandLineRunner(scanner);
				break;
			case "5":
				System.out.println("System Closed !!");
				System.exit(0);
			default:
				System.out.println("\nUnknown Input. Please try again!\n");
				break;
			}
		}	

	}

}
