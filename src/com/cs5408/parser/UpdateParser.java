package com.cs5408.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cs5408.logging.Logging;
import com.cs5408.model.Database;

public class UpdateParser extends Parser {

	public Database parse(Database database) throws Exception {
		Logging log=new Logging();
		if (!queryPartsList.get(0).equalsIgnoreCase("update")) {
			System.err.println("Invalid Query syntax! Try again!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
			return database;
		}
		tableName = queryPartsList.get(1);
		System.out.println("Table Name - " + tableName);

		if (!queryPartsList.get(2).equalsIgnoreCase("set")) {
			System.err.println("Invalid Query syntax! Try again!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
			return database;
		}

		int whereIndex = queryPartsList.indexOf("where");
		if (whereIndex == -1) {
			for (int i = 3; i < queryPartsList.size(); i++) {
				String[] properties = queryPartsList.get(i).split("=");
				columns.add(properties[0]);
				values.add(properties[1].replaceAll(",", "").replaceAll(";", ""));
			}
		} else {

			for (int i = 3; i < whereIndex; i++) {
				String[] properties = queryPartsList.get(i).split("=");
				columns.add(properties[0]);
				values.add(properties[1].replaceAll(",", "").replaceAll(";", ""));
			}

			String[] whereCondition = queryPartsList.get(whereIndex + 1).split("=");
			conditions = new HashMap<>();
			String var = whereCondition[0];
			String value = whereCondition[1];
			conditions.put(var, value.replaceAll(",", "").replaceAll(";", ""));
		}
		log.log("query",query+" ?"+"Valid |"+database.getDatabase());
		log.log("query",query+" ?"+"Valid |"+database.getDatabase());
		return database;
	}

	String tableName = null;
	List<String> columns = new ArrayList<>();
	List<String> values = new ArrayList<>();
	Map<String, String> conditions;

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

	public UpdateParser(String query, List<String> queryPartsList) {
		super(query, queryPartsList);
	}

	public Map<String, String> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, String> conditions) {
		this.conditions = conditions;
	}

}
