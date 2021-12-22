package com.cs5408.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cs5408.logging.Logging;
import com.cs5408.model.Database;

public class DeleteParser extends Parser {

	public Database parse(Database database) throws Exception {
		Logging log=new Logging();
		if (!queryPartsList.get(0).equalsIgnoreCase("delete")) {
			System.err.println("Invalid Query syntax! Try again!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
			return database;
		}

		int fromKeywordIndex = queryPartsList.indexOf("from");
		if (fromKeywordIndex == -1) {
			System.err.println("Invalid Query syntax! Try again!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
			return database;
		}

		tableName = queryPartsList.get(fromKeywordIndex + 1).replace(";", "");
		log.log("query",query+" ?"+"Valid |"+database.getDatabase());
		int whereIndex = queryPartsList.indexOf("where");
		if(whereIndex != -1) {
			String[] whereCondition = queryPartsList.get(whereIndex + 1).split("=");
			conditions = new HashMap<>();
			String var = whereCondition[0];
			String value = whereCondition[1];
			conditions.put(var, value.replaceAll(",", "").replaceAll(";", ""));
		}
		return database;
	}

	String tableName = null;

	Map<String, String> conditions;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Map<String, String> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, String> conditions) {
		this.conditions = conditions;
	}

	public DeleteParser(String query, List<String> queryPartsList) {
		super(query, queryPartsList);
	}

}
