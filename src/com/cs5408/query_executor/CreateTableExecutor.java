package com.cs5408.query_executor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

import com.cs5408.logging.Logging;
import com.cs5408.file_handler.FIleHandler;
import com.cs5408.file_handler.IFileHandler;
import com.cs5408.model.Constants;
import com.cs5408.model.Database;

public class CreateTableExecutor extends QueryExecutor {
	Logging log=new Logging();
	StateOfDatabase stateOfDatabase=new StateOfDatabase();

	private String tableName;
	private String primaryKey;
	private String foreignKey;
	private String referencedTable;
	private Map<String, String> fields;

	@Override
	public void executeQuery(Database database) throws Exception {

		validateQuery(database);
		IFileHandler fileHandler = new FIleHandler();
		fileHandler.createTable(database.getDatabase(), tableName, fields, primaryKey, foreignKey, referencedTable);
		log.log("general","New table "+tableName+" created in "+database.getDatabase());
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
		if (Files.exists(path)) {
			System.err.println("Table '" + tableName + "' already exist!");
			throw new Exception();
		}

		String datatypes[] = new String[] { "int", "bigint", "tinyint", "varchar", "text", "longtext", "date",
				"datetime", "time", "timestamp", "decimal", "float", "double", "boolean" };
		for (String field : fields.values()) {
			if (!Arrays.stream(datatypes).anyMatch(field::equalsIgnoreCase)) {
				System.err.println("Invalid Syntax : Invalid datatype!");
				throw new Exception();
			}
		}
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public CreateTableExecutor(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

	public CreateTableExecutor(String tableName, Map<String, String> fields, String primaryKey, String foreignKey, String referencedTable) {
		super();
		this.tableName = tableName;
		this.fields = fields;
		this.primaryKey = primaryKey;
		this.foreignKey = foreignKey;
		this.referencedTable = referencedTable;
	}

}
