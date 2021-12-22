package com.cs5408.parser;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import com.cs5408.logging.Logging;
import com.cs5408.model.Database;

public class InsertParser extends Parser {

	public Database parse(Database database) throws Exception {
		Logging log=new Logging();
		int i, j;
		if (!queryPartsList.get(1).equalsIgnoreCase("into")) {
			System.err.println("Invalid Query syntax! Try again!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
			return database;
		}
		tableName = queryPartsList.get(2);
		i = tableName.indexOf("(");
		tableName = tableName.substring(0, i > 0 ? i : tableName.length());

		i = query.indexOf("(");
		j = query.indexOf(")");

		if (i == -1 || j == -1) {
			System.err.println("Invalid Query syntax! Try again!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
			return database;
		}

		String fields = query.substring(i + 1, j);

		String values = query.substring(j + 1);
		i = values.indexOf("(");
		j = values.indexOf(")");

		String keyword = values.substring(0, i);

		if (!keyword.strip().equalsIgnoreCase("values")) {
			System.err.println("Invalid Query syntax! Try again!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
			return database;
		}

		values = values.substring(i + 1, j);

		fields = fields.strip();
		values = values.strip();

		List<String> fieldList = Arrays.asList(fields.split(","));
		List<String> valuesList = Arrays.asList(values.split(","));

		if (fieldList.size() != valuesList.size()) {
			System.err.println("Invalid Query syntax! Try again!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
			return database;
		}

		String field = "", value = "";
		for (int f = 0; f < fieldList.size(); f++) {
			field = fieldList.get(f).strip();
			value = valuesList.get(f).replace("\"", "").strip();
			fieldValueMap.put(field, value);
		}
		log.log("query",query+" ?"+"Valid |"+database.getDatabase());
		return database;
	}

	String tableName = null;
	LinkedHashMap<String, Object> fieldValueMap = new LinkedHashMap<>();
	Database database = null;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public LinkedHashMap<String, Object> getFieldValueMap() {
		return fieldValueMap;
	}

	public void setFieldValueMap(LinkedHashMap<String, Object> fieldValueMap) {
		this.fieldValueMap = fieldValueMap;
	}

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public InsertParser(String query, List<String> queryPartsList) {
		super(query, queryPartsList);
	}
}
