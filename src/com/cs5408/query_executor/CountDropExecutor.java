package com.cs5408.query_executor;

import com.cs5408.model.Constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CountDropExecutor {
    public void executeQuery(String database) throws Exception {

        Map<String,Integer> dropQueryCount =new HashMap<String, Integer>();

        File logFile=new File(Constants.logPath+"query_logs.txt");
        Scanner logFileReader =new Scanner(logFile);

        while(logFileReader.hasNextLine()) {
            String data = logFileReader.nextLine();
            data=data.toLowerCase();
            if (data.length() != 0) {
                if(database.equals(data.split("\\|")[1].replace(";",""))){
                    String user = (data.split("@")[1]).split("\\s+")[0];
                    boolean valid = ((data.split("\\?")[1]).split("\\s+")[0]).equals("valid");
                    boolean drop = data.contains("drop ");
                    if(valid && drop){
                        if (dropQueryCount.containsKey(user)) {
                            int currentCount = dropQueryCount.get(user);
                            int updatedCount = currentCount + 1;
                            dropQueryCount.put(user, updatedCount);
                        } else {
                            dropQueryCount.put(user, 1);
                        }
                    }
                }
            }
        }
        for(String user: dropQueryCount.keySet()){
            System.out.println("user "+user+" submitted "+ dropQueryCount.get(user)+" drop queries on "+database);
        }

    }
}
