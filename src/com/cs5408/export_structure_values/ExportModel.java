package com.cs5408.export_structure_values;

import com.cs5408.model.Constants;

import java.io.*;
import java.util.*;

public class ExportModel {

    public void ExportWithoutValues(String DatabaseName) throws IOException {
        List<String> str_FilesList = new ArrayList<String>();
        if (new File(Constants.baseFilePath + DatabaseName).exists()) {
            File[] files = new File(Constants.baseFilePath + DatabaseName).listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    str_FilesList.add(file.getName());
                }
            }
            String str_DumpFileName = DatabaseName + "_WithoutValues_" + System.currentTimeMillis() + ".sql";
            String str_DatabaseDumpPath = Constants.sqldumpsPath + str_DumpFileName;
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(str_DatabaseDumpPath);
                for (String fileName : str_FilesList) {
                    if (fileName.contains("-metadata")) {
                        try (BufferedReader br = new BufferedReader(new FileReader(Constants.baseFilePath + DatabaseName + "//" + fileName))) {
                            String str_CurrentLine;
                            String query = "Create table " + fileName.split("-")[0] + "(";
                            while ((str_CurrentLine = br.readLine()) != null) {
                                if (str_CurrentLine.isEmpty() || str_CurrentLine.trim().equals("") || str_CurrentLine.trim().equals("\n"))
                                    str_CurrentLine = br.readLine();
                                if (!str_CurrentLine.contains("table_name$column_name$datatype")) {
                                    String keys_query = "";
                                    String[] strArr_CurrentLine = str_CurrentLine.split("\\$");
                                    if (strArr_CurrentLine.length == 4) {
                                        String[] strArr_CurrentFieldKeys = str_CurrentLine.split("\\$")[3].split("-");
                                        for (int i = 0; i < strArr_CurrentFieldKeys.length; i++) {
                                            if (strArr_CurrentFieldKeys[i].equals("PK")) {
                                                keys_query += " PRIMARY KEY";
                                            } else if (strArr_CurrentFieldKeys[i].equals("NN")) {
                                                keys_query += " NOT NULL";
                                            } else if (strArr_CurrentFieldKeys[i].equals("UN")) {
                                                keys_query += " UNIQUE";
                                            } else {
                                                String[] strArr_FKs = strArr_CurrentFieldKeys[i].split("_");
                                                keys_query += " FOREIGN KEY REFERENCES " + strArr_FKs[1] + "(" + strArr_FKs[2] + ")";
                                            }
                                        }
                                    }
                                    query += "\n\t" + str_CurrentLine.split("\\$")[1] + " " + str_CurrentLine.split("\\$")[2] + keys_query + ",";
                                }
                            }
                            query = query.substring(0, query.length() - 1) + "\n);\n";
                            fileWriter.write(query);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                fileWriter.close();
                System.out.println("Database Successfully Exported. Please Check resources/sql_dumps/" + str_DumpFileName + " !!");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            System.out.println("\nDatabase not Found !! Please Enter Again.");
        }
    }

    public void ExportWithValues(String DatabaseName) throws IOException {
        List<String> str_FilesList = new ArrayList<String>();
        if (new File(Constants.baseFilePath + DatabaseName).exists()) {
            File[] files = new File(Constants.baseFilePath + DatabaseName).listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    str_FilesList.add(file.getName());
                }
            }
            String str_DumpFileName = DatabaseName + "_WithValues_" + System.currentTimeMillis() + ".sql";
            String str_DatabaseDumpPath = Constants.sqldumpsPath + str_DumpFileName;
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(str_DatabaseDumpPath);
                for (String fileNames : str_FilesList) {
                    if (fileNames.contains("-metadata")) {
                        String createfilePath = Constants.baseFilePath + DatabaseName + "//" + fileNames;
                        Map<String, String> fieldDataType = new HashMap<>();
                        try (BufferedReader br = new BufferedReader(new FileReader(createfilePath))) {
                            String str_CurrentLine;
                            String query = "Create table " + fileNames.split("-")[0] + "(";
                            while ((str_CurrentLine = br.readLine()) != null) {
                                if (str_CurrentLine.isEmpty() || str_CurrentLine.trim().equals("") || str_CurrentLine.trim().equals("\n"))
                                    str_CurrentLine = br.readLine();
                                if (!str_CurrentLine.contains("table_name$column_name$datatype")) {
                                    String keys_query = "";
                                    String[] strArr_CurrentLine = str_CurrentLine.split("\\$");
                                    if (strArr_CurrentLine.length == 4) {
                                        String[] strArr_CurrentFieldKeys = str_CurrentLine.split("\\$")[3].split("-");
                                        for (int i = 0; i < strArr_CurrentFieldKeys.length; i++) {
                                            if (strArr_CurrentFieldKeys[i].equals("PK")) {
                                                keys_query += " PRIMARY KEY";
                                            } else if (strArr_CurrentFieldKeys[i].equals("NN")) {
                                                keys_query += " NOT NULL";
                                            } else if (strArr_CurrentFieldKeys[i].equals("UN")) {
                                                keys_query += " UNIQUE";
                                            } else {
                                                String[] strArr_FKs = strArr_CurrentFieldKeys[i].split("_");
                                                keys_query += " FOREIGN KEY REFERENCES " + strArr_FKs[1] + "(" + strArr_FKs[2] + ")";
                                            }
                                        }
                                    }
                                    query += "\n\t" + str_CurrentLine.split("\\$")[1] + " " + str_CurrentLine.split("\\$")[2] + keys_query + ",";
                                    fieldDataType.put(str_CurrentLine.split("\\$")[1], str_CurrentLine.split("\\$")[2]);
                                }
                            }
                            query = query.substring(0, query.length() - 1) + "\n);\n";
                            fileWriter.write(query);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //----------------------------------------------------
                        String insertfilePath = Constants.baseFilePath + DatabaseName + "//" + fileNames.split("-")[0] + ".txt";
                        try (BufferedReader br = new BufferedReader(new FileReader(insertfilePath))) {

                            String str_CurrentLine = br.readLine();
                            if (str_CurrentLine.isEmpty() || str_CurrentLine.trim().equals("") || str_CurrentLine.trim().equals("\n"))
                                str_CurrentLine = br.readLine();
                            String str_Column = str_CurrentLine.replaceAll("\\$", ",");
                            String[] strArr_Fields = str_Column.split(",");
                            while ((str_CurrentLine = br.readLine()) != null) {
                                String[] strArr_Values = str_CurrentLine.split("\\$");
                                String query = "insert into " + fileNames.split("-")[0] + "(" + str_Column + ") values (";
                                for (int i = 0; i < strArr_Values.length; i++) {
                                    String str_CurrentFieldType = fieldDataType.get(strArr_Fields[i]);
                                    if (str_CurrentFieldType.equalsIgnoreCase("int") || str_CurrentFieldType.equalsIgnoreCase("bigint") || str_CurrentFieldType.equalsIgnoreCase("tinyint")
                                            || str_CurrentFieldType.equalsIgnoreCase("decimal") || str_CurrentFieldType.equalsIgnoreCase("float") || str_CurrentFieldType.equalsIgnoreCase("double")
                                            || str_CurrentFieldType.equalsIgnoreCase("boolean"))
                                        query += strArr_Values[i] + ",";
                                    else if (str_CurrentFieldType.equalsIgnoreCase("varchar") || str_CurrentFieldType.equalsIgnoreCase("nvarchar") || str_CurrentFieldType.equalsIgnoreCase("date")
                                            || str_CurrentFieldType.equalsIgnoreCase("datetime") || str_CurrentFieldType.equalsIgnoreCase("text") || str_CurrentFieldType.equalsIgnoreCase("longtext")
                                            || str_CurrentFieldType.equalsIgnoreCase("time") || str_CurrentFieldType.equalsIgnoreCase("timestamp"))
                                        query += "'" + strArr_Values[i] + "',";
                                }
                                query = query.substring(0, query.length() - 1) + ");\n";
                                fileWriter.write(query);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                fileWriter.close();
                System.out.println("Database Successfully Exported. Please Check resources/sql_dumps/" + str_DumpFileName + " !!");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            System.out.println("\nDatabase not Found !! Please Enter Again.");
        }

    }
}
