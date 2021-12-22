package com.cs5408.parser;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import com.cs5408.logging.Logging;
import com.cs5408.model.Database;

public class CreateParser extends Parser {

	public Database parse(Database database) throws Exception {
		Logging log=new Logging();

		if (queryPartsList.get(1).equalsIgnoreCase("database")) {
			String databaseName = queryPartsList.get(2);
			databaseName = databaseName.replace(";", "");
			database = new Database(databaseName);

		} else if (queryPartsList.get(1).equalsIgnoreCase("table")) {

			String tableProperties = queryPartsList.get(2);
			int i = tableProperties.indexOf("(");
			tableName = tableProperties.substring(0, i).strip();
			System.out.println("Table Name - " + tableName);

			i = query.indexOf("(");
			String properties = query.substring(i + 1, query.length() - 2);

			List<String> fields = Arrays.asList(properties.split(","));

			for (String s : fields) {
				s = s.strip();
				List<String> individualFields = Arrays.asList(s.split("\\s+"));
				if (individualFields.get(0).toString().equals("PRIMARY")) {
					primaryKey = individualFields.get(2).toString().replaceAll("[\\(\\)]", "");
					continue;
				} else if (individualFields.get(0).toString().equals("FOREIGN")) {
					foreignKey = individualFields.get(2).toString().replaceAll("[\\(\\)]", "");
					referencedTable = individualFields.get(4).toString().replaceAll("\\(.*", "");
					continue;
				}
				fieldMap.put(individualFields.get(0), individualFields.get(1));
			}
			log.log("query",query+" ?"+"Valid |"+database.getDatabase());
		} else {
			System.err.println("Invalid Query syntax! Try again!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
		}

		return database;
	}

	String tableName = null;
	LinkedHashMap<String, String> fieldMap = new LinkedHashMap<>();
	String primaryKey = null;
	String foreignKey = null;
	String referencedTable = null;
	Database database = null;

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getForeignKey() {
		return foreignKey;
	}

	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}

	public String getReferencedTable() {
		return referencedTable;
	}

	public void setReferencedTable(String referencedTable) {
		this.referencedTable = referencedTable;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public LinkedHashMap<String, String> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(LinkedHashMap<String, String> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public CreateParser(String query, List<String> queryPartsList) {
		super(query, queryPartsList);
	}

}
