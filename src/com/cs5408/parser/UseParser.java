package com.cs5408.parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.cs5408.logging.Logging;
import com.cs5408.model.Constants;
import com.cs5408.model.Database;

public class UseParser extends Parser {
	public UseParser(String query, List<String> queryPartsList) {
		super(query, queryPartsList);
	}
	Logging log=new Logging();
	public Database parse(Database database) throws Exception {
		String databaseName = queryPartsList.get(1);
		databaseName = databaseName.replace(";", "");
		Path path = Paths.get(Constants.baseFilePath, databaseName);
		if (!Files.exists(path)) {
			System.err.println("Database doesn't exist!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
			return database;
		}
		database = new Database(databaseName);
		log.log("query",query+" ?"+"Valid |"+database.getDatabase());
		return database;
	}
}
