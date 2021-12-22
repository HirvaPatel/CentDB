package com.cs5408.file_handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cs5408.model.Constants;

public class FIleHandler implements IFileHandler {

	@Override
	public void createTable(String databaseName, String tableName, Map<String, String> properties, String primaryKey,
			String foreignKey, String referencedTable) throws Exception {

		File metaDataFile = new File(Constants.baseFilePath + databaseName + "/" + tableName + "-metadata.txt");
		File tableFile = new File(Constants.baseFilePath + databaseName + "/" + tableName + ".txt");

		BufferedWriter writer = new BufferedWriter(new FileWriter(metaDataFile));

		String fileData = "";

		fileData = String.join(Constants.delimiter, "table_name", "column_name", "datatype", "key");
		writer.write(fileData + System.getProperty("line.separator"));
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (key != null && key.equals(primaryKey)) {
				fileData = String.join(Constants.delimiter, tableName, key, value, "PK");
			} else if (key != null && key.equals(foreignKey)) {
				fileData = String.join(Constants.delimiter, tableName, key, value,
						"FK_" + referencedTable + "_" + foreignKey);
			} else {
				fileData = String.join(Constants.delimiter, tableName, key, value);
			}
			writer.write(fileData + System.getProperty("line.separator"));
		}

		writer.flush();
		writer.close();

		writer = new BufferedWriter(new FileWriter(tableFile));
		List<String> columns = new ArrayList<String>(properties.keySet());

		fileData = String.join(Constants.delimiter, columns);
		writer.write(fileData + System.getProperty("line.separator"));
		writer.flush();
		writer.close();
	}

	@Override
	public void insertIntoTable(String databaseName, String tableName, Map<String, Object> values) throws Exception {

		File tableFile = new File(Constants.baseFilePath + databaseName + "/" + tableName + ".txt");
		File metaDataFile = new File(Constants.baseFilePath + databaseName + "/" + tableName + "-metadata.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(tableFile, true));
		BufferedReader reader = new BufferedReader(new FileReader(metaDataFile));

		String currentLine;
		int lineNo = 0;
		List<String> columnOrder = new ArrayList<>();
		while ((currentLine = reader.readLine()) != null) {
			if (lineNo == 0) {
			} else {
				List<String> fields = Arrays.asList(currentLine.split(Constants.delimiterForFetch));
				columnOrder.add(fields.get(1));
			}
			lineNo = lineNo + 1;
		}

		String fileData = "";
		List<String> dataOrder = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			dataOrder.add(values.get(columnOrder.get(i)).toString());
		}

		fileData = String.join(Constants.delimiter, dataOrder);
		writer.write(fileData + System.getProperty("line.separator"));
		writer.flush();
		writer.close();
		reader.close();
	}

	@Override
	public Map<String, String> getTableMetaDataColumnProperties(String databaseName, String tableName)
			throws Exception {

		File metaDataFile = new File(Constants.baseFilePath + databaseName + "/" + tableName + "-metadata.txt");
		BufferedReader reader = new BufferedReader(new FileReader(metaDataFile));

		String currentLine;
		int lineNo = 0;
		Map<String, String> columnDatatypes = new HashMap<>();
		while ((currentLine = reader.readLine()) != null) {

			if (lineNo == 0) {
			} else {
				List<String> fields = Arrays.asList(currentLine.split(Constants.delimiterForFetch));
				columnDatatypes.put(fields.get(1), fields.get(2));
			}
			lineNo = lineNo + 1;
		}
		reader.close();
		return columnDatatypes;
	}

	@Override
	public Map<String, String> selectTable(String databaseName, String tableName, List<String> getColumns,
			Map<String, String> conditions) throws Exception {
		File tableFile = new File(Constants.baseFilePath + databaseName + "/" + tableName + ".txt");
			BufferedReader reader = new BufferedReader(new FileReader(tableFile));

		String currentLine;
		int lineNo = 0;
		Map<String, List<String>> tableData = new HashMap<>();
		List<String> fields = null;
		List<String> data = null;
		System.out.println("Select Result : ");
		while ((currentLine = reader.readLine()) != null) {
			if (lineNo == 0) {
				fields = Arrays.asList(currentLine.split(Constants.delimiterForFetch));
			} else {
				data = Arrays.asList(currentLine.split(Constants.delimiterForFetch));
				List<String> row = new ArrayList<>();

				if (conditions != null && !conditions.isEmpty()) {
					Boolean conditionSatisfied = true;
					for (Map.Entry<String, String> mapEntry : conditions.entrySet()) {
						String var = mapEntry.getKey();
						String value = mapEntry.getValue();

						int i = fields.indexOf(var);
						if (!data.get(i).equalsIgnoreCase(value)) {
							conditionSatisfied = false;
							break;
						}

					}

					if (!conditionSatisfied) {

						lineNo = lineNo + 1;
						continue;
					}
				}
				if (getColumns.get(0).equals("*")) {
					for (int columnIndex = 0; columnIndex < fields.size(); columnIndex++) {
						row.add(data.get(columnIndex));
					}
				} else {
					for (String column : getColumns) {
						int columnIndex = fields.indexOf(column);
						row.add(data.get(columnIndex));
					}
				}
				tableData.put("Data", row);
			}
			lineNo = lineNo + 1;

			for (Map.Entry<String, List<String>> entry : tableData.entrySet()) {
				System.out.println(entry.getValue());
			}
		}
		reader.close();
		return null;
	}

	@Override
	public void deleteTable(String databaseName, String tableName, Map<String, String> conditions) throws Exception {
		File tableFile = new File(Constants.baseFilePath + databaseName + "/" + tableName + ".txt");
		BufferedReader reader = new BufferedReader(new FileReader(tableFile));

		int lineNo = 0;
		String columnData = "";
		String currentLine;
		List<String> data = null;
		List<String> fields = null;
		while ((currentLine = reader.readLine()) != null) {
			if (lineNo == 0) {

				columnData += currentLine;
				fields = Arrays.asList(currentLine.split(Constants.delimiterForFetch));

			} else {

				if (conditions != null && !conditions.isEmpty()) {

					data = Arrays.asList(currentLine.split(Constants.delimiterForFetch));

					Boolean conditionSatisfied = false;
					for (Map.Entry<String, String> mapEntry : conditions.entrySet()) {
						String var = mapEntry.getKey();
						String value = mapEntry.getValue();

						int i = fields.indexOf(var);
						if (data.get(i).equalsIgnoreCase(value)) {
							conditionSatisfied = true;
							break;
						}

					}

					if (!conditionSatisfied) {
						if (lineNo == 1) {
							columnData += System.getProperty("line.separator") + currentLine
									+ System.getProperty("line.separator");
//							System.out.println(columnData);
						} else {
							columnData += System.getProperty("line.separator")+ currentLine;
//							System.out.println(columnData);
						}
						lineNo = lineNo + 1;
						continue;
					}
				}

			}
			lineNo++;
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(tableFile));
		writer.write(columnData + System.getProperty("line.separator"));
		reader.close();
		writer.close();

		System.out.println(tableName + " deleted successfully!!");
	}

	@Override
	public void dropTable(String databaseName, String tableName) throws Exception {
		File tableFile = new File(Constants.baseFilePath + databaseName + "/" + tableName + ".txt");
		File metaDataFile = new File(Constants.baseFilePath + databaseName + "/" + tableName + "-metadata.txt");

		metaDataFile.delete();
		System.out.println(tableName + " structure dropped successfully!!");
		tableFile.delete();
		System.out.println(tableName + " dropped successfully!!");
	}

	@Override
	public void updateTable(String databaseName, String tableName, List<String> columns, List<String> values,
			Map<String, String> conditions) throws Exception {
		File tableFile = new File(Constants.baseFilePath + databaseName + "/" + tableName + ".txt");
		BufferedReader reader = new BufferedReader(new FileReader(tableFile));

		String currentLine;
		int lineNo = 0;

		List<String> fields = null;
		List<String> data = null;
		String updatedData = "";
		while ((currentLine = reader.readLine()) != null) {
			if (lineNo == 0) {
				fields = Arrays.asList(currentLine.split(Constants.delimiterForFetch));
			} else {
				data = Arrays.asList(currentLine.split(Constants.delimiterForFetch));

				if (conditions != null && !conditions.isEmpty()) {
					Boolean conditionSatisfied = true;
					for (Map.Entry<String, String> mapEntry : conditions.entrySet()) {
						String var = mapEntry.getKey();
						String value = mapEntry.getValue();

						int i = fields.indexOf(var);
						if (!data.get(i).equalsIgnoreCase(value)) {
							conditionSatisfied = false;
							break;
						}

					}

					if (!conditionSatisfied) {
						updatedData += currentLine + System.getProperty("line.separator");
						lineNo = lineNo + 1;
						continue;
					}
				}

				for (int i = 0; i < columns.size(); i++) {
					int colIndex = fields.indexOf(columns.get(i));
					currentLine = currentLine.replaceAll(data.get(colIndex), values.get(i));
				}
			}
			updatedData += currentLine + System.getProperty("line.separator");

			lineNo++;
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(tableFile));
		writer.write("");
		writer.write(updatedData);
		reader.close();
		writer.close();
	}

}
