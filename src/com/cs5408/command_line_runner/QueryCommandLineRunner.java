package com.cs5408.command_line_runner;

import java.util.Arrays;
import java.util.Scanner;

import com.cs5408.login.user.User;
import com.cs5408.model.Database;
import com.cs5408.parser.QueryParser;
import com.cs5408.parser.TransactionParser;

public class QueryCommandLineRunner {
	private static Database database;

	public void queryCommandLineRunner(Scanner scanner) throws Exception {
		while (true) {
			System.out.println("[1] Enter Queries \n[2] Back");
			System.out.print(">> ");
			
			QueryParser parser = new QueryParser();
			String input;
			switch (scanner.nextLine()) {
			case "1":
				System.out.println("\nWrite your Queries!");
				System.out.print(">> ");
				input=scanner.nextLine();
				if (input.chars().filter(ch -> ch == ';').count() > 1) {
					TransactionParser transaction = new TransactionParser();
					transaction.setQueries(Arrays.asList(input.split(";")));
					parser.setTransaction(true);
					for (String query : transaction.getQueries()) {
						query = query.trim() + ";";
						database = parser.parseQueryAndExecute(query, database, false);
					}
					for (String query : transaction.getQueries()) {
						query = query.trim() + ";";
						database = parser.parseQueryAndExecute(query, database, true);
					}
				} else {
					database = parser.parseQueryAndExecute(input, database, true);
				}
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
