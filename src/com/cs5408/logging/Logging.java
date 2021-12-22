package com.cs5408.logging;
import com.cs5408.command_line_runner.LoginCommandLineRunner;
import com.cs5408.model.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logging {

    public void log(String logType, String message) throws IOException {

        String filepath;

        switch (logType.toLowerCase()) {
            case "query":     //for query log
                filepath = Constants.logPath +"query_logs.txt";
                break;
            case "event":     //for Event log
                filepath = Constants.logPath+"event_logs.txt";
                break;
            case "general":         //for general log
                filepath = Constants.logPath+"general_logs.txt";
                break;
            default:
                System.out.println("Invalid logs type used");
                return;
        }
        LoginCommandLineRunner loginCommandLineRunner = new LoginCommandLineRunner();
        String user = loginCommandLineRunner.getUser().getUserName();

            FileWriter logFile = new FileWriter(filepath,true);
            LocalDate logDate = LocalDate.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String logTime = LocalTime.now().format(dateTimeFormatter);
            logFile.write("[" + (logDate) + "-" + (logTime) + "] @" + user + " #" + message + "\n");
            logFile.close();
    }
}


