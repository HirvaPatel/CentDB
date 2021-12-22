package com.cs5408.parser;

import com.cs5408.logging.Logging;
import com.cs5408.query_executor.*;

import java.util.Arrays;
import java.util.List;

public class LogParser {
    public void parse(String input) throws Exception {

        String[] analyticQueryParts = input.split("\\s+");
        List<String> queryPartsList = Arrays.asList(analyticQueryParts);
        Logging log=new Logging();

        String analyticQueryType = queryPartsList.get(0);
       // System.out.println(analyticQueryType);

        if(analyticQueryType.toLowerCase().equals("count")){
            String analyticQueryFilter =queryPartsList.get(1);
       //     System.out.println(analyticQueryFilter);
            String analyticDatabase=queryPartsList.get(2);

            switch (analyticQueryFilter){
                case "queries":
                    log.log("event","Requested Analysis of number of queries");
                    CountQueriesExecutor countQueriesExecutor=new CountQueriesExecutor();
                    countQueriesExecutor.executeQuery(analyticDatabase);
                    break;
                case "select":
                    log.log("event","Requested Analysis of number of select queries");
                    CountSelectExecutor countSelectExecutor=new CountSelectExecutor();
                    countSelectExecutor.executeQuery(analyticDatabase);
                    break;
                case "update":
                    log.log("event","Requested Analysis of number of update queries");
                    CountUpdateExecutor countUpdateExecutor=new CountUpdateExecutor();
                    countUpdateExecutor.executeQuery(analyticDatabase);
                    break;
                case "use":
                    log.log("event","Requested Analysis of number of use queries");
                    CountUseExecutor countUseExecutor=new CountUseExecutor();
                    countUseExecutor.executeQuery(analyticDatabase);
                    break;
                case "insert":
                    log.log("event","Requested Analysis of number of insert queries");
                    CountInsertExecutor countInsertExecutor=new CountInsertExecutor();
                    countInsertExecutor.executeQuery(analyticDatabase);
                    break;
                case "delete":
                    log.log("event","Requested Analysis of number of delete queries");
                    CountDeleteExecutor countDeleteExecutor=new CountDeleteExecutor();
                    countDeleteExecutor.executeQuery(analyticDatabase);
                    break;
                case "drop":
                    log.log("event","Requested Analysis of number of drop queries");
                    CountDropExecutor countDropExecutor=new CountDropExecutor();
                    countDropExecutor.executeQuery(analyticDatabase);
                    break;
                default:
                    System.err.println("Invalid Query syntax! Try again!");
                    log.log("event","invalid analysis syntax");
                    break;
            }
        }
    }
}
