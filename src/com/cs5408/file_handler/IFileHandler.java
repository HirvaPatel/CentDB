package com.cs5408.file_handler;

import java.util.List;
import java.util.Map;

public interface IFileHandler {

	public void createTable(String databaseName, String tableName, Map<String, String> properties, String primaryKey,
			String foreignKey, String referencedTable) throws Exception;

	public Map<String, String> getTableMetaDataColumnProperties(String databaseName, String tableName) throws Exception;

	public void insertIntoTable(String databaseName, String tableName, Map<String, Object> values) throws Exception;

	public Map<String, String> selectTable(String databaseName, String tableName, List<String> values,
			Map<String, String> conditions) throws Exception;

	public void deleteTable(String databaseName, String tableName, Map<String, String> conditions) throws Exception;

	public void dropTable(String databaseName, String tableName) throws Exception;

	public void updateTable(String databaseName, String tableName, List<String> columns, List<String> values,
			Map<String, String> conditions) throws Exception;

}
