package ru.spbau.mit.App;

import ru.spbau.mit.Command.Command;
import ru.spbau.mit.Exceptions.VariableUndefinedException;
import ru.spbau.mit.InputProcessing.CommandParser;
import ru.spbau.mit.ShellEnvironment.ShellEnvironment;
import ru.spbau.mit.ShellEnvironment.ShellEnvironmentImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Main class - creates environment and runs the shell
 */
public class Shell {
    private static ShellEnvironment env = new ShellEnvironmentImpl();

    static ShellEnvironment getEnv() {
        return env;
    }

    /**
     * While System.in we parse input and run commands
     */
    public static void run() {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                final String userInput = consoleReader.readLine();
                if (Objects.equals(userInput, "")) {
                    continue;
                }
                if (userInput == null) {
                    break;
                }
                final Command command = CommandParser.parse(userInput, getEnv());
                if (command == null) {
                    continue;
                }
                command.run();
            } catch (VariableUndefinedException e) {
                System.out.println(e.toString());
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }


    /**
     * Runs the shell
     *
     * @param args main args
     */
    public static void main(String[] args) {
        Shell.run();
    }
}
