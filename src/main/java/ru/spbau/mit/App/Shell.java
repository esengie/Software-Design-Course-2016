package ru.spbau.mit.App;

import ru.spbau.mit.Command.Command;
import ru.spbau.mit.Exceptions.CommandCreationException;
import ru.spbau.mit.Exceptions.VariableUndefinedException;
import ru.spbau.mit.InputProcessing.CommandParser;
import ru.spbau.mit.ShellEnvironment.ShellEnvironment;
import ru.spbau.mit.ShellEnvironment.ShellEnvironmentImpl;

import java.io.*;

public class Shell {
    private Shell() {
        m_env.addToEnvironment("HOME", "");
    }

    private static ShellEnvironment m_env = new ShellEnvironmentImpl();

    public static ShellEnvironment getEnv() {
        return m_env;
    }

    public static void run() throws IOException, VariableUndefinedException, CommandCreationException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            final String userInput = consoleReader.readLine();
            final Command command = CommandParser.parse(userInput, getEnv());
            command.run();
        }
    }
}
