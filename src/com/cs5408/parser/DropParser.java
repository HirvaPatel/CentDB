package com.cs5408.parser;

import java.util.List;

import com.cs5408.logging.Logging;
import com.cs5408.model.Database;

public class DropParser extends Parser{

	public Database parse(Database database) throws Exception {
		Logging log=new Logging();
		if (!queryPartsList.get(0).equalsIgnoreCase("drop")) {
			System.err.println("Invalid Query syntax! Try again!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
			return database;
		}
		
		int fromKeywordIndex = queryPartsList.indexOf("table");
		if(fromKeywordIndex == -1) {
			System.err.println("Invalid Query syntax! Try again!");
			log.log("query",query+" ?"+"Invalid |"+database.getDatabase());
			return database;
		}
		
		tableName = queryPartsList.get(fromKeywordIndex + 1).replace(";", "");
		log.log("query",query+" ?"+"Valid |"+database.getDatabase());
		return database;
	}
	
	String tableName = null;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public DropParser(String query, List<String> queryPartsList) {
		super(query, queryPartsList);
	}

}
