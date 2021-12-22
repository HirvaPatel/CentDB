package com.cs5408.parser;

import java.util.Arrays;
import java.util.List;

import com.cs5408.logging.Logging;
import com.cs5408.model.Database;
import com.cs5408.query_executor.CreateDatabaseExecutor;
import com.cs5408.query_executor.CreateTableExecutor;
import com.cs5408.query_executor.DeleteTableExecutor;
import com.cs5408.query_executor.DropTableExecutor;
import com.cs5408.query_executor.InsertTableExecutor;
import com.cs5408.query_executor.QueryExecutor;
import com.cs5408.query_executor.SelectTableExecutor;
import com.cs5408.query_executor.UpdateTableExecutor;

public class QueryParser {

	boolean transaction = false;

	public boolean isTransaction() {
		return transaction;
	}

	public void setTransaction(boolean transaction) {
		this.transaction = transaction;
	}

	public Database parseQueryAndExecute(String query, Database database, boolean performTransaction) throws Exception {

		if (!query.substring(query.length() - 1).equalsIgnoreCase(";")) {
			System.err.println("Invalid Query Syntax. Missing Semi-Colon !");
			return database;
		}

		if (query.contains("$")) {
			System.err.println("Invalid Query Syntax. '$' not allowed! ");
			return database;
		}

		String[] queryParts = query.split("\\s+");

		List<String> queryPartsList = Arrays.asList(queryParts);

		String queryType = queryPartsList.get(0);

		switch (queryType) {
		case "create":
			CreateParser createParser = new CreateParser(query, queryPartsList);
			database = parse(createParser, database);
			if (performTransaction) {
				if (queryPartsList.get(1).equalsIgnoreCase("database")) {
					executor(new CreateDatabaseExecutor(database.getDatabase(), queryType), database);
				} else if (queryPartsList.get(1).equalsIgnoreCase("table")) {
					executor(new CreateTableExecutor(createParser.getTableName(), createParser.getFieldMap(),
							createParser.getPrimaryKey(), createParser.getForeignKey(),
							createParser.getReferencedTable()), database);
				}
			}
			break;
		case "use":
			database = parse(new UseParser(query, queryPartsList), database);
			break;
		case "insert":
			InsertParser insertParser = new InsertParser(query, queryPartsList);
			database = parse(insertParser, database);
			if (performTransaction) {
				executor(new InsertTableExecutor(insertParser.getTableName(), insertParser.getFieldValueMap()),
						database);
			}
			break;
		case "select":
			SelectParser selectParser = new SelectParser(query, queryPartsList);
			database = parse(selectParser, database);
			if (performTransaction) {
				executor(new SelectTableExecutor(selectParser.getTableName(), selectParser.getColumns(),
						selectParser.getConditions()), database);
			}
			break;
		case "update":
			UpdateParser updateParser = new UpdateParser(queryType, queryPartsList);
			database = parse(updateParser, database);
			if (performTransaction) {
				executor(new UpdateTableExecutor(updateParser.getTableName(), updateParser.getColumns(),
						updateParser.getValues(), updateParser.getConditions()), database);
			}
			break;
		case "delete":
			DeleteParser deleteParser = new DeleteParser(queryType, queryPartsList);
			database = parse(deleteParser, database);
			if (performTransaction) {
				executor(new DeleteTableExecutor(deleteParser.getTableName(), deleteParser.getConditions()), database);
			}
			break;
		case "drop":
			DropParser dropParser = new DropParser(queryType, queryPartsList);
			database = parse(dropParser, database);
			if (performTransaction) {
				executor(new DropTableExecutor(dropParser.getTableName()), database);
			}
			break;
		default:
			System.err.println("Invalid Query syntax! Try again!");
			break;
		}
		return database;

	}

	public Database parse(Parser parser, Database database) throws Exception {

		try {
			database = parser.parse(database);
		} catch (Exception e) {
			log.log("event","Exception occurred while executing queries");
			e.printStackTrace();
		}
		return database;

	}

	Logging log=new Logging();
	public void executor(QueryExecutor executor, Database database) throws Exception {
		try {
			executor.executeQuery(database);
		} catch (Exception ex) {
			log.log("event","Exception occurred while executing queries");
			ex.printStackTrace();
		}
	}

}
