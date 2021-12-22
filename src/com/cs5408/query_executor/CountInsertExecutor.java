package com.cs5408.query_executor;

import com.cs5408.model.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CountInsertExecutor {
    public void executeQuery(String database) throws FileNotFoundException {

        Map<String, Integer> insertQueryCount = new HashMap<String, Integer>();

        File logFile = new File(Constants.logPath + "query_logs.txt");
        Scanner logFileReader = new Scanner(logFile);

        while (logFileReader.hasNextLine()) {
            String data = logFileReader.nextLine();
            data=data.toLowerCase();
            if (data.length() != 0) {
                if(database.equals(data.split("\\|")[1].replaceAll(";",""))){
                    boolean valid=((data.split("\\?")[1]).split("\\s+")[0]).equals("valid");
                    boolean insert = data.contains("insert");
                    if(valid && insert){
                        String table = (data.split("insert into ")[1]).split("\\s")[0];
                        table=table.replace(";","");
                        if (insertQueryCount.containsKey(table)) {
                            int currentCount = insertQueryCount.get(table);
                            int updatedCount = currentCount + 1;
                            insertQueryCount.put(table, updatedCount);
                        } else {
                            insertQueryCount.put(table, 1);}
                    }
                }
            }

        }
        for (String table : insertQueryCount.keySet()) {
            System.out.println("Total " + insertQueryCount.get(table) + " insert operations are performed on " + table);
        }

    }
}
