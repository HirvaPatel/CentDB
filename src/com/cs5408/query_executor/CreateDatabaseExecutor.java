package com.cs5408.query_executor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.cs5408.logging.Logging;
import com.cs5408.model.Constants;
import com.cs5408.model.Database;

public class CreateDatabaseExecutor extends QueryExecutor {
	Logging log=new Logging();

	private String databaseName;

	private String query;

	public CreateDatabaseExecutor(String databaseName, String query) {
		this.databaseName = databaseName;
	}

	@Override
	public void executeQuery(Database database) throws Exception {

		validateQuery(database);
		File databaseDir = new File(Constants.baseFilePath + databaseName);
		if (!databaseDir.mkdir()) {
			System.err.println("Something went wrong!");
		}
		log.log("general","New database "+databaseName+" created");
		System.out.println("Created database - " + databaseName);
	}

	@Override
	public void validateQuery(Database database) throws Exception {
		Path path = Paths.get(Constants.baseFilePath, databaseName);
		if (Files.exists(path)) {
			System.err.println("Database '" + databaseName + "' already exists!");
			throw new Exception();
		}
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

}
