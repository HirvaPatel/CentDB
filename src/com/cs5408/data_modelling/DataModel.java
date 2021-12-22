package com.cs5408.data_modelling;

import com.cs5408.model.Constants;

import java.io.*;
import java.util.*;

public class DataModel {

    public void createERD(String DatabaseName) throws IOException {
        List<String> str_FilesList = new ArrayList<String>();
        if (new File(Constants.baseFilePath + DatabaseName).exists()) {
            File[] files = new File(Constants.baseFilePath + DatabaseName).listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    str_FilesList.add(file.getName());
                }
            }
            String str_DumpFileName = DatabaseName + "_" + System.currentTimeMillis() + ".txt";
            String str_DatabaseDumpPath = Constants.erdPath + str_DumpFileName;
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(str_DatabaseDumpPath);
                for (String fileName : str_FilesList) {
                    if (fileName.contains("-metadata")) {
                        fileWriter.append(String.format("+" + "-".repeat(42) + "+\n"));
                        try (BufferedReader br = new BufferedReader(new FileReader(Constants.baseFilePath + DatabaseName + "//" + fileName))) {
                            String str_CurrentLine;
                            fileWriter.append(String.format("| %-40s |\n", fileName.split("-")[0].toUpperCase()));
                            fileWriter.append(String.format("+" + "-".repeat(19) + "+" + "-".repeat(22) + "+\n"));
                            while ((str_CurrentLine = br.readLine()) != null) {
                                if (str_CurrentLine.isEmpty() || str_CurrentLine.trim().equals("") || str_CurrentLine.trim().equals("\n"))
                                    str_CurrentLine = br.readLine();
                                if (!str_CurrentLine.contains("table_name$column_name$datatype")) {
                                    String[] strArr_CurrentLine = str_CurrentLine.split("\\$");
                                    String field_key = "";
                                    if (strArr_CurrentLine.length == 4) {
                                        field_key = str_CurrentLine.split("\\$")[3].contains("FK")
                                                ? str_CurrentLine.split("\\$")[3].split("_")[0] + "-" + str_CurrentLine.split("\\$")[3].split("_")[1] + "("
                                                + str_CurrentLine.split("\\$")[3].split("_")[2] + ")"
                                                : str_CurrentLine.split("\\$")[3].replaceAll("-", ",");
                                    }
                                    fileWriter.append(String.format("| %-17s | %-20s |\n", field_key, str_CurrentLine.split("\\$")[1] + " " + str_CurrentLine.split("\\$")[2]));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        fileWriter.append(String.format("+" + "-".repeat(42) + "+\n"));
                    }
                }
                fileWriter.close();
                System.out.println("ERD Successfully Generated. Please Check resources/erd/" + str_DumpFileName + " !!");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            System.out.println("\nDatabase not Found !! Please Enter Again.");
        }
    }

}
