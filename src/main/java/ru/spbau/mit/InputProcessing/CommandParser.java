package ru.spbau.mit.InputProcessing;

import ru.spbau.mit.Command.Command;
import ru.spbau.mit.Command.CommandFactory;
import ru.spbau.mit.Exceptions.VariableUndefinedException;
import ru.spbau.mit.Piping.Pipe;
import ru.spbau.mit.ShellEnvironment.ShellEnvironment;
import ru.spbau.mit.Util.Pair;

import java.io.IOException;
import java.util.*;

/**
 * Parses an input line and returns a Command
 */
public class CommandParser {
    /**
     * Breaks input by pipes, reads all the env vars and removes the lines
     * they're in, substitutes vars in the lines
     *
     * @param a_input input string from console
     * @param a_env env - gets vars added here
     * @return commands split by pipes
     * @throws VariableUndefinedException if there's no variable in the env
     */
    public static List<String> processInput(String a_input, ShellEnvironment a_env) throws VariableUndefinedException {
        List<String> piped = CommandSplitter.splitByPipe(a_input);
        for (int i = 0; i < piped.size();){
            Optional<Pair<String, String>> res =
                    EnvironmentReader.getEnvironmentVariable(piped.get(i));
            if (!res.isPresent()){
                piped.set(i,
                        VariableSubstituter.substituteVariables(piped.get(i), a_env));
                ++i;
                continue;
            }

            a_env.addToEnvironment(res.get().first, res.get().second);
            piped.remove(i);
        }

        return piped;
    }

    /**
     * Calls processInput and combines the piped commands into one
     *
     * @param a_input input string from console
     * @param a_env env -- to substitute vars
     * @return a Command that is a pipe of all the commands
     * @throws IOException piping error or a variable undefined
     */
    public static Command parse(String a_input, ShellEnvironment a_env) throws IOException {
        List<String> pipedCommands = processInput(a_input, a_env);
        Deque<Command> commands = new ArrayDeque<>();
        for (String s : pipedCommands){
            commands.add(formCommand(s));
        }

        // Pipe everything
        while (commands.size() > 1){
            Command left = commands.pollFirst();
            Command right = commands.pollFirst();
            commands.addFirst(Pipe.connect(left, right));
        }

        return commands.pollFirst();
    }

    /**
     * Calls to a factory breaking up the line with only one command in it
     * (doesn't know about vars or anything)
     *
     * @param s a command with args
     * @return command
     */
    private static Command formCommand(String s) {
        String[] l = s.split("\\s");
        if (l.length < 2)
            return CommandFactory.createCommand(s);
        return CommandFactory.createCommand(l[0], Arrays.copyOfRange(l, 1, l.length));
    }
}
