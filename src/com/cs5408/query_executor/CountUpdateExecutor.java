package com.cs5408.query_executor;

import com.cs5408.model.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CountUpdateExecutor {
    public void executeQuery(String database) throws FileNotFoundException {
        Map<String, Integer> updateQueryCount = new HashMap<String, Integer>();

        File logFile = new File(Constants.logPath + "query_logs.txt");
        Scanner logFileReader = new Scanner(logFile);

        while (logFileReader.hasNextLine()) {
            String data = logFileReader.nextLine();
            data=data.toLowerCase();
            if (data.length() != 0) {
                if(database.equals(data.split("\\|")[1].replaceAll(";",""))){
                    boolean valid=((data.split("\\?")[1]).split("\\s+")[0]).equals("valid");
                     boolean update = data.contains("update");
                       if(valid && update){
                          String table = (data.split("update ")[1]).split(" set")[0];
                         table=table.replace(";","");
                       if (updateQueryCount.containsKey(table)) {
                         int currentCount = updateQueryCount.get(table);
                         int updatedCount = currentCount + 1;
                         updateQueryCount.put(table, updatedCount);
                    } else {
                        updateQueryCount.put(table, 1);}
                }
            }
           }

        }
        for (String table : updateQueryCount.keySet()) {
            System.out.println("Total " + updateQueryCount.get(table) + " update operations are performed on " + table);
        }

    }
}
