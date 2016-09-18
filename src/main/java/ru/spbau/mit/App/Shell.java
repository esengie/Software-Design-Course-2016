package ru.spbau.mit.App;

import ru.spbau.mit.Command.Command;
import ru.spbau.mit.Exceptions.CommandCreationException;
import ru.spbau.mit.Exceptions.VariableUndefinedException;
import ru.spbau.mit.InputProcessing.CommandParser;
import ru.spbau.mit.ShellEnvironment.ShellEnvironment;
import ru.spbau.mit.ShellEnvironment.ShellEnvironmentImpl;

import java.io.*;
import java.util.Objects;

public class Shell {
    private static ShellEnvironment m_env = new ShellEnvironmentImpl();

    static {
        m_env.addToEnvironment("HOME", "");
    }

    public static ShellEnvironment getEnv() {
        return m_env;
    }

    public static void run() {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                final String userInput = consoleReader.readLine();
                if (Objects.equals(userInput, ""))
                    continue;
                if (userInput == null)
                    break;
                final Command command = CommandParser.parse(userInput, getEnv());
                if (command == null)
                    continue;
                command.run();
            }
            catch (CommandCreationException | VariableUndefinedException | IOException e){
                System.out.println(e.toString());
            }
        }
    }

    public static void main(String [ ] args){
        Shell.run();
    }
}
