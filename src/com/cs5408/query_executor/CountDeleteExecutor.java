package com.cs5408.query_executor;

import com.cs5408.model.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CountDeleteExecutor {
    public void executeQuery(String database) throws FileNotFoundException {

        Map<String, Integer> deleteQueryCount = new HashMap<String, Integer>();

        File logFile = new File(Constants.logPath + "query_logs.txt");
        Scanner logFileReader = new Scanner(logFile);

        while (logFileReader.hasNextLine()) {
            String data = logFileReader.nextLine();
            data=data.toLowerCase();
            if (data.length() != 0) {
                if(database.equals(data.split("\\|")[1].replaceAll(";",""))){
                    boolean valid=((data.split("\\?")[1]).split("\\s+")[0]).equals("valid");
                    boolean delete = data.contains("delete");
                    if(valid && delete){
                        String table = (data.split("delete from ")[1]).split("\\s")[0];
                        table=table.replace(";","");
                        if (deleteQueryCount.containsKey(table)) {
                            int currentCount = deleteQueryCount.get(table);
                            int updatedCount = currentCount + 1;
                            deleteQueryCount.put(table, updatedCount);
                        } else {
                            deleteQueryCount.put(table, 1);}
                    }
                }
            }

        }
        for (String table : deleteQueryCount.keySet()) {
            System.out.println("Total " + deleteQueryCount.get(table) + " delete operations are performed on " + table);
        }

    }
}
