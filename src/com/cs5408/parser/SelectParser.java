package com.cs5408.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cs5408.logging.Logging;
import com.cs5408.file_handler.FIleHandler;
import com.cs5408.model.Database;

public class SelectParser extends Parser {

	public Database parse(Database database) throws Exception {
		Logging log=new Logging();
		int i, j;
		if (!queryPartsList.get(0).equalsIgnoreCase("select")) {
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

		System.out.println("Table Name - " + tableName);

		i = queryPartsList.indexOf("select") + 1;
		j = queryPartsList.indexOf("from") - 1;

		for (int index = i; index <= j; index++) {
			String element = queryPartsList.get(index);
			element = element.contains(",") ? element.replace(",", "") : element;
			columns.add(element);
		}

		if (columns.size() == 0) {
			System.err.println("Invalid Query syntax! Try again!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
			throw new Exception();
		}

		i = queryPartsList.indexOf("where");
		if (i != -1) {
			String var = queryPartsList.get(i + 1).strip();
			String eq = queryPartsList.get(i + 2).strip();
			if (!eq.equalsIgnoreCase("=")) {
				System.err.println("Invalid Query syntax! Try again!");
				throw new Exception();
			}
			String value = queryPartsList.get(i + 3).replaceAll(";", "").strip();

			Map<String, String> colums = new FIleHandler().getTableMetaDataColumnProperties(database.getDatabase(),
					tableName);
			if (!colums.containsKey(var)) {
				System.err.println("Unknown column - " + var + " !");
				throw new Exception();
			}
			conditions = new HashMap<>();
			conditions.put(var, value);
		}
		log.log("query",query+" ?"+"Valid |"+database.getDatabase());
		return database;
	}

	String tableName = null;
	List<String> columns = new ArrayList<String>();
	Database database = null;
	Map<String, String> conditions = null;

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

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public Map<String, String> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, String> conditions) {
		this.conditions = conditions;
	}

	public SelectParser(String query, List<String> queryPartsList) {
		super(query, queryPartsList);
	}
}
