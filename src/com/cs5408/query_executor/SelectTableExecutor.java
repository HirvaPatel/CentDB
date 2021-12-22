package com.cs5408.query_executor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.cs5408.file_handler.FIleHandler;
import com.cs5408.file_handler.IFileHandler;
import com.cs5408.model.Constants;
import com.cs5408.model.Database;

public class SelectTableExecutor extends QueryExecutor {

	private String tableName;

	private List<String> values;

	private IFileHandler handler = null;

	private Map<String, String> conditions = null;

	@Override
	public void executeQuery(Database database) throws Exception {

		validateQuery(database);
		try {
			handler.selectTable(database.getDatabase(), tableName, values, conditions);
		} catch (Exception e) {
			System.err.println("No Records Found!");

		}

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

		List<String> columns = new ArrayList<String>(columnProperties.keySet());
		String columnArray[] = new String[columns.size()];
		columns.toArray(columnArray);

		if (values.get(0).equals("*") && values.size() == 1) {
		} else {
			for (int i = 0; i < values.size(); i++) {
				if (!Arrays.stream(columnArray).anyMatch(values.get(i)::equalsIgnoreCase)) {
					System.err.println("Unknown Column '" + values.get(i) + "'. Check your syntax!");
				}
			}
		}
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public SelectTableExecutor(String tableName, List<String> values, Map<String, String> conditions) {
		super();
		this.tableName = tableName;
		this.values = values;
		this.handler = new FIleHandler();
		this.conditions = conditions;

	}

}
