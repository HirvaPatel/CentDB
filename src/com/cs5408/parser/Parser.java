package com.cs5408.parser;

import java.util.List;

import com.cs5408.model.Database;
import com.cs5408.query_executor.QueryExecutor;

public abstract class Parser {

	public Parser(String query, List<String> queryPartsList) {
		super();
		this.query = query;
		this.queryPartsList = queryPartsList;
	}

	String query;

	List<String> queryPartsList;

	public Database parse(Database database) throws Exception {

		return null;
	}

	public void executor(QueryExecutor executor, Database database) throws Exception {
		try {
			executor.executeQuery(database);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
