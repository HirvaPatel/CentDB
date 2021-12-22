package com.cs5408.application;

import com.cs5408.command_line_runner.CommandLineRunner;
import com.cs5408.command_line_runner.LoginCommandLineRunner;
import com.cs5408.logging.Logging;

public class Main {

    public static void main(String[] args) throws Exception {
        Logging log = new Logging();
        LoginCommandLineRunner loginRunner = new LoginCommandLineRunner();
        loginRunner.loginRunner();

        if (loginRunner.getUser()._isLoggedIn()) {
            CommandLineRunner commandLineRunner = new CommandLineRunner();
            try {
                log.log("event","Execution Started");
                commandLineRunner.commandLineRunner();
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    }
}
