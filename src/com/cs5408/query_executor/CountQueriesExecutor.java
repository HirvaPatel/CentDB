package com.cs5408.query_executor;

import com.cs5408.model.Constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CountQueriesExecutor{

    public void executeQuery(String database) throws Exception {
        Map<String,Integer> userQueryCount=new HashMap<String, Integer>();

        File logFile=new File(Constants.logPath+"query_logs.txt");
        Scanner logFileReader =new Scanner(logFile);

        while(logFileReader.hasNextLine()) {
            String data = logFileReader.nextLine();
            if (data.length() != 0) {
                if(database.equals(data.split("\\|")[1].replace(";",""))){
                String userValue = (data.split("@")[1]).split("\\s+")[0];
                if (userQueryCount.containsKey(userValue)) {
                    int currentCount = userQueryCount.get(userValue);
                    int updatedCount = currentCount + 1;
                    userQueryCount.put(userValue, updatedCount);
                } else {
                    userQueryCount.put(userValue, 1);
                }
            }
            }
        }
        for(String user: userQueryCount.keySet()){
            System.out.println("user "+user+" submitted "+userQueryCount.get(user)+" queries on "+database);
        }

   }
}
