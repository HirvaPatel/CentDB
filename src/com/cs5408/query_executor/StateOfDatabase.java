package com.cs5408.query_executor;

import com.cs5408.model.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StateOfDatabase {

    public String getStatus(String database) throws IOException {

        File directory=new File(Constants.baseFilePath + database);
        int tableCount=directory.list().length/2;
        String value="";
        for(File file:directory.listFiles()){
            if(!file.getName().contains("metadata")) {
                value=value.concat(file.getName().replaceAll(".txt",""));
                int counter = 0;
                BufferedReader reader = new BufferedReader(new FileReader(file));
                int lineNo = 0;
                while (reader.readLine() != null) {
                    if (lineNo == 0) {
                    } else {
                        counter++;
                    }
                    lineNo++;
                    }
                value=value.concat(":"+counter+" ");
                }
            }
        return "Database "+database+" contains "+tableCount+" tables :{"+value+"}";
    }
}
