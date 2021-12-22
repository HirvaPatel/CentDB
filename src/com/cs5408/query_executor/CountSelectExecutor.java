package com.cs5408.query_executor;

import com.cs5408.model.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CountSelectExecutor{

    public void executeQuery(String database) throws FileNotFoundException {
        Map<String, Integer> selectQueryCount = new HashMap<String, Integer>();

        File logFile = new File(Constants.logPath + "query_logs.txt");
        Scanner logFileReader = new Scanner(logFile);

        while (logFileReader.hasNextLine()) {
            String data = logFileReader.nextLine();
            data=data.toLowerCase();
            if (data.length() != 0) {
                if(database.equals(data.split("\\|")[1].replaceAll(";",""))) {
                    boolean valid = ((data.split("\\?")[1]).split("\\s+")[0]).equals("valid");
                    boolean select = data.contains("select");
                    if (valid && select) {
                        String table = (data.split("from ")[1]).split("\\s+")[0];
                        table = table.replace(";", "");
                        if (selectQueryCount.containsKey(table)) {
                            int currentCount = selectQueryCount.get(table);
                            int updatedCount = currentCount + 1;
                            selectQueryCount.put(table, updatedCount);
                        } else {
                            selectQueryCount.put(table, 1);
                        }
                    }
                }
            }
        }
           for (String table : selectQueryCount.keySet()) {
              System.out.println("Total " + selectQueryCount.get(table) + " select operations are performed on " + table);
        }

    }
}
