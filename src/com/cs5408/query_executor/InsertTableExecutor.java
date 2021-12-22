package com.cs5408.query_executor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.cs5408.logging.Logging;
import com.cs5408.file_handler.FIleHandler;
import com.cs5408.file_handler.IFileHandler;
import com.cs5408.model.Constants;
import com.cs5408.model.Database;

public class InsertTableExecutor extends QueryExecutor {
	Logging log=new Logging();
	StateOfDatabase stateOfDatabase=new StateOfDatabase();

	private String tableName;

	private Map<String, Object> values;

	private IFileHandler handler = null;

	@Override
	public void executeQuery(Database database) throws Exception {

		validateQuery(database);
		handler.insertIntoTable(database.getDatabase(), tableName, values);
		log.log("general","New insertion in "+tableName+" in "+database.getDatabase());
		log.log("general",database.getDatabase()+" Status: "+stateOfDatabase.getStatus(database.getDatabase()));

	}

	@Override
	public void validateQuery(Database database) throws Exception {

		if (database == null || database.getDatabase() == null) {
			System.err.println("No Database selected!");
			throw new Exception();
		}

		Path path = Paths.get(Constants.baseFilePath, database.getDatabase());

		if (!Files.exists(path)) {
			System.err.println("Database doesn't exist!");
			throw new Exception();
		}

		path = Paths.get(Constants.baseFilePath, database.getDatabase(), tableName + ".txt");
		if (!Files.exists(path)) {
			System.err.println("Table '" + tableName + "' doesn't exist!");
			throw new Exception();
		}

		Map<String, String> columnProperties = handler.getTableMetaDataColumnProperties(database.getDatabase(),
				tableName);

		List<String> columns = new ArrayList<String>(columnProperties.keySet());
		String columnArray[] = new String[columns.size()];
		columns.toArray(columnArray);

		for (Map.Entry<String, Object> entry : values.entrySet()) {
			if (!Arrays.stream(columnArray).anyMatch(entry.getKey()::equalsIgnoreCase)) {
				System.err.println("Unknown Column '" + entry.getKey() + "'. Check your syntax!");
			}
		}

	}

	public InsertTableExecutor(String tableName, Map<String, Object> values) {
		this.tableName = tableName;
		this.values = values;
		this.handler = new FIleHandler();
	}

}
