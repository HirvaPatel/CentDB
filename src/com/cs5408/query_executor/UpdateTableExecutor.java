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

public class UpdateTableExecutor extends QueryExecutor {

	Logging log=new Logging();
	StateOfDatabase stateOfDatabase=new StateOfDatabase();

	private String tableName;

	private List<String> columns;

	private List<String> values;

	private IFileHandler handler = null;

	private Map<String, String> conditions;

	@Override
	public void executeQuery(Database database) throws Exception {

		validateQuery(database);
		handler.updateTable(database.getDatabase(), tableName, columns, values, conditions);
		log.log("general","updating "+tableName+" in "+database.getDatabase());
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
			System.err.println("Table '" + tableName + "' dosen't exist!");
			throw new Exception();
		}

		Map<String, String> columnProperties = handler.getTableMetaDataColumnProperties(database.getDatabase(),
				tableName);

		List<String> columnsMetaData = new ArrayList<String>(columnProperties.keySet());
		String columnArray[] = new String[columnsMetaData.size()];
		columnsMetaData.toArray(columnArray);

		for (int i = 0; i < columns.size(); i++) {
			if (!Arrays.stream(columnArray).anyMatch(columns.get(i)::equalsIgnoreCase)) {
				System.err.println("Unknown Column '" + columns.get(i) + "'. Check your syntax!");
			}
		}

	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public UpdateTableExecutor(String tableName, List<String> columns, List<String> values,
			Map<String, String> conditions) {
		super();
		this.tableName = tableName;
		this.columns = columns;
		this.values = values;
		this.handler = new FIleHandler();
		this.conditions = conditions;
	}

}
